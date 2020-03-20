package com.glovoapp.planningpoker;

import static io.reactivex.Completable.complete;
import static io.vertx.core.logging.LoggerFactory.getLogger;

import com.glovoapp.planningpoker.ApplicationState.Player;
import io.reactivex.Completable;
import io.vertx.core.logging.Logger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

final class ApplicationStateHandler {

    private final Logger log = getLogger(getClass());

    private final AtomicReference<ApplicationState> state = new AtomicReference<>(ApplicationState.empty());


    final String getCurrentSerializedState() {
        return serializeState(state.get());
    }

    final String serializeState(final ApplicationState state) {
        final boolean votesMissing = state.getPlayers()
                                          .values()
                                          .stream()
                                          .map(Player::getVote)
                                          .anyMatch(String::isEmpty);
        return state.getTicket() + ":" + state.getPlayers()
                                              .values()
                                              .stream()
                                              .map(player -> player.getName() + ':'
                                                  + (votesMissing ? "…" : player.getVote()))
                                              .collect(Collectors.joining(":"));
    }

    final void notifyEveryone(final ApplicationState state,
                              final Function<WebSocketWrapper, Completable> notifyFunction) {
        log.info("sending notification to all players");
        state.getPlayers()
             .keySet()
             .stream()
             .peek(socket -> log.info("notifying " + socket))
             .map(notifyFunction)
             .forEach(notificationResult -> notificationResult.subscribe(
                 () -> log.info("notification complete")
             ));
    }

    final void clearTicketNameAndVotes(final BiFunction<ApplicationState, WebSocketWrapper, Completable> notify) {
        updateState(ApplicationState::withoutTicketNameAndVotes, notify);
    }

    final void showVotes(final BiFunction<ApplicationState, WebSocketWrapper, Completable> notify) {
        updateState(ApplicationState::withEveryoneHavingAVote, notify);
    }

    final void setTicket(final String ticketValue,
                         final BiFunction<ApplicationState, WebSocketWrapper, Completable> notify) {
        updateState(currentState -> currentState.withTicket(ticketValue), notify);
    }

    final void addPlayer(final WebSocketWrapper socket,
                         final Player player,
                         final BiFunction<ApplicationState, WebSocketWrapper, Completable> notify) {
        updateState(currentState -> currentState.withPlayer(socket, player), notify);
    }

    final void removePlayer(final WebSocketWrapper socket,
                            final BiFunction<ApplicationState, WebSocketWrapper, Completable> notify) {
        updateState(currentState -> currentState.withoutPlayer(socket), notify);
    }

    final void vote(final WebSocketWrapper socket,
                    final String vote,
                    final BiFunction<ApplicationState, WebSocketWrapper, Completable> notify) {
        updateState(currentState -> currentState.withVote(socket, vote), notify);
    }

    final void closeAllActiveConnections() {
        updateState(currentState -> {
            currentState.getPlayers()
                        .keySet()
                        .stream()
                        .filter(WebSocketWrapper::isOpen)
                        .forEach(WebSocketWrapper::close);
            return ApplicationState.empty();
        }, (applicationState, wrapper) -> complete());
    }

    private void updateState(final Function<ApplicationState, ApplicationState> stateUpdateFunction,
                             final BiFunction<ApplicationState, WebSocketWrapper, Completable> notify) {
        state.updateAndGet(currentState -> {
            final ApplicationState newState = stateUpdateFunction.apply(currentState);
            log.info("notifying everyone about state update from " + currentState + " to " + newState);
            notifyEveryone(newState, playerSocket -> notify.apply(newState, playerSocket));
            log.info("persisting state " + newState);
            return newState;
        });
    }

}
