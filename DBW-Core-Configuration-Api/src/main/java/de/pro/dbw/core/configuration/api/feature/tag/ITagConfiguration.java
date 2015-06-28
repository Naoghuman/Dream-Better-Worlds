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
package de.pro.dbw.core.configuration.api.feature.tag;

/**
 *
 * @author PRo
 */
public interface ITagConfiguration {
    
    public static final String ENTITY__TABLE_NAME__TAG_MODEL = "TagModel"; // NOI18N
    
    public static final String NAMED_QUERY__NAME__FIND_ALL = "TagModel.findAll"; // NOI18N
    public static final String NAMED_QUERY__QUERY__FIND_ALL = "SELECT v FROM TagModel v"; // NOI18N
    
    public static final String TAG_MODEL__COLUMN_NAME__CATEGORIES = "categories"; // NOI18N
    public static final String TAG_MODEL__COLUMN_NAME__DESCRIPTION = "description"; // NOI18N
    public static final String TAG_MODEL__COLUMN_NAME__GENERATION_TIME = "generationTime"; // NOI18N
    public static final String TAG_MODEL__COLUMN_NAME__ID = "id"; // NOI18N
    public static final String TAG_MODEL__COLUMN_NAME__TAG_CATEGORY = "tagcategory"; // NOI18N
    public static final String TAG_MODEL__COLUMN_NAME__TITLE = "title"; // NOI18N
    
    public static final String JOIN_TABLE__NAME__MAPPING_TAG_CATEGORY = "MappingTagCategory"; // NOI18N

}
