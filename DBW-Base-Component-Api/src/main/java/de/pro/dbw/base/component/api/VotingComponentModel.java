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
package de.pro.dbw.base.component.api;

import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import de.pro.dbw.util.api.IDateConverter;
import de.pro.dbw.util.impl.DateConverter;
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
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * TODO parameter
 VotingComponentModel
   - (v)id,
 * - (v)title,
 * - (v)fromDate,
 * - (v)toDate,
 * - (v)generationTime
 * - ( )voteid(id für die individuelle übung)
 *       - wie in reflectionfile? (List<XY>)
 * 
  - das model ist für die voting-komponente. chooser, editor, navigation
  - model wird nicht in db gespeichert.
     - model doch speichern, aber dann kein VotingExerciseModel
 
 Neues Model
 VotingExerciseModel
  - id, ()parentid, parentvoteid, List VotingRatingModel
  - das model ist für das speichern einer einzelnen VotingÜbung mit allen Ratings.
 * 
 * VotingRatingModel
 *  - id, parentid rating, ratingdate
 *  - stellt ein klick (voting) dar. Wert + Klickzeit
 * 
 * Job
 *  - kontrolliert die angezeigten Votings in Navigation.
 *  - Wenn nicht vorhanden, aber aktiv, dann anzeigen.
 *  - wenn abgelaufen, dann entfernen, wenn vorhanden.
 *     - show message?
 *
 * @author PRo
 */
public class VotingComponentModel implements Comparable<VotingComponentModel>, 
        Externalizable, IDateConverter, IDefaultIdConfiguration, IUtilConfiguration
{
    private static final long serialVersionUID = 1L;
    
    public VotingComponentModel() {
        this.initialize();
    }
    
    private void initialize() {
        markAsChangedProperty = new SimpleBooleanProperty(Boolean.FALSE);
    }
    
    // START  ID ---------------------------------------------------------------
    private LongProperty idProperty;
    private long _id = FILE__DREAM__DEFAULT_ID;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
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
            idProperty = new SimpleLongProperty(this, "id", _id);
        }
        return idProperty;
    }
    // END  ID -----------------------------------------------------------------
    
    // START  GENERATIONTIME ---------------------------------------------------
    private LongProperty generationTimeProperty;
    private long _generationTime = System.currentTimeMillis();

    @Column(name = "generationtime")
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
            generationTimeProperty = new SimpleLongProperty(this, "generationtime", _generationTime);
        }
        return generationTimeProperty;
    }
    // END  GENERATIONTIME -----------------------------------------------------
    
    // START  FROM-DATE --------------------------------------------------------
    private LongProperty fromDateProperty;
    private long _fromDate = System.currentTimeMillis();

    @Column(name = "fromDate")
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
            fromDateProperty = new SimpleLongProperty(this, "fromDate", _fromDate);
        }
        return fromDateProperty;
    }
    // END  FROM-DATE ----------------------------------------------------------
    
    // START  TO-DATE ----------------------------------------------------------
    private LongProperty toDateProperty;
    private long _toDate = System.currentTimeMillis();

    @Column(name = "toDate")
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
            toDateProperty = new SimpleLongProperty(this, "toDate", _toDate);
        }
        return toDateProperty;
    }
    // END  TO-DATE ------------------------------------------------------------
    
    // START  TITLE -------------------------------------------------------------
    private StringProperty titleProperty = null;
    private String _title = SIGN__EMPTY;
    
    @Column(name = "title")
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
            titleProperty = new SimpleStringProperty(this, "title", _title);
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
    
    @Transient
    public String getInfoActiveDuring() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Active during "); // NOI18N
        
        final String convertedFromDate = UtilProvider.getDefault().getDateConverter()
                .convertLongToDateTime(this.getFromDate(), PATTERN__DATE);
        sb.append(convertedFromDate);
        
        sb.append(" - "); // NOI18N
        
        final String convertedToDate = UtilProvider.getDefault().getDateConverter()
                .convertLongToDateTime(this.getToDate(), PATTERN__DATE);
        sb.append(convertedToDate);
        
        return sb.toString();
    }

    @Transient
    public double getLastVote() {
        /*
        TODO get last vote
         - get vote from this day
         - return vote
        */
        return 1.2d;
    }

    private transient boolean hasVotedToDay = Math.random() < 0.5;// XXX remove it
    @Transient
    public boolean hasVotedToDay() {
        /*
        TODO check if on this date voted
         - get all votes
         - look if this date is voted
        */
        
        return false; //hasVotedToDay;
    }
    
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
        final VotingComponentModel other = (VotingComponentModel) obj;
        if (this.getId() != other.getId()) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public int compareTo(VotingComponentModel other) {
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
        sb.append("Voting[");
        sb.append("id=").append(this.getId());
        sb.append(", title=").append(this.getTitle());
        sb.append(", generationtime=").append(this.getGenerationTime());
        sb.append("]");
        
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
