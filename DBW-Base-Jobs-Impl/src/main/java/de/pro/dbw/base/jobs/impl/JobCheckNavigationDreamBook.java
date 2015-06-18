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
package de.pro.dbw.base.jobs.impl;

import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.application.job.IJobConfiguration;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.logger.api.LoggerFacade;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.util.Duration;

/**
 *
 * @author PRo
 */
public class JobCheckNavigationDreamBook implements IActionConfiguration, IJobConfiguration {
    
    private static PauseTransition ptChecker = null;

    public static void start() {
        LoggerFacade.getDefault().info(JobCheckNavigationDreamBook.class, "Start job for DreamBook-Navigation...");
        
        ptChecker = new PauseTransition();
        ptChecker.setDuration(CHECKER_DURATION);
        ptChecker.setOnFinished((ActionEvent event) -> {
            ActionFacade.getDefault().handle(ACTION__JOB_CHECK_NAVIGATION__DREAMBOOK);
        
            if (ptChecker.getDelay().equals(CHECKER_DURATION)) {
                ptChecker.setDelay(Duration.ZERO);
            }
            ptChecker.playFromStart();
        });
        
        ptChecker.playFromStart();
    }
    
    public static void stop() {
        LoggerFacade.getDefault().info(JobCheckNavigationDreamBook.class, "Stop job for DreamBook-Navigation");
        
        if (
                ptChecker != null
                && ptChecker.getStatus().equals(Animation.Status.RUNNING)
        ) {
            ptChecker.stop();
        }
    }
    
}
