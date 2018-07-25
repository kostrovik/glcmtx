package com.github.kostrovik.kernel.views.menu.actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import provider.interfaces.EventListenerInterface;

/**
 * project: glcmtx
 * author:  kostrovik
 * date:    23/07/2018
 * github:  https://github.com/kostrovik/glcmtx
 */
public class ExitAction implements EventHandler<ActionEvent> {
    private EventListenerInterface listener;

    public ExitAction(EventListenerInterface listener) {
        this.listener = listener;
    }

    @Override
    public void handle(ActionEvent event) {
        System.exit(0);
    }
}