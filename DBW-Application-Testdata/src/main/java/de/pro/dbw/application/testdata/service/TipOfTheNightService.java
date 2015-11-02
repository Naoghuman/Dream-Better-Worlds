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

import de.pro.dbw.application.testdata.entity.tipofthenight.TipOfTheNightPresenter;
import de.pro.dbw.application.testdata.service.loremipsum.LoremIpsum;
import de.pro.dbw.core.sql.provider.SqlProvider;
import de.pro.dbw.file.tipofthenight.api.TipOfTheNightModel;
import de.pro.dbw.util.api.IDateConverter;
import de.pro.dbw.util.provider.UtilProvider;
import de.pro.lib.database.api.DatabaseFacade;
import de.pro.lib.database.api.ICrudService;
import de.pro.lib.logger.api.LoggerFacade;
import java.util.List;
import java.util.Random;
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
public class TipOfTheNightService extends Service<Void> {
    
    private final DoubleProperty entityProperty = new SimpleDoubleProperty(0.0d);
    
    private int saveMaxEntities = 0;
    
    private TipOfTheNightPresenter presenter = null;
    private String entityName = null;
    private String onStartMessage = null;
    
    public TipOfTheNightService(String entityName) {
        this.entityName = entityName;
    }

    public void bind(TipOfTheNightPresenter presenter) {
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
        
        this.presenter.progressPropertyFromEntityDream().unbind();
        this.presenter.progressPropertyFromEntityDream().bind(super.progressProperty());
    }
    
    private long createGenerationTime() {
        final long now = System.currentTimeMillis();
        final String startTime = "01-01-2010"; // TODO create combobox where user can change the time-period
        final long convertedStartTime = UtilProvider.getDefault().getDateConverter().convertDateTimeToLong(startTime, IDateConverter.PATTERN__DATE);
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

                List<TipOfTheNightModel> tipOfTheNightModels = SqlProvider.getDefault().getTipOfTheNightProvider().findAll();
                System.out.println(" ---- " + entityName); // XXX create logger, then disable logger
                System.out.println("   before: tips found: " + tipOfTheNightModels.size());
                long id = -1_000_000_000L + tipOfTheNightModels.size();
                for (int i = 0; i < saveMaxEntities; i++) {
                    final TipOfTheNightModel model = new TipOfTheNightModel();
                    model.setGenerationTime(TipOfTheNightService.this.createGenerationTime());
                    model.setId(id++);
                    model.setTitle(LoremIpsum.getDefault().getTitle());
                    model.setText(LoremIpsum.getDefault().getTextWithMaxEntries(LoremIpsum.MAX_ENTRIES_FOR__TIP_OF_THE_NIGHT));
                    
                    crudService.create(model, false);
                    updateProgress(i, saveMaxEntities);
                    
                    if (i % 5000 == 0) {
                        crudService.commitTransaction();
                        crudService.beginTransaction();
                    }
                }

                crudService.commitTransaction();
                
                // XXX active logger, then log
                tipOfTheNightModels = SqlProvider.getDefault().getTipOfTheNightProvider().findAll();
                System.out.println("   after: tips found: " + tipOfTheNightModels.size());

                return null;
            }
        };
    }

    public void setOnStart(String onStartMessage) {
        this.onStartMessage = onStartMessage;
    }
    
    public void setOnSucceeded(String onSucceededMessage) {
        super.setOnSucceeded((WorkerStateEvent t) -> {
            LoggerFacade.INSTANCE.debug(this.getClass(), onSucceededMessage);
            
            presenter.setProgressBarInformation(onSucceededMessage);
            presenter.getProgressBarPercentInformation().textProperty().unbind();
            presenter.getProgressBarPercentInformation().setText("100%"); // NOI18N
        });
    }

    @Override
    public void start() {
        final SequentialTransition st = new SequentialTransition();
        
        PauseTransition pt = new PauseTransition();
        pt.setDuration(Duration.millis(250.0d));
        pt.setOnFinished((ActionEvent event) -> {
            LoggerFacade.INSTANCE.debug(this.getClass(), onStartMessage);
            
            presenter.setProgressBarInformation(onStartMessage);
        });
        st.getChildren().add(pt);
        
        pt = new PauseTransition();
        pt.setDuration(Duration.millis(1000.0d));
        pt.setOnFinished((ActionEvent event) -> {
            super.start();
        });
        st.getChildren().add(pt);
        
        st.playFromStart();
    }
    
}
