/*
 * Copyright (C) 2015 Dream Better Worlds
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.pro.dbw.dialog.api;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author PRo
 */
public class DialogEventHandler {
    
    private static DialogEventHandler instance = null;
    
    public static DialogEventHandler getDefault() {
        if (instance == null) {
            instance = new DialogEventHandler();
        }
        
        return instance;
    }
    
    private double orgSceneX = 0.0d;
    private double orgSceneY = 0.0d;
    private double orgTranslateX = 0.0d;
    private double orgTranslateY = 0.0d;
    
    private DialogEventHandler() {
        this.initialize();
    }
    
    private void initialize() {
        
    }
    
    public void configure(final Pane pTitledPaneHeader, final AnchorPane apTranslate) {
        pTitledPaneHeader.setCursor(Cursor.MOVE);
        pTitledPaneHeader.setOnMouseDragged(this.getOnMouseDraggedEventHandler(apTranslate));
        pTitledPaneHeader.setOnMousePressed(this.getOnMousePressedEventHandler(apTranslate));
    }
    
    private EventHandler<MouseEvent> getOnMouseDraggedEventHandler(final AnchorPane apTranslate) {
        final EventHandler<MouseEvent> onMouseDraggedEventHandler = (MouseEvent t) -> {
            final double offsetX = t.getSceneX() - orgSceneX;
            final double offsetY = t.getSceneY() - orgSceneY;
            final double newTranslateX = orgTranslateX + offsetX;
            final double newTranslateY = orgTranslateY + offsetY;

            apTranslate.setTranslateX(newTranslateX);
            apTranslate.setTranslateY(newTranslateY);
        };
        
        return onMouseDraggedEventHandler;
    }
    
    private EventHandler<MouseEvent> getOnMousePressedEventHandler(final AnchorPane apTranslate) {
        final EventHandler<MouseEvent> onMousePressedEventHandler = (MouseEvent t) -> {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            orgTranslateX = apTranslate.getTranslateX();
            orgTranslateY = apTranslate.getTranslateY();
        };
        
        return onMousePressedEventHandler;
    }
    
}
