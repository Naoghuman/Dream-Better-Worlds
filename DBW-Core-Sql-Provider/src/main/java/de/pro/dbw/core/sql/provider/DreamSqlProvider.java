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

import de.pro.dbw.file.dream.api.DreamModel;
import de.pro.lib.database.api.DatabaseFacade;
import java.util.Objects;

/**
 *
 * @author PRo
 */
public class DreamSqlProvider {
    
    private static DreamSqlProvider instance = null;
    
    public static DreamSqlProvider getDefault() {
        if (instance == null) {
            instance = new DreamSqlProvider();
        }
        
        return instance;
    }
    
    private DreamSqlProvider() {}
    
    public void create(DreamModel model) {
        DatabaseFacade.INSTANCE.getCrudService().create(model);
    }
    
    public void createOrUpdate(DreamModel model, Long defaultId) {
        if (Objects.equals(model.getId(), defaultId)) {
            model.setId(System.currentTimeMillis());
            DatabaseFacade.INSTANCE.getCrudService().create(model);
        }
        else {
            DatabaseFacade.INSTANCE.getCrudService().update(model);
        }
    }
    
    public void delete(Long idToDelete) {
        DatabaseFacade.INSTANCE.getCrudService().delete(DreamModel.class, idToDelete);
    }
    
    public DreamModel findById(Long dreamId) {
        final DreamModel model = DatabaseFacade.INSTANCE.getCrudService().findById(DreamModel.class, dreamId);
        
        return model;
    }
    
}
