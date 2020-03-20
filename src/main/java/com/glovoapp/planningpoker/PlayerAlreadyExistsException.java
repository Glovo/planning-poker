package com.glovoapp.planningpoker;

import static com.glovoapp.planningpoker.ExceptionWithStatus.Status.CLIENT_ERROR;

import com.glovoapp.planningpoker.ApplicationState.Player;

final class PlayerAlreadyExistsException extends ExceptionWithStatus {

    PlayerAlreadyExistsException(final Player player) {
        super("player with name " + player.getName() + " already exists", CLIENT_ERROR);
    }

}
