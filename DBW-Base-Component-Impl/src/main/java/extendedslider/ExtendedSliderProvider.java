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
package extendedslider;

import javafx.scene.control.Button;

/**
 *
 * @author PRo
 */
public final class ExtendedSliderProvider {
    
    private static ExtendedSliderProvider instance = null;
    
    public static ExtendedSliderProvider getDefault() {
        if (instance == null) {
            instance = new ExtendedSliderProvider();
        }
        
        return instance;
    }
    
    private ExtendedSliderProvider() {
        this.init();
    }
    
    private void init() {
        
    }
    
}
