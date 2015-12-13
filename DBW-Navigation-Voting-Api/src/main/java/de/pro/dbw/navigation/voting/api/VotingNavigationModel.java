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
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

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
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.getIdToOpen())
                .append(this.getGenerationTime())
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj == this) {
            return false;
        }
        
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        
        final VotingNavigationModel other = (VotingNavigationModel) obj;
        return new EqualsBuilder()
                .append(this.getIdToOpen(), other.getIdToOpen())
                .append(this.getGenerationTime(), other.getGenerationTime())
                .isEquals();
    }
    
    @Override
    public int compareTo(VotingNavigationModel other) {
        return new CompareToBuilder()
                .append(other.getGenerationTime(), this.getGenerationTime())
                .append(other.getIdToOpen(), this.getIdToOpen())
                .toComparison();
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("idToOpen", this.getIdToOpen()) // NOI18N
                .append("generationtime", this.getGenerationTime()) // NOI18N
                .toString();
    }
    
}
