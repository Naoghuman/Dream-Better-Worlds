/*
 * Copyright (C) 2015 PRo
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
package de.pro.dbw.core.configuration.api.file.dream;

/**
 *
 * @author PRo
 */
public interface IDreamConfiguration {

    public static final String ENTITY__TABLE_NAME__DREAM_MODEL = "DreamModel"; // NOI18N
    
    public static final String NAMED_QUERY__NAME__FIND_ALL = "DreamModel.findAll"; // NOI18N
    public static final String NAMED_QUERY__QUERY__FIND_ALL = "SELECT d FROM DreamModel d"; // NOI18N
    
    public static final String NAMED_QUERY__NAME__FIND_ALL_FOR_NAVIGATION_HISTORY = "DreamModel.findAllForNavigationHistory"; // NOI18N
    public static final String NAMED_QUERY__QUERY__FIND_ALL_FOR_NAVIGATION_HISTORY = "SELECT d FROM DreamModel d WHERE d.generationTime > :generationTime"; // NOI18N
    
    public static final String DREAM_MODEL__COLUMN_NAME__DESCRIPTION = "description"; // NOI18N
    public static final String DREAM_MODEL__COLUMN_NAME__GENERATION_TIME = "generationTime"; // NOI18N
    public static final String DREAM_MODEL__COLUMN_NAME__ID = "id"; // NOI18N
    public static final String DREAM_MODEL__COLUMN_NAME__FAVORITE = "favorite"; // NOI18N
    public static final String DREAM_MODEL__COLUMN_NAME__FAVORITE_REASON = "favoritereason"; // NOI18N
    public static final String DREAM_MODEL__COLUMN_NAME__TEXT = "text"; // NOI18N
    public static final String DREAM_MODEL__COLUMN_NAME__TITLE = "title"; // NOI18N
    
}
