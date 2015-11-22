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
package de.pro.dbw.feature.voting.api;

import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import de.pro.dbw.core.configuration.api.feature.voting.IVotingConfiguration;
import de.pro.dbw.util.api.IDateConverter;
import de.pro.dbw.util.provider.UtilProvider;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author PRo
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = IVotingConfiguration.ENTITY__TABLE_NAME__VOTING_ELEMENT_MODEL)
@NamedQueries({
    @NamedQuery(
            name = IVotingConfiguration.NAMED_QUERY__NAME__FIND_ALL,
            query = IVotingConfiguration.NAMED_QUERY__QUERY__FIND_ALL)
})
public class VotingElementModel implements Comparable<VotingElementModel>, 
        Externalizable, IDateConverter, IDefaultIdConfiguration, IUtilConfiguration,
        IVotingConfiguration
{
    private static final long serialVersionUID = 1L;
    
    public VotingElementModel() {
        this.initialize();
    }
    
    private void initialize() {
        markAsChangedProperty = new SimpleBooleanProperty(Boolean.FALSE);
    }
    
    // START  ID ---------------------------------------------------------------
    private LongProperty idProperty;
    private long _id = FEATURE__VOTING__DEFAULT_ID;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = VOTING_ELEMENT_MODEL__COLUMN_NAME__ID)
    public long getId() {
        if (this.idProperty == null) {
            return _id;
        } else {
            return idProperty.get();
        }
    }

    public final void setId(long id) {
        if (this.idProperty == null) {
            _id = id;
        } else {
            this.idProperty.set(id);
        }
    }

    public LongProperty idProperty() {
        if (idProperty == null) {
            idProperty = new SimpleLongProperty(this, 
                    VOTING_ELEMENT_MODEL__COLUMN_NAME__ID, _id);
        }
        return idProperty;
    }
    // END  ID -----------------------------------------------------------------
    
    // START  GENERATIONTIME ---------------------------------------------------
    private LongProperty generationTimeProperty;
    private long _generationTime = System.currentTimeMillis();

    @Column(name = VOTING_ELEMENT_MODEL__COLUMN_NAME__GENERATION_TIME)
    public long getGenerationTime() {
        if (this.generationTimeProperty == null) {
            return _generationTime;
        } else {
            return generationTimeProperty.get();
        }
    }

    public final void setGenerationTime(long generationTime) {
        if (this.generationTimeProperty == null) {
            _generationTime = generationTime;
        } else {
            this.generationTimeProperty.set(generationTime);
        }
    }

    public LongProperty generationTimeProperty() {
        if (generationTimeProperty == null) {
            generationTimeProperty = new SimpleLongProperty(this, 
                    VOTING_ELEMENT_MODEL__COLUMN_NAME__GENERATION_TIME, _generationTime);
        }
        return generationTimeProperty;
    }
    // END  GENERATIONTIME -----------------------------------------------------
    
    // START  FROM-DATE --------------------------------------------------------
    private LongProperty fromDateProperty;
    private long _fromDate = System.currentTimeMillis();

    @Column(name = VOTING_ELEMENT_MODEL__COLUMN_NAME__FROM_DATE)
    public long getFromDate() {
        if (this.fromDateProperty == null) {
            return _fromDate;
        } else {
            return fromDateProperty.get();
        }
    }

    public final void setFromDate(long fromDate) {
        if (this.fromDateProperty == null) {
            _fromDate = fromDate;
        } else {
            this.fromDateProperty.set(fromDate);
        }
    }

    public LongProperty fromDateProperty() {
        if (fromDateProperty == null) {
            fromDateProperty = new SimpleLongProperty(this, 
                    VOTING_ELEMENT_MODEL__COLUMN_NAME__FROM_DATE, _fromDate);
        }
        return fromDateProperty;
    }
    // END  FROM-DATE ----------------------------------------------------------
    
    // START  TO-DATE ----------------------------------------------------------
    private LongProperty toDateProperty;
    private long _toDate = System.currentTimeMillis();

    @Column(name = VOTING_ELEMENT_MODEL__COLUMN_NAME__TO_DATE)
    public long getToDate() {
        if (this.toDateProperty == null) {
            return _toDate;
        } else {
            return toDateProperty.get();
        }
    }

    public final void setToDate(long toDate) {
        if (this.toDateProperty == null) {
            _toDate = toDate;
        } else {
            this.toDateProperty.set(toDate);
        }
    }

    public LongProperty toDateProperty() {
        if (toDateProperty == null) {
            toDateProperty = new SimpleLongProperty(this, 
                    VOTING_ELEMENT_MODEL__COLUMN_NAME__TO_DATE, _toDate);
        }
        return toDateProperty;
    }
    // END  TO-DATE ------------------------------------------------------------
    
    // START  DESCRIPTION ------------------------------------------------------
    private StringProperty descriptionProperty = null;
    private String _description = SIGN__EMPTY;
    
    @Column(name = VOTING_ELEMENT_MODEL__COLUMN_NAME__DESCRIPTION)
    public String getDescription() {
        if (this.descriptionProperty == null) {
            return _description;
        } else {
            return descriptionProperty.get();
        }
    }
    
    public void setDescription(String description) {
        if (this.descriptionProperty == null) {
            _description = description;
        } else {
            this.descriptionProperty.set(description);
        }
    }
    
    public StringProperty descriptionProperty() {
        if (descriptionProperty == null) {
            descriptionProperty = new SimpleStringProperty(this, 
                    VOTING_ELEMENT_MODEL__COLUMN_NAME__DESCRIPTION, _description);
        }
        return descriptionProperty;
    }
    // END  DESCRIPTION --------------------------------------------------------
    
    // START  TITLE -------------------------------------------------------------
    private StringProperty titleProperty = null;
    private String _title = SIGN__EMPTY;
    
    @Column(name = VOTING_ELEMENT_MODEL__COLUMN_NAME__TITLE)
    public String getTitle() {
        if (this.titleProperty == null) {
            return _title;
        } else {
            return titleProperty.get();
        }
    }
    
    public void setTitle(String title) {
        if (this.titleProperty == null) {
            _title = title;
        } else {
            this.titleProperty.set(title);
        }
    }
    
    public StringProperty titleProperty() {
        if (titleProperty == null) {
            titleProperty = new SimpleStringProperty(this, 
                    VOTING_ELEMENT_MODEL__COLUMN_NAME__TITLE, _title);
        }
        return titleProperty;
    }
    // END  TITLE --------------------------------------------------------------
    
    private transient BooleanProperty markAsChangedProperty = null;

    @Transient
    public Boolean isMarkAsChanged() {
        return markAsChangedProperty.getValue();
    }
    
    public BooleanProperty markAsChangedProperty() {
        return markAsChangedProperty;
    }
    
    public void setMarkAsChanged(Boolean isMarkAsChanged) {
        markAsChangedProperty.setValue(isMarkAsChanged);
    }
    
