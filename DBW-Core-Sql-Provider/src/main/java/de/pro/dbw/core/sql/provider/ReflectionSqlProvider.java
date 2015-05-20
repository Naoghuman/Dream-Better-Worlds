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

import de.pro.dbw.file.reflection.api.ReflectionModel;
import de.pro.lib.database.api.DatabaseFacade;
import java.util.Objects;

/**
 *
 * @author PRo
 */
public class ReflectionSqlProvider {
    
    private static ReflectionSqlProvider instance = null;
    
    public static ReflectionSqlProvider getDefault() {
        if (instance == null) {
            instance = new ReflectionSqlProvider();
        }
        
        return instance;
    }
    
    private ReflectionSqlProvider() {}
    
    public void createOrUpdate(ReflectionModel model, Long defaultId) {
        if (Objects.equals(model.getId(), defaultId)) {
            model.setId(System.currentTimeMillis());
            DatabaseFacade.getDefault().getCrudService().create(model);
        }
        else {
            DatabaseFacade.getDefault().getCrudService().update(model);
        }
    }
    
    public void delete(Long idToDelete) {
        DatabaseFacade.getDefault().getCrudService().delete(ReflectionModel.class, idToDelete);
    }
    
    public ReflectionModel findById(Long reflectionId) {
        final ReflectionModel model = DatabaseFacade.getDefault().getCrudService()
                .findById(ReflectionModel.class, reflectionId);
        
        return model;
    }
}
