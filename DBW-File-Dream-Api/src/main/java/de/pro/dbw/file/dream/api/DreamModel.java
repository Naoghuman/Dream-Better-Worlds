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
package de.pro.dbw.file.dream.api;

import de.pro.dbw.core.configuration.api.file.dream.IDreamConfiguration;
import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
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
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 *
 * @author PRo
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = IDreamConfiguration.ENTITY__TABLE_NAME__DREAM_MODEL)
@NamedQueries({
    @NamedQuery(
            name = IDreamConfiguration.NAMED_QUERY__NAME__FIND_ALL,
            query = IDreamConfiguration.NAMED_QUERY__QUERY__FIND_ALL),
    @NamedQuery(
            name = IDreamConfiguration.NAMED_QUERY__NAME__FIND_ALL_FOR_NAVIGATION_HISTORY,
            query = IDreamConfiguration.NAMED_QUERY__QUERY__FIND_ALL_FOR_NAVIGATION_HISTORY)
})
public class DreamModel implements Comparable<DreamModel>, Externalizable, 
        IDefaultIdConfiguration, IDreamConfiguration, IUtilConfiguration {

    private static final long serialVersionUID = 1L;
    
    public static DreamModel copy(DreamModel toCopy) {
        final DreamModel copy = new DreamModel();
        copy.setDescription(toCopy.getDescription());
        copy.setFavorite(toCopy.isFavorite());
        copy.setFavoriteReason(toCopy.getFavoriteReason());
        copy.setGenerationTime(toCopy.getGenerationTime());
        copy.setId(toCopy.getId());
        copy.setMarkAsChanged(Boolean.FALSE);
        copy.setText(toCopy.getText());
        copy.setTitle(toCopy.getTitle());
        
        return copy;
    }
    
    public DreamModel() {
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
    @Column(name = DREAM_MODEL__COLUMN_NAME__ID)
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
            idProperty = new SimpleLongProperty(this, DREAM_MODEL__COLUMN_NAME__ID, _id);
        }
        return idProperty;
    }
    // END  ID -----------------------------------------------------------------
    
    // START  GENERATIONTIME ---------------------------------------------------
    private LongProperty generationTimeProperty;
    private long _generationTime = System.currentTimeMillis();

    @Column(name = DREAM_MODEL__COLUMN_NAME__GENERATION_TIME)
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
                    DREAM_MODEL__COLUMN_NAME__GENERATION_TIME, _generationTime);
        }
        return generationTimeProperty;
    }
    // END  GENERATIONTIME -----------------------------------------------------
    
    // START  FAVORITE ---------------------------------------------------------
    private BooleanProperty favoriteProperty = null;
    private boolean _favorite = Boolean.FALSE;
    
    @Column(name = DREAM_MODEL__COLUMN_NAME__FAVORITE)
    public Boolean isFavorite() {
        if (this.favoriteProperty == null) {
            return _favorite;
        } else {
            return favoriteProperty.get();
        }
    }
    
    public void setFavorite(Boolean isFavorite) {
        if (this.favoriteProperty == null) {
            _favorite = isFavorite;
        } else {
            this.favoriteProperty.set(isFavorite);
        }
    }
    
    public BooleanProperty favoriteProperty() {
        if (favoriteProperty == null) {
            favoriteProperty = new SimpleBooleanProperty(this, 
                    DREAM_MODEL__COLUMN_NAME__FAVORITE, _favorite);
        }
        return favoriteProperty;
    }
    // END  FAVORITE -----------------------------------------------------------
    
    // START  FAVORITEREASON ---------------------------------------------------
    private StringProperty favoriteReasonProperty = null;
    private String _favoriteReason = SIGN__EMPTY;
    
    @Column(name = DREAM_MODEL__COLUMN_NAME__FAVORITE_REASON)
    public String getFavoriteReason() {
        if (this.favoriteReasonProperty == null) {
            return _favoriteReason;
        } else {
            return favoriteReasonProperty.get();
        }
    }
    
    public void setFavoriteReason(String favoriteReason) {
        if (this.favoriteReasonProperty == null) {
            _favoriteReason = favoriteReason;
        } else {
            this.favoriteReasonProperty.set(favoriteReason);
        }
    }
    
    public StringProperty favoriteReasonProperty() {
        if (favoriteReasonProperty == null) {
            favoriteReasonProperty = new SimpleStringProperty(this,
                    DREAM_MODEL__COLUMN_NAME__FAVORITE_REASON, _favoriteReason);
        }
        return favoriteReasonProperty;
    }
    // END  FAVORITEREASON -----------------------------------------------------
    
    // START  DESCRIPTION ------------------------------------------------------
    private StringProperty descriptionProperty = null;
    private String _description = SIGN__EMPTY;
    
    @Column(name = DREAM_MODEL__COLUMN_NAME__DESCRIPTION)
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
                    DREAM_MODEL__COLUMN_NAME__DESCRIPTION, _description);
        }
        return descriptionProperty;
    }
    // END  DESCRIPTION --------------------------------------------------------
    
    // START  TEXT -------------------------------------------------------------
    private StringProperty textProperty = null;
    private String _text = SIGN__EMPTY;
    
    @Column(name = DREAM_MODEL__COLUMN_NAME__TEXT)
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
            textProperty = new SimpleStringProperty(this, DREAM_MODEL__COLUMN_NAME__TEXT, _text);
        }
        return textProperty;
    }
    // END  TEXT ---------------------------------------------------------------
    
    // START  TITLE -------------------------------------------------------------
    private StringProperty titleProperty = null;
    private String _title = SIGN__EMPTY;
    
    @Column(name = DREAM_MODEL__COLUMN_NAME__TITLE)
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
            titleProperty = new SimpleStringProperty(this, DREAM_MODEL__COLUMN_NAME__TITLE, _title);
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
        
        final DreamModel other = (DreamModel) obj;
        return new EqualsBuilder()
                .append(this.getId(), other.getId())
                .append(this.getGenerationTime(), other.getGenerationTime())
                .isEquals();
    }
    
    @Override
    public int compareTo(DreamModel other) {
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
        out.writeBoolean(this.isFavorite());
        out.writeObject(this.getFavoriteReason());
        out.writeObject(this.getDescription());
        out.writeObject(this.getText());
        out.writeObject(this.getTitle());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readLong());
        this.setGenerationTime(in.readLong());
        this.setFavorite(in.readBoolean());
        this.setFavoriteReason(String.valueOf(in.readObject()));
        this.setDescription(String.valueOf(in.readObject()));
        this.setText(String.valueOf(in.readObject()));
        this.setTitle(String.valueOf(in.readObject()));
    }
    
}
