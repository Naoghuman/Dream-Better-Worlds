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
package de.pro.dbw.application.testdata.service;

import de.pro.dbw.application.testdata.component.extendedprogressbar.ExtendedProgressBarPresenter;
import de.pro.lib.logger.api.LoggerFacade;
import java.util.Random;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.util.Duration;

/**
 *
 * @author PRo
 */
public abstract class AbstractService extends Service<Void> {
    
    private static final Random RANDOM = new Random();
    
    private int maxEntities = 0;
    
    private ExtendedProgressBarPresenter extendedProgressBarPresenter = null;
    private String entityName = null;
    private String onStartMessage = null;
    
    public AbstractService(String entityName) {
        this.entityName = entityName;
    }

    public void bind(ExtendedProgressBarPresenter extendedProgressBarPresenter, int maxEntities) {
        this.extendedProgressBarPresenter = extendedProgressBarPresenter;
        this.maxEntities = maxEntities;
        
        extendedProgressBarPresenter.entityProperty().unbind();
        extendedProgressBarPresenter.getProgressBar().progressProperty().unbind();
        
        extendedProgressBarPresenter.setMaxEntities(500);
        extendedProgressBarPresenter.entityProperty().setValue(0);
        extendedProgressBarPresenter.entityProperty().bind(super.progressProperty());
        extendedProgressBarPresenter.getProgressBar().progressProperty().bind(super.progressProperty());
    }
    
    @Override
    protected abstract Task<Void> createTask();
    
    protected String getEntityName() {
        return entityName;
    }
    
    protected ExtendedProgressBarPresenter getExtendedProgressBarPresenter() {
        return extendedProgressBarPresenter;
    }
    
    protected long getGenerationTime() {
        return System.currentTimeMillis() - RANDOM.nextLong();
    }
    
    protected int getMaxEntities() {
        return maxEntities;
    }
    
    public void setOnStart(String onStartMessage) {
        this.onStartMessage = onStartMessage;
    }
    
    public void setOnSucceeded(String onSucceededMessage) {
        super.setOnSucceeded((WorkerStateEvent t) -> {
            extendedProgressBarPresenter.setTextForProgress(onSucceededMessage);

            LoggerFacade.INSTANCE.debug(this.getClass(), onSucceededMessage);
        });
    }

    @Override
    public void start() {
        final SequentialTransition st = new SequentialTransition();
        
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.millis(1000.0d));//Duration.ZERO);
        pt.setOnFinished((ActionEvent event) -> {
            LoggerFacade.INSTANCE.debug(this.getClass(), onStartMessage);

            extendedProgressBarPresenter.setTextForProgress(onStartMessage);
        });
        st.getChildren().add(pt);
        
        pt = new PauseTransition();
        pt.setDuration(Duration.millis(250.0d));
        pt.setOnFinished((ActionEvent event) -> {
            super.start();
        });
        st.getChildren().add(pt);
        
        st.playFromStart();
    }
    
}
