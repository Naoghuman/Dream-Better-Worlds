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

import de.pro.dbw.application.testdata.service.loremipsum.LoremIpsum;
import de.pro.dbw.core.sql.provider.SqlProvider;
import de.pro.dbw.file.tipofthenight.api.TipOfTheNightModel;
import de.pro.lib.database.api.DatabaseFacade;
import de.pro.lib.database.api.ICrudService;
import java.util.List;
import javafx.concurrent.Task;

/**
 *
 * @author PRo
 */
public class TipOfTheNightService extends AbstractService {
    
    public TipOfTheNightService(String entityName) {
        super(entityName);
    }

    @Override
    protected Task createTask() {
        return new Task<Void>() {
            {
                updateProgress(0, getMaxEntities());
            }
            
            @Override
            protected Void call() throws Exception {
                final String entityName = TipOfTheNightService.this.getEntityName();
                final ICrudService crudService = DatabaseFacade.INSTANCE.getCrudService(entityName);
                crudService.beginTransaction();

                List<TipOfTheNightModel> tipOfTheNightModels = SqlProvider.getDefault().getTipOfTheNightProvider().findAll();
                System.out.println(" ---- " + entityName);
                System.out.println("   before: tips found: " + tipOfTheNightModels.size());
                long id = -1_000_000_000L + tipOfTheNightModels.size();
                for (int i = 0; i < getMaxEntities(); i++) {
                    final TipOfTheNightModel model = new TipOfTheNightModel();
                    model.setGenerationTime(getGenerationTime());
                    model.setId(id++);
                    model.setTitle(LoremIpsum.getDefault().getTitle());
                    model.setText(LoremIpsum.getDefault().getTextWithMaxEntries(LoremIpsum.MAX_ENTRIES_FOR__TIP_OF_THE_NIGHT));
                    
                    crudService.create(model, false);
                    updateProgress(i, getMaxEntities());
                    
                    if (i % 5000 == 0) {
                        crudService.commitTransaction();
                        crudService.beginTransaction();
                    }
                }

                crudService.commitTransaction();
                
                // XXX test
                tipOfTheNightModels = SqlProvider.getDefault().getTipOfTheNightProvider().findAll();
                System.out.println("   after: tips found: " + tipOfTheNightModels.size());

                return null;
            }
        };
    }
    
}
