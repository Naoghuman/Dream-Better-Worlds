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

import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.file.reflection.IReflectionConfiguration;
import de.pro.dbw.file.reflection.api.ReflectionCommentModel;
import de.pro.dbw.file.reflection.api.ReflectionModel;
import de.pro.lib.database.api.DatabaseFacade;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javafx.collections.FXCollections;

/**
 *
 * @author PRo
 */
public class ReflectionSqlProvider implements IDefaultIdConfiguration, IReflectionConfiguration {
    
    private static ReflectionSqlProvider instance = null;
    
    public static ReflectionSqlProvider getDefault() {
        if (instance == null) {
            instance = new ReflectionSqlProvider();
        }
        
        return instance;
    }
    
    private ReflectionSqlProvider() {}
    
    public void createOrUpdate(ReflectionModel model) {
        // All in one transaction
        DatabaseFacade.INSTANCE.getDatabase().getCrudService().beginTransaction();
        
        final List<ReflectionCommentModel> reflectionCommentModelsToDelete = FXCollections.observableArrayList();
        for (Iterator iterator = model.getReflectionCommentModels().iterator(); iterator.hasNext();) {
            final Object next = iterator.next();
            if (!(next instanceof ReflectionCommentModel)) {
                continue;
            }
            
            final ReflectionCommentModel reflectionCommentModel = (ReflectionCommentModel) next;
            
            // Check if for deletion
            if (reflectionCommentModel.isMarkAsDeleted()) {
                reflectionCommentModelsToDelete.add(reflectionCommentModel);
                iterator.remove();
                continue;
            }
            
            // Check if for saving
            if (Objects.equals(reflectionCommentModel.getId(), FILE__REFLECTION_COMMENT__DEFAULT_ID)) {
                reflectionCommentModel.setId(reflectionCommentModel.getGenerationTime());
            }
        }
        
        if (Objects.equals(model.getId(), FILE__REFLECTION__DEFAULT_ID)) {
            model.setId(System.currentTimeMillis());
            DatabaseFacade.INSTANCE.getDatabase().getCrudService().create(model, Boolean.FALSE);
        }
        else {
            DatabaseFacade.INSTANCE.getDatabase().getCrudService().update(model, Boolean.FALSE);
        }
        
        for (ReflectionCommentModel reflectionCommentModelToDelete : reflectionCommentModelsToDelete) {
            DatabaseFacade.INSTANCE.getDatabase().getCrudService().delete(ReflectionCommentModel.class,
                    reflectionCommentModelToDelete.getId(), Boolean.FALSE);
        }
        
        DatabaseFacade.INSTANCE.getDatabase().getCrudService().commitTransaction();
    }
    
    public void deleteReflectionWithAllComments(Long idToDelete) {
        DatabaseFacade.INSTANCE.getDatabase().getCrudService().delete(ReflectionModel.class, idToDelete);
    }
    
    public ReflectionModel findReflectionById(Long reflectionId) {
        final ReflectionModel model = DatabaseFacade.INSTANCE.getDatabase().getCrudService().findById(
                ReflectionModel.class, reflectionId);
        Collections.sort(model.getReflectionCommentModels());
        
        return model;
    }
    
}
