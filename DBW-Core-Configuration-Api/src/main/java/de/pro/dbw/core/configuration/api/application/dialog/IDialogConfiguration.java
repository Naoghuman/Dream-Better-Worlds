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
package de.pro.dbw.core.configuration.api.application.dialog;

import java.awt.Dimension;

/**
 *
 * @author PRo
 */
public interface IDialogConfiguration {
    
    // TODO all sizes are in the format 16:9
    public Dimension SIZE__W200_H300 = new Dimension(200, 300);
    
    public Dimension SIZE__W300_H200 = new Dimension(300, 200);
    public Dimension SIZE__W400_H450 = new Dimension(400, 450);
    public Dimension SIZE__W495_H330 = new Dimension(495, 330);
    public Dimension SIZE__W495_H414 = new Dimension(495, 414);
    public Dimension SIZE__W875_H359 = new Dimension(875, 359);
    
    public static final String KEY__DIALOG_TITLE__ABOUT = "key.dialog.title.about"; // NOI18N
    public static final String KEY__DIALOG_TITLE__DELETE = "key.dialog.title.delete"; // NOI18N
    public static final String KEY__DIALOG_TITLE__DREAM_WIZARD = "key.dialog.title.dreamwizard"; // NOI18N
    public static final String KEY__DIALOG_TITLE__REFLECTION_WIZARD = "key.dialog.title.reflectionwizard"; // NOI18N
    public static final String KEY__DIALOG_TITLE__SAVE = "key.dialog.title.save"; // NOI18N
    public static final String KEY__DIALOG_TITLE__TIP_OF_THE_NIGHT = "key.dialog.title.tipofthenight"; // NOI18N
    public static final String KEY__DIALOG_TITLE__TIP_OF_THE_NIGHT_EDITOR = "key.dialog.title.tipofthenighteditor"; // NOI18N
    
    public static final String DIALOG__RESOURCE_BUNDLE = "/de/pro/dbw/dialog/provider/DialogProvider.properties"; // NOI18N
    
}
