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

import de.pro.dbw.core.configuration.api.file.reflection.IReflectionConfiguration;
import de.pro.dbw.file.reflection.api.ReflectionCommentModel;
import de.pro.dbw.file.reflection.api.ReflectionModel;
import de.pro.lib.database.api.DatabaseFacade;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.collections.FXCollections;

/**
 *
 * @author PRo
 */
public class ReflectionSqlProvider implements IReflectionConfiguration {
    
    private static ReflectionSqlProvider instance = null;
    
    public static ReflectionSqlProvider getDefault() {
        if (instance == null) {
            instance = new ReflectionSqlProvider();
        }
        
        return instance;
    }
    
    private ReflectionSqlProvider() {}
    
    public void createOrUpdate(ReflectionCommentModel model, Long defaultId) {
        if (Objects.equals(model.getId(), defaultId)) {
            model.setId(System.currentTimeMillis());
            DatabaseFacade.getDefault().getCrudService().create(model);
        }
        else {
            DatabaseFacade.getDefault().getCrudService().update(model);
        }
    }
    
    public void createOrUpdate(ReflectionModel model, Long defaultId) {
        if (Objects.equals(model.getId(), defaultId)) {
            model.setId(System.currentTimeMillis());
            DatabaseFacade.getDefault().getCrudService().create(model);
        }
        else {
            DatabaseFacade.getDefault().getCrudService().update(model);
        }
    }
    
    public void delete(Class clazz, Long idToDelete) {
        DatabaseFacade.getDefault().getCrudService().delete(clazz, idToDelete);
        
        // TODO need also delete all comments from this idToDelete (delete all where parentId=idToDelete
    }
    
    public List<ReflectionCommentModel> findAllComments(Long parentId) {
        final Map<String, Object> parameters = FXCollections.observableHashMap();
        parameters.put(PARA__REFLECTION_MODEL__PARENTID, parentId);
        
        final List<ReflectionCommentModel> reflectionCommentModels = 
                DatabaseFacade.getDefault().getCrudService().findByNamedQuery(
                        ReflectionCommentModel.class, REFLECTION_COMMENT_MODEL__FIND_ALL_COMMENTS, 
                        parameters);
        Collections.sort(reflectionCommentModels);
        
        return reflectionCommentModels;
    }
    
    public ReflectionModel findById(Long reflectionId) {
        final ReflectionModel model = DatabaseFacade.getDefault().getCrudService()
                .findById(ReflectionModel.class, reflectionId);
        
        return model;
    }
    
}
