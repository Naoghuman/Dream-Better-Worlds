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
import de.pro.dbw.application.testdata.entity.dream.DreamPresenter;
import de.pro.dbw.application.testdata.service.loremipsum.LoremIpsum;
import de.pro.dbw.file.dream.api.DreamModel;
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

/**
 *
 * @author PRo
 */
public class TemplateService extends Service<Void> {
    
    private final DoubleProperty entityProperty = new SimpleDoubleProperty(0.0d);
    
    private int saveMaxEntities = 0;
    
    private DreamPresenter presenter = null;// XXX replace
    private String entityName = null;
    private String onStartMessage = null;
    
    public TemplateService(String entityName) {
        this.entityName = entityName;
    }

    public void bind(DreamPresenter presenter) {
        this.presenter = presenter;
        
        saveMaxEntities = presenter.getSaveMaxEntities();
        
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
        
        this.presenter.progressPropertyFromEntityDream().unbind();// XXX replace
        this.presenter.progressPropertyFromEntityDream().bind(super.progressProperty());// XXX replace
    }
    
    private long createGenerationTime() {
        final String startTime = "01-01-2010"; // TODO create combobox where user can change the time-period
        final long convertedStartTime = UtilProvider.getDefault().getDateConverter().convertDateTimeToLong(startTime, IDateConverter.PATTERN__DATE);
        final long now = System.currentTimeMillis();
        final long timePeriod = now - convertedStartTime;
        final long generationTime = now - UtilProvider.getDefault().getDateConverter().getLongInPeriodFromNowTo(timePeriod);
        
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
                final ICrudService crudService = DatabaseFacade.INSTANCE.getCrudService(entityName);
                crudService.beginTransaction();

                long count = DatabaseFacade.INSTANCE.getCrudService().count(entityName);
            	LoggerFacade.INSTANCE.debug(this.getClass(), "Found " + count + " before testdata generation."); // NOI18N
            	LoggerFacade.INSTANCE.debug(this.getClass(), "Create " + saveMaxEntities + " XY as testdata..."); // NOI18N
		LoggerFacade.INSTANCE.deactivate(Boolean.TRUE);// XXX replace at top

                long id = -1_000_000_000L + count;
                for (int i = 1; i <= saveMaxEntities; i++) {
                    final DreamModel model = new DreamModel();// XXX replace
                    model.setGenerationTime(TemplateService.this.createGenerationTime());
                    model.setDescription(LoremIpsum.getDefault().getDescription());
                    model.setId(id++);
                    model.setTitle(LoremIpsum.getDefault().getTitle());
                    model.setText(LoremIpsum.getDefault().getText());
                    
                    crudService.create(model, false);
                    updateProgress(i - 1, saveMaxEntities);
                    
                    if (i % 5000 == 0) {
                        crudService.commitTransaction();
                        crudService.beginTransaction();
                    }
                }

                crudService.commitTransaction();
                
		LoggerFacade.INSTANCE.deactivate(Boolean.FALSE);
                count = DatabaseFacade.INSTANCE.getCrudService().count(entityName);// XXX replace above
            	LoggerFacade.INSTANCE.debug(this.getClass(), "Found " + count + " XY after testdata generation."); // NOI18N

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
            LoggerFacade.INSTANCE.debug(this.getClass(), onStartMessage);
            
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