//    @Transient
//    public double getLastVote() {
//        /*
//        XXX move to ActiveVotingModel
//        TODO get last vote
//         - get vote from this day
//         - return vote
//        */
//        return 1.2d;
//    }

//    private transient boolean hasVotedToDay = Math.random() < 0.5;// XXX remove it
//    @Transient
//    public boolean hasVotedToDay() {
//        /*
//        XXX move to ActiveVotingModel
//        TODO check if on this date voted
//         - get all votes
//         - look if this date is voted
//        */
//        
//        return false; //hasVotedToDay;
//    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Long.hashCode(this.getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final VotingElementModel other = (VotingElementModel) obj;
        if (this.getId() != other.getId()) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public int compareTo(VotingElementModel other) {
        int compareTo = Long.compare(other.getGenerationTime(), this.getGenerationTime());
        if (compareTo != 0) {
            return compareTo;
        }
        compareTo = other.getTitle().compareTo(this.getTitle());
        if (compareTo != 0) {
            return compareTo;
        }
        
        return Long.compare(other.getId(), this.getId());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Voting["); // NOI18N
        sb.append("id=").append(this.getId()); // NOI18N
        sb.append(", title=").append(this.getTitle()); // NOI18N
        sb.append(", generationtime=").append(this.getGenerationTime()); // NOI18N
        sb.append("]"); // NOI18N
        
        return sb.toString();
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getId());
        out.writeLong(this.getGenerationTime());
        out.writeLong(this.getFromDate());
        out.writeLong(this.getToDate());
        out.writeObject(this.getTitle());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readLong());
        this.setGenerationTime(in.readLong());
        this.setFromDate(in.readLong());
        this.setToDate(in.readLong());
        this.setTitle(String.valueOf(in.readObject()));
    }
    
}
