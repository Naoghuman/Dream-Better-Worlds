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
package de.pro.dbw.navigation.voting.api;

import javafx.scene.Parent;

/**
 *
 * @author PRo
 */
public class VotingNavigationModel implements Comparable<VotingNavigationModel> {
    
    private Long idToOpen = null;
    private Long generationTime = null;
    private String actionKey = null;
    private Parent view = null;
    
    public VotingNavigationModel() {
        this.initialize();
    }

    private void initialize() {
        idToOpen = System.currentTimeMillis();
        actionKey = ""; // XXX sign-empty
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
    }

    public Parent getView() {
        return view;
    }

    public void setView(Parent view) {
        this.view = view;
    }
    
    @Override
    public int compareTo(VotingNavigationModel other) {
        return Long.compare(other.getGenerationTime(), this.getGenerationTime());
    }
    
}
