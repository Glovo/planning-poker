package com.glovoapp.planningpoker;

import static java.util.Collections.emptyMap;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.concat;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.With;

@ToString
@With(PACKAGE)
@Getter(PACKAGE)
@RequiredArgsConstructor(access = PRIVATE)
final class ApplicationState {

    private final String ticket;
    private final Map<WebSocketWrapper, Player> players;

    static ApplicationState empty() {
        return new ApplicationState("", emptyMap());
    }

    final Set<Player> getActivePlayers() {
        return players.values()
                      .stream()
                      .filter(player -> !player.isSpectator())
                      .collect(Collectors.toSet());
    }

    final ApplicationState withoutPlayer(final WebSocketWrapper socket) {
        if (players.containsKey(socket)) {
            return withPlayers(
                players.entrySet()
                       .stream()
                       .filter(it -> it.getKey()
                                       .equals(socket))
                       .collect(toMap(Entry::getKey, Entry::getValue))
            ).withoutClosedPlayers();
        } else {
            return this;
        }
    }

    final ApplicationState withoutTicketNameAndVotes() {
        return withEveryoneHavingAVote(it -> true, "").withTicket("")
                                                      .withoutClosedPlayers();
    }

    final ApplicationState withEveryoneHavingAVote() {
        return withEveryoneHavingAVote(String::isEmpty, "?").withoutClosedPlayers();
    }

    private ApplicationState withEveryoneHavingAVote(final Predicate<String> currentVote, final String voteValue) {
        return withPlayers(
            players.entrySet()
                   .stream()
                   .map(SocketToPlayer::new)
                   .collect(toMap(SocketToPlayer::getSocket, it -> currentVote.test(it.getPlayer()
                                                                                      .getVote())
                       ? it.getPlayer()
                           .withVote(voteValue)
                       : it.getPlayer()))
        ).withoutClosedPlayers();
    }

    final ApplicationState withPlayer(final WebSocketWrapper socket, final Player player) {
        if (players.containsValue(player) && !Optional.of(socket)
                                                      .map(players::get)
                                                      .map(player::equals)
                                                      .orElse(false)) {
            throw new PlayerAlreadyExistsException(player);
        } else {
            return withPlayers(
                concat(
                    players.entrySet()
                           .stream()
                           .map(SocketToPlayer::new)
                           .filter(it -> !it.socket.equals(socket)),
                    Stream.of(new SocketToPlayer(socket, player))
                ).collect(toMap(SocketToPlayer::getSocket, SocketToPlayer::getPlayer))
            ).withoutClosedPlayers();
        }
    }

    final ApplicationState withVote(final WebSocketWrapper socket, final String vote) {
        return withPlayer(socket, Optional.of(socket)
                                          .map(players::get)
                                          .orElseThrow(PlayerNotFoundException::new)
                                          .withVote(vote)).withoutClosedPlayers();
    }

    private ApplicationState withoutClosedPlayers() {
        return withPlayers(
            players.entrySet()
                   .stream()
                   .map(SocketToPlayer::new)
                   .filter(it -> it.getSocket()
                                   .isOpen())
                   .collect(toMap(SocketToPlayer::getSocket, SocketToPlayer::getPlayer))
        );
    }

    @ToString
    @With(PACKAGE)
    @Getter(PACKAGE)
    @RequiredArgsConstructor(access = PACKAGE)
    final static class Player {

        private final String name;
        private final String vote;
        private final boolean spectator;

        public static Comparator<Player> comparingNamesIgnoringCase() {
            return (first, second) -> first.getName()
                                           .compareToIgnoreCase(second.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name);
        }

        @Override
        public boolean equals(final Object other) {
            return other instanceof Player && name.equals(((Player) other).name);
        }

    }

    @Getter(PRIVATE)
    @RequiredArgsConstructor(access = PRIVATE)
    final static class SocketToPlayer {

        private final WebSocketWrapper socket;
        private final Player player;

        private SocketToPlayer(final Map.Entry<WebSocketWrapper, Player> entry) {
            this(entry.getKey(), entry.getValue());
        }

    }

}
