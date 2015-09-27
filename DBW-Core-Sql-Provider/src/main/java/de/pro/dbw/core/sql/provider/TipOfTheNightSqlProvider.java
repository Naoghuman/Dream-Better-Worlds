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
package de.pro.dbw.core.sql.provider;

import de.pro.dbw.core.configuration.api.file.tipofthenight.ITipOfTheNightConfiguration;
import de.pro.dbw.file.tipofthenight.api.TipOfTheNightModel;
import de.pro.lib.database.api.DatabaseFacade;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author PRo
 */
public class TipOfTheNightSqlProvider implements ITipOfTheNightConfiguration {
    
    private static TipOfTheNightSqlProvider instance = null;
    
    public static TipOfTheNightSqlProvider getDefault() {
        if (instance == null) {
            instance = new TipOfTheNightSqlProvider();
        }
        
        return instance;
    }
    
    private TipOfTheNightSqlProvider() {}
    
    
    public void createOrUpdate(TipOfTheNightModel model, Long defaultId) {
        if (Objects.equals(model.getId(), defaultId)) {
            model.setId(System.currentTimeMillis());
            DatabaseFacade.INSTANCE.getCrudService().create(model);
        }
        else {
            DatabaseFacade.INSTANCE.getCrudService().update(model);
        }
    }
    
    public void delete(Long idToDelete) {
        DatabaseFacade.INSTANCE.getCrudService().delete(TipOfTheNightModel.class, idToDelete);
    }
    
    public List<TipOfTheNightModel> findAll() {
        final List<TipOfTheNightModel> allTipsOfTheNight = DatabaseFacade.INSTANCE.getCrudService()
                .findByNamedQuery(TipOfTheNightModel.class, NAMED_QUERY__NAME__FIND_ALL);
        Collections.sort(allTipsOfTheNight);
        
        return allTipsOfTheNight;
    }
    
}
