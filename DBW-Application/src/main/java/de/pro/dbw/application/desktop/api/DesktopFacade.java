/*
 * Copyright (C) 2014 Dream Better Worlds
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
package de.pro.dbw.application.desktop.api;

import de.pro.dbw.application.desktop.DesktopPresenter;
import de.pro.dbw.application.desktop.DesktopView;
import de.pro.lib.logger.api.LoggerFacade;
import javafx.scene.Parent;

/**
 *
 * @author PRo
 */
public final class DesktopFacade {
    
    private static DesktopFacade instance = null;
    
    public static DesktopFacade getDefault() {
        if (instance == null) {
            instance = new DesktopFacade();
        }
        
        return instance;
    }
    
    private DesktopView view = null;
    
    private DesktopFacade() {
        this.init();
    }
    
    private void init() {
        view = new DesktopView();
    }
    
//    public EventHandler<KeyEvent> getGlobalKeyEventHandler() {
//        return ((StartPagePresenter) startPageView.getPresenter()).getGlobalKeyEventHandler();
//    }
    
    public Boolean checkForUnsavedFiles() {
        final DesktopPresenter presenter = view.getRealPresenter();
        return presenter.checkForUnsavedFiles();
    }
    
    public Parent getDesktop() {
        return view.getView();
    }

    public void onActionSaveMultiFiles() {
        LoggerFacade.INSTANCE.getLogger().debug(this.getClass(), "On action save Multi Files"); // NOI18N
        
        final DesktopPresenter presenter = view.getRealPresenter();
        presenter.onActionSaveMultiFiles();
    }
    
}
