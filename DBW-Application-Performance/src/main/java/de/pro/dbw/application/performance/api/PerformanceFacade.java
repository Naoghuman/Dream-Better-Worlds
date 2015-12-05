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
package de.pro.dbw.application.performance.api;

import de.pro.dbw.application.performance.PerformancePresenter;
import de.pro.dbw.application.performance.PerformanceView;
import javafx.scene.Parent;

/**
 *
 * @author PRo
 */
public enum PerformanceFacade {
    
    INSTANCE;
    
    private PerformancePresenter presenter = null;
    private PerformanceView view = null;
    
    private PerformanceFacade() {
        this.initialize();
    }
    
    private void initialize() {
        view = new PerformanceView();
        presenter = view.getRealPresenter();
    }
    
    public void shutdown() throws InterruptedException {
        presenter.shutdown();
    }
    
    public Parent getView() {
        return view.getView();
    }
    
}
