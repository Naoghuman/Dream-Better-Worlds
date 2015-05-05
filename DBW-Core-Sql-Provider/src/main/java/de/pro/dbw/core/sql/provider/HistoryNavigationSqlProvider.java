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
import de.pro.dbw.file.dream.api.DreamModel;
import de.pro.dbw.util.provider.UtilProvider;
import de.pro.lib.database.api.DatabaseFacade;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;

/**
 *
 * @author PRo
 */
public class HistoryNavigationSqlProvider implements IDreamConfiguration {
    
    private static HistoryNavigationSqlProvider instance = null;
    
    public static HistoryNavigationSqlProvider getDefault() {
        if (instance == null) {
            instance = new HistoryNavigationSqlProvider();
        }
        
        return instance;
    }
    
    private HistoryNavigationSqlProvider() {}
    
    public List<DreamModel> findAll(int addDays) {
        final Map<String, Object> parameters = FXCollections.observableHashMap();
        parameters.put(
                PARA__DREAM_FILE_MODEL__GENERATIONTIME,
                UtilProvider.getDefault().getDateConverter().addDays(addDays));
        final List<DreamModel> dreams = DatabaseFacade.getDefault().getCrudService().findByNamedQuery(DreamModel.class, DREAM_MODEL__FIND_ALL_FOR_NAVIGATION__HISTORY, parameters);
        Collections.sort(dreams);
        
        return dreams;
    }
    
}
