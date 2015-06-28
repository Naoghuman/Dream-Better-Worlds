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
package de.pro.dbw.core.configuration.api.application;

import javafx.util.Duration;

/**
 *
 * @author PRo
 */
public interface IApplicationConfiguration {
    
    public static final Duration DBW__LITTLE_DELAY__DURATION_125 = Duration.millis(125.0d);
    public static final Duration DBW__LITTLE_DELAY__DURATION_250 = Duration.millis(250.0d);
    
    public static final String DBW__RESOURCE_BUNDLE = "/de/pro/dbw/application/DreamBetterWorlds.properties"; // NOI18N
    
    public static final String KEY__APPLICATION__BORDER_SIGN = "application.border.sign"; // NOI18N
    public static final String KEY__APPLICATION__DATABASE = "application.database"; // NOI18N
    public static final String KEY__APPLICATION__PREFIX_NEW = "application.prefix.new"; // NOI18N
    public static final String KEY__APPLICATION__TESTDATA_MESSAGE_START = "application.testdata.message.start"; // NOI18N
    public static final String KEY__APPLICATION__TESTDATA_MESSAGE_STOP = "application.testdata..message.stop"; // NOI18N
    public static final String KEY__APPLICATION__TESTDATA_TITLE = "application.testdata.title"; // NOI18N
    
    public static final String KEY__FEATURE_TAG__TAG_CATEGORY_CHOOSER_TITLE = "feature.tag.category.chooser.title"; // NOI18N
    public static final String KEY__FEATURE_TAG__TAG_CATEGORY_EDTIOR_TITLE = "feature.tag.category.editor.title"; // NOI18N
    public static final String KEY__FEATURE_TAG__TAG_EDTIOR_TITLE = "feature.tag.editor.title"; // NOI18N
    
}
