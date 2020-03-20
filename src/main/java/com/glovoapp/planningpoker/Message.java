package com.glovoapp.planningpoker;

import static lombok.AccessLevel.PACKAGE;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(PACKAGE)
@RequiredArgsConstructor(access = PACKAGE)
final class Message {

    private final Action action;
    private final String data;

    static Message parse(final String message) {
        final String[] messageParts = message.split(":");
        final String actionString = messageParts[0];
        final String data = messageParts.length == 2 ? messageParts[1] : "";

        if (messageParts.length > 2) {
            throw new MalformedMessageException(message);
        }
        final Action action = Arrays.stream(Action.values())
                                    .filter(it -> it.name()
                                                    .equals(actionString))
                                    .findAny()
                                    .orElseThrow(() -> new UnrecognizedActionException(actionString));

        return new Message(action, data);
    }

    enum Action {
        NEW_PLAYER, VOTE, GET_DATA, REMOVE_PLAYER, CLEAR_EVERYTHING, SHOW_VOTES, SET_TICKET
    }

}
