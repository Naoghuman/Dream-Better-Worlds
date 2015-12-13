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

import de.pro.dbw.application.testdata.TestdataPresenter;
import de.pro.dbw.application.testdata.entity.reflection.ReflectionPresenter;
import de.pro.dbw.application.testdata.service.loremipsum.LoremIpsum;
import de.pro.dbw.file.reflection.api.ReflectionModel;
import de.pro.dbw.util.api.IDateConverter;
import de.pro.dbw.util.provider.UtilProvider;
import de.pro.lib.database.api.DatabaseFacade;
import de.pro.lib.database.api.ICrudService;
import de.pro.lib.logger.api.LoggerFacade;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import org.apache.commons.lang.time.StopWatch;

/**
 *
 * @author PRo
 */
public class ReflectionService extends Service<Void> {
    
    private final long now = System.currentTimeMillis();
    
    private final DoubleProperty entityProperty = new SimpleDoubleProperty(0.0d);
    
    private int saveMaxEntities = 0;
    private int timePeriod = 0;
    private long convertedTimePeriod = 0L;
    
    private ReflectionPresenter presenter = null;
    private String entityName = null;
    private String onStartMessage = null;
    
    public ReflectionService(String entityName) {
        this.entityName = entityName;
    }

    public void bind(ReflectionPresenter presenter) {
        this.presenter = presenter;
        
        saveMaxEntities = presenter.getSaveMaxEntities();
        timePeriod = presenter.getTimePeriod();
        
        String startTime = UtilProvider.getDefault().getDateConverter().convertLongToDateTime(now, IDateConverter.PATTERN__DATE);
        int year = Integer.parseInt(startTime.substring(6)) - timePeriod;
        startTime = startTime.substring(0, 6) + year;
        
        final long convertedStartTime = UtilProvider.getDefault().getDateConverter().convertDateTimeToLong(startTime, IDateConverter.PATTERN__DATE);
        convertedTimePeriod = now - convertedStartTime;
        
        entityProperty.unbind();
        entityProperty.setValue(0);
        entityProperty.bind(super.progressProperty());
        
        this.presenter.getProgressBarPercentInformation().textProperty().bind(
                Bindings.createStringBinding(() -> {
                    int process = (int) (entityProperty.getValue() * 100.0d);
                    if (process <= 0) {
                        process = 0;
                    } else {
                        ++process;
                    }

                    return process + "%"; // NOI18N
                },
                entityProperty));
        
        this.presenter.progressPropertyFromEntityReflection().unbind();
        this.presenter.progressPropertyFromEntityReflection().bind(super.progressProperty());
    }
    
    private long createGenerationTime() {
        final long generationTime = now - UtilProvider.getDefault().getDateConverter().getLongInPeriodFromNowTo(convertedTimePeriod);
        
        return generationTime;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            {
                updateProgress(0, saveMaxEntities);
            }
            
            @Override
            protected Void call() throws Exception {
                LoggerFacade.INSTANCE.deactivate(Boolean.TRUE);
                
                final StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                
                final ICrudService crudService = DatabaseFacade.INSTANCE.getCrudService(entityName);
                long id = -1_000_000_000L + DatabaseFacade.INSTANCE.getCrudService().count(entityName);
                for (int i = 1; i <= saveMaxEntities; i++) {
                    crudService.beginTransaction();
                
                    final ReflectionModel model = new ReflectionModel();
                    model.setGenerationTime(ReflectionService.this.createGenerationTime());
                    model.setId(id++);
                    model.setTitle(LoremIpsum.getDefault().getTitle());
                    model.setText(LoremIpsum.getDefault().getText());
                    
                    crudService.create(model, false);
                    updateProgress(i - 1, saveMaxEntities);
                    
                    crudService.commitTransaction();
                }
                
                LoggerFacade.INSTANCE.deactivate(Boolean.FALSE);
                stopWatch.split();
                LoggerFacade.INSTANCE.debug(this.getClass(), "  + " + stopWatch.toSplitString() + " for " + saveMaxEntities + " Reflections."); // NOI18N
		stopWatch.stop();
                
                return null;
            }
        };
    }

    public void setOnStart(String onStartMessage) {
        this.onStartMessage = onStartMessage;
    }
    
    public void setOnSuccededAfterService(TestdataPresenter testdataPresenter, String onSucceededMessage) {
        super.setOnSucceeded((WorkerStateEvent t) -> {
            LoggerFacade.INSTANCE.debug(this.getClass(), onSucceededMessage);
            
            presenter.setProgressBarInformation(onSucceededMessage);
            
            if (!presenter.getProgressBarPercentInformation().getText().equals("100%")) { // NOI18N
                presenter.getProgressBarPercentInformation().textProperty().unbind();
                presenter.getProgressBarPercentInformation().setText("100%"); // NOI18N
            }
            
            if (testdataPresenter != null) {
                testdataPresenter.cleanUpAfterServices();
            }
        });
    }

    @Override
    public void start() {
        final SequentialTransition sequentialTransition = new SequentialTransition();
        
        final PauseTransition ptProgressBarInformation = new PauseTransition();
        ptProgressBarInformation.setDuration(Duration.millis(250.0d));
        ptProgressBarInformation.setOnFinished((ActionEvent event) -> {
            presenter.setProgressBarInformation(onStartMessage);
        });
        sequentialTransition.getChildren().add(ptProgressBarInformation);
        
        final PauseTransition ptStart = new PauseTransition();
        ptStart.setDuration(Duration.millis(1000.0d));
        ptStart.setOnFinished((ActionEvent event) -> {
            super.start();
        });
        sequentialTransition.getChildren().add(ptStart);
        
        sequentialTransition.playFromStart();
    }
    
}

