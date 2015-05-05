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
package de.pro.dbw.navigation.history.api;

import de.pro.dbw.util.provider.UtilProvider;
import javafx.scene.Parent;

/**
 *
 * @author PRo
 */
public class HistoryNavigationModel {
    
    public static final Long GENERATION_TIME_FOR_PARENT = -1L;
    
    private Boolean hasPrefixNew = Boolean.FALSE;
    private Long generationTime = null;
    private Long idToOpen = null;
    private String actionKey = null;
    private Parent view = null;

    public HistoryNavigationModel() {
        this.initialize();
    }
    
    private void initialize() {
        generationTime = GENERATION_TIME_FOR_PARENT;
        idToOpen = System.currentTimeMillis();
        actionKey = "";
    }
    
    public Boolean hasPrefixNew() {
        return hasPrefixNew;
    }

    public String getActionKey() {
        return actionKey;
    }

    public void setActionKey(String actionKey) {
        this.actionKey = actionKey;
    }

    public Long getIdToOpen() {
        return idToOpen;
    }

    public void setIdToOpen(Long idToOpen) {
        this.idToOpen = idToOpen;
    }
    
    public Long getGenerationTime() {
        return generationTime;
    }
    
    public void setGenerationTime(Long generationTime) {
        this.generationTime = generationTime;
        
        hasPrefixNew = UtilProvider.getDefault().getDateConverter().isAfter(-3, generationTime);
    }

    public Parent getView() {
        return view;
    }

    public void setView(Parent view) {
        this.view = view;
    }
    
}
