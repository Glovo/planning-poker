package com.glovoapp.planningpoker;

import static java.util.Arrays.copyOfRange;
import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static lombok.AccessLevel.PACKAGE;

import java.util.function.Function;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(PACKAGE)
@RequiredArgsConstructor(access = PACKAGE)
final class Message {

    private final Action action;
    private final String[] dataParts;

    Message(Action action, String data) {
        this(action, new String[]{data});
    }

    final String getData() {
        return getData(0);
    }

    final <T> T getData(Function<String, T> valueMapper) {
        return getData(0, valueMapper);
    }

    final String getData(final int part) {
        return getData(part, identity());
    }

    final <T> T getData(final int part, Function<String, T> valueMapper) {
        try {
            return valueMapper.apply(dataParts[part]);
        } catch (final Exception exception) {
            throw new MalformedMessageException(exception);
        }
    }

    static Message parse(final String message) {
        final String[] messageParts = message.split(":");
        final String actionString = messageParts[0];

        final Action action = stream(Action.values()).filter(it -> it.name()
                                                                     .equals(actionString))
                                                     .findAny()
                                                     .orElseThrow(() -> new UnrecognizedActionException(actionString));

        final String[] data = messageParts.length == 1
            ? new String[]{""}
            : copyOfRange(messageParts, 1, messageParts.length);

        return new Message(action, data);
    }

    enum Action {
        NEW_PLAYER,
        VOTE,
        GET_DATA,
        REMOVE_PLAYER,
        CLEAR_EVERYTHING,
        SHOW_VOTES,
        SET_TICKET,
        STATE,
        SESSION_END,
        ERROR
    }

}
