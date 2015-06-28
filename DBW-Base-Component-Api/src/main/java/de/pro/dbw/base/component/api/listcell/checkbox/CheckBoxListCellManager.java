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
package de.pro.dbw.base.component.api.listcell.checkbox;

import java.util.List;

/**
 *
 * @author PRo
 */
public class CheckBoxListCellManager {
    
    public static int maxSelectedCheckBoxes = 3;
    public static int currentSelectedCheckBoxes = 0;
    
    public static final void check(int addToSelectedCheckBoxes, List<CheckBoxListCellModel> elements) {
        currentSelectedCheckBoxes = currentSelectedCheckBoxes + addToSelectedCheckBoxes;
        
        elements.stream().forEach((element) -> {
            if (maxSelectedCheckBoxes <= currentSelectedCheckBoxes) {
                element.setDisable(!element.isSelected());
            } else {
                if (element.isDisable()) {
                    element.setDisable(Boolean.FALSE);
                }
            }
        });
    }
    
    public static final void configure(int maxSelectedCheckBoxes) {
        configure(maxSelectedCheckBoxes, 0, null);
    }
    
    public static final void configure(int maxSelectedCheckBoxes, List<CheckBoxListCellModel> elements) {
        configure(maxSelectedCheckBoxes, 0, elements);
    }
    
    public static final void configure(int maxSelectedCheckBoxes, int currentSelectedCheckBoxes) {
        configure(maxSelectedCheckBoxes, currentSelectedCheckBoxes, null);
    }
    
    public static final void configure(
            int maxSelectedCheckBoxes, int currentSelectedCheckBoxes,
            List<CheckBoxListCellModel> elements
    ) {
        CheckBoxListCellManager.maxSelectedCheckBoxes = maxSelectedCheckBoxes;
        CheckBoxListCellManager.currentSelectedCheckBoxes = currentSelectedCheckBoxes;
        
        if (elements == null || elements.isEmpty()) {
            return;
        }
        
        CheckBoxListCellManager.check(0, elements);
    }
    
}
