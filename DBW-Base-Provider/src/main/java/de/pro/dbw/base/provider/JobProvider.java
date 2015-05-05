/*
 * Copyright (C) 2015 PRo
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
package de.pro.dbw.base.provider;

import de.pro.dbw.core.configuration.api.application.job.IJobConfiguration;
import de.pro.dbw.base.jobs.impl.JobCheckNavigationDreamBook;
import de.pro.dbw.base.jobs.impl.JobCheckNavigationHistory;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;

/**
 *
 * @author PRo
 */
public class JobProvider implements IJobConfiguration {
    
    private static JobProvider instance = null;
    
    public static JobProvider getDefault() {
        if (instance == null) {
            instance = new JobProvider();
        }
        
        return instance;
    }
    
    private JobProvider() {
        this.initialize();
    }
    
    private void initialize() {
        
    }
    
    public void start() {
        PauseTransition ptChecker = new PauseTransition();
        ptChecker.setDuration(CHECKER_DURATION);
        ptChecker.setOnFinished((ActionEvent event) -> {
            JobCheckNavigationDreamBook.start();
            JobCheckNavigationHistory.start();
        });
        
        ptChecker.playFromStart();
    }
    
    public void stop() {
        JobCheckNavigationDreamBook.stop();
        JobCheckNavigationHistory.stop();
    }
}
