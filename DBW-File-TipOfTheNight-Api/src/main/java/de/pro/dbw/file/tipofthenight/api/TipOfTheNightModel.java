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
package de.pro.dbw.file.tipofthenight.api;

import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.file.tipofthenight.ITipOfTheNightConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javafx.beans.property.LongProperty;
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
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * TODO parameter tag: long (id, vergleich im comperator)
 * 
 * @author PRo
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = ITipOfTheNightConfiguration.ENTITY__TABLE_NAME__TIP_OF_THE_NIGHT_MODEL)
@NamedQueries({
    @NamedQuery(
            name = ITipOfTheNightConfiguration.NAMED_QUERY__NAME__FIND_ALL,
            query = ITipOfTheNightConfiguration.NAMED_QUERY__QUERY__FIND_ALL),
    @NamedQuery(
            name = ITipOfTheNightConfiguration.NAMED_QUERY__NAME__FIND_ALL_FOR_NAVIGATION_HISTORY,
            query = ITipOfTheNightConfiguration.NAMED_QUERY__QUERY__FIND_ALL_FOR_NAVIGATION_HISTORY)
})
public class TipOfTheNightModel implements Comparable<TipOfTheNightModel>, Externalizable, 
        IDefaultIdConfiguration, ITipOfTheNightConfiguration, IUtilConfiguration {

    // START  ID ---------------------------------------------------------------
    private LongProperty idProperty;
    private long _id = FILE__TIP_OF_THE_NIGHT__DEFAULT_ID;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = TIP_OF_THE_NIGHT_MODEL__COLUMN_NAME__ID)
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
            idProperty = new SimpleLongProperty(this, TIP_OF_THE_NIGHT_MODEL__COLUMN_NAME__ID, _id);
        }
        return idProperty;
    }
    // END  ID -----------------------------------------------------------------
    
    // START  GENERATIONTIME ---------------------------------------------------
    private LongProperty generationTimeProperty;
    private long _generationTime = System.currentTimeMillis();

    @Column(name = TIP_OF_THE_NIGHT_MODEL__COLUMN_NAME__GENERATION_TIME)
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
                    TIP_OF_THE_NIGHT_MODEL__COLUMN_NAME__GENERATION_TIME, _generationTime);
        }
        return generationTimeProperty;
    }
    // END  GENERATIONTIME -----------------------------------------------------
    
    // START  TEXT -------------------------------------------------------------
    private StringProperty textProperty = null;
    private String _text = SIGN__EMPTY;
    
    @Column(name = TIP_OF_THE_NIGHT_MODEL__COLUMN_NAME__TEXT)
    public String getText() {
        if (this.textProperty == null) {
            return _text;
        } else {
            return textProperty.get();
        }
    }
    
    public void setText(String text) {
        if (this.textProperty == null) {
            _text = text;
        } else {
            this.textProperty.set(text);
        }
    }
    
    public StringProperty textProperty() {
        if (textProperty == null) {
            textProperty = new SimpleStringProperty(this,
                    TIP_OF_THE_NIGHT_MODEL__COLUMN_NAME__TEXT, _text);
        }
        return textProperty;
    }
    // END  TEXT ---------------------------------------------------------------
    
    // START  TITLE -------------------------------------------------------------
    private StringProperty titleProperty = null;
    private String _title = SIGN__EMPTY;
    
    @Column(name = TIP_OF_THE_NIGHT_MODEL__COLUMN_NAME__TITLE)
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
                    TIP_OF_THE_NIGHT_MODEL__COLUMN_NAME__TITLE, _title);
        }
        return titleProperty;
    }
    // END  TITLE --------------------------------------------------------------
    
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(this.getId())
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
        
        final TipOfTheNightModel other = (TipOfTheNightModel) obj;
        return new EqualsBuilder()
                .append(this.getId(), other.getId())
                .append(this.getGenerationTime(), other.getGenerationTime())
                .isEquals();
    }
    
    @Override
    public int compareTo(TipOfTheNightModel other) {
        return new CompareToBuilder()
                .append(other.getGenerationTime(), this.getGenerationTime())
                .append(other.getTitle(), this.getTitle())
                .append(other.getId(), this.getId())
                .toComparison();
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", this.getId()) // NOI18N
                .append("title", this.getTitle()) // NOI18N
                .append("generationtime", this.getGenerationTime()) // NOI18N
                .toString();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getId());
        out.writeLong(this.getGenerationTime());
        out.writeObject(this.getText());
        out.writeObject(this.getTitle());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readLong());
        this.setGenerationTime(in.readLong());
        this.setText(String.valueOf(in.readObject()));
        this.setTitle(String.valueOf(in.readObject()));
    }
    
}
