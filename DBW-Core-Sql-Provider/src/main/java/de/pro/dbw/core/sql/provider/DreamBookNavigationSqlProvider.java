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

import de.pro.dbw.core.configuration.api.file.dream.IDreamConfiguration;
import de.pro.dbw.core.configuration.api.file.reflection.IReflectionConfiguration;
import de.pro.dbw.file.dream.api.DreamModel;
import de.pro.dbw.file.reflection.api.ReflectionModel;
import de.pro.lib.database.api.DatabaseFacade;
import java.util.List;

/**
 *
 * @author PRo
 */
public class DreamBookNavigationSqlProvider {
    
    private static DreamBookNavigationSqlProvider instance = null;
    
    public static DreamBookNavigationSqlProvider getDefault() {
        if (instance == null) {
            instance = new DreamBookNavigationSqlProvider();
        }
        
        return instance;
    }
    
    private DreamBookNavigationSqlProvider() {}
    
    public List<DreamModel> findAllDreams() {
        final List<DreamModel> dreams = DatabaseFacade.getDefault().getCrudService().findByNamedQuery(
                DreamModel.class, IDreamConfiguration.NAMED_QUERY__NAME__FIND_ALL);
            
        return dreams;
    }
    
    public List<ReflectionModel> findAllReflections() {
        final List<ReflectionModel> reflections = DatabaseFacade.getDefault().getCrudService().findByNamedQuery(
                ReflectionModel.class, IReflectionConfiguration.NAMED_QUERY__NAME__FIND_ALL);
            
        return reflections;
    }
    
}
