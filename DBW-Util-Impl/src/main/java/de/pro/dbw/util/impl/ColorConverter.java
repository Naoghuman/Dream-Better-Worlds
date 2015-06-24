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
package de.pro.dbw.util.impl;

import javafx.scene.paint.Color;

/**
 *
 * @author PRo
 */
public class ColorConverter {
    
    private static final String REGEX = ";"; // NOI18N
    
    public String convertColorToString(Color color) {
        if (color == null) {
            color = Color.WHITE;
        }
        
        final StringBuilder rgba = new StringBuilder();
        rgba.append(color.getRed());
        rgba.append(REGEX);
        rgba.append(color.getGreen());
        rgba.append(REGEX);
        rgba.append(color.getBlue());
        rgba.append(REGEX);
        rgba.append(color.getOpacity());
        
        return rgba.toString();
    }
    
    public Color convertStringToColor(String color) {
        if (
                color == null
                || color.trim().isEmpty()
        ) {
            return Color.WHITE;
        }
        
        final String[] splittedColor = color.split(REGEX);
        final double red = Double.valueOf(splittedColor[0]);
        final double green = Double.valueOf(splittedColor[1]);
        final double blue = Double.valueOf(splittedColor[2]);
        final double opacity = Double.valueOf(splittedColor[3]);
        
        return new Color(red, green, blue, opacity);
    }
    
}
