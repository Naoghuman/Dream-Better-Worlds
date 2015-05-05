/*
 * Copyright (C) 2014 Dream Better Worlds
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
package de.pro.dbw.core.configuration.api.file;

/**
 * TODO beschreibt die art einer datei
 *
 * @author PRo
 */
public enum EFileType {
    
    DREAM("", ""), // TODO add color, image(for button, tab...)
    DREAMMAP("", ""),
    EXERCISE("", ""),
    FASTDREAM("", ""),
    FIVE_POINTS("", ""),
    TIPOFTHENIGHT("", ""),
    WELCOME("", "");
    
    private String colorKey = null;
    private String imageKey = null;
    
    EFileType(String colorKey, String imageKey) {
        this.colorKey = colorKey;
        this.imageKey = imageKey;
    }

    public String getColorKey() {
        return colorKey;
    }

    public String getImageKey() {
        return imageKey;
    }
    
}
