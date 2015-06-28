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
package de.pro.dbw.feature.tag.api;

import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import de.pro.dbw.core.configuration.api.feature.tag.ITagCategoryConfiguration;
import de.pro.dbw.core.configuration.api.feature.tag.ITagConfiguration;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author PRo
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = ITagConfiguration.ENTITY__TABLE_NAME__TAG_MODEL)
@NamedQueries({
    @NamedQuery(
            name = ITagConfiguration.NAMED_QUERY__NAME__FIND_ALL,
            query = ITagConfiguration.NAMED_QUERY__QUERY__FIND_ALL)
})
public class TagModel implements Comparable<TagModel>,
        IDefaultIdConfiguration, Externalizable, ITagConfiguration,
        IUtilConfiguration
{
    private static final long serialVersionUID = 1L;
    
    public TagModel() {
        this.initialize();
    }
    
    private void initialize() {
        markAsChangedProperty = new SimpleBooleanProperty(Boolean.FALSE);
    }
    
    // START  ID ---------------------------------------------------------------
    private LongProperty idProperty;
    private long _id = FEATURE__TAG_CATEGORY__DEFAULT_ID;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = TAG_MODEL__COLUMN_NAME__ID)
    public long getId() {
        if (idProperty == null) {
            return _id;
        } else {
            return idProperty.get();
        }
    }

    public final void setId(long id) {
        if (idProperty == null) {
            _id = id;
        } else {
            this.idProperty.set(id);
        }
    }

    public LongProperty idProperty() {
        if (idProperty == null) {
            idProperty = new SimpleLongProperty(this, 
                    TAG_MODEL__COLUMN_NAME__ID, _id);
        }
        return idProperty;
    }
    // END  ID -----------------------------------------------------------------
    
    // START  GENERATIONTIME ---------------------------------------------------
    private LongProperty generationTimeProperty;
    private long _generationTime = System.currentTimeMillis();

    @Column(name = TAG_MODEL__COLUMN_NAME__GENERATION_TIME)
    public long getGenerationTime() {
        if (generationTimeProperty == null) {
            return _generationTime;
        } else {
            return generationTimeProperty.get();
        }
    }

    public final void setGenerationTime(long generationTime) {
        if (generationTimeProperty == null) {
            _generationTime = generationTime;
        } else {
            generationTimeProperty.set(generationTime);
        }
    }

    public LongProperty generationTimeProperty() {
        if (generationTimeProperty == null) {
            generationTimeProperty = new SimpleLongProperty(this, 
                    TAG_MODEL__COLUMN_NAME__GENERATION_TIME, _generationTime);
        }
        return generationTimeProperty;
    }
    // END  GENERATIONTIME -----------------------------------------------------
    
    
    // START  TAG-CATEGORY -----------------------------------------------------
    private ObjectProperty tagCategoryModelsProperty;
    private List<TagCategoryModel> _tagCategoryModels = FXCollections.observableArrayList();
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = JOIN_TABLE__NAME__MAPPING_TAG_CATEGORY,
            joinColumns = @JoinColumn(name = ITagCategoryConfiguration.TAG_CATEGORY_MODEL__COLUMN_NAME__ID),
            inverseJoinColumns = @JoinColumn(name = ITagCategoryConfiguration.TAG_CATEGORY_MODEL__COLUMN_NAME__ID)
    )
    public List<TagCategoryModel> getTagCategoryModels() {
        if (tagCategoryModelsProperty == null) {
            return _tagCategoryModels;
        } else {
            return (List<TagCategoryModel>) tagCategoryModelsProperty.getValue();
        }
    }
    
    public void setTagCategoryModels(List<TagCategoryModel> tagCategoryModels) {
        if (tagCategoryModelsProperty == null) {
            _tagCategoryModels = tagCategoryModels;
        } else {
            tagCategoryModelsProperty.setValue(tagCategoryModels);
        }
    }
    
    public ObjectProperty tagCategoryModelsProperty() {
        if (tagCategoryModelsProperty == null) {
            tagCategoryModelsProperty = new SimpleObjectProperty(this,
                    TAG_MODEL__COLUMN_NAME__TAG_CATEGORY, _tagCategoryModels);
        }
        
        return tagCategoryModelsProperty;
    }
    // END  TAG-CATEGORY -------------------------------------------------------
    
    // START  DESCRIPTION ------------------------------------------------------
    private StringProperty descriptionProperty = null;
    private String _description = SIGN__EMPTY;
    
    @Column(name = TAG_MODEL__COLUMN_NAME__DESCRIPTION)
    public String getDescription() {
        if (descriptionProperty == null) {
            return _description;
        } else {
            return descriptionProperty.get();
        }
    }
    
    public void setDescription(String description) {
        if (descriptionProperty == null) {
            _description = description;
        } else {
            descriptionProperty.set(description);
        }
    }
    
    public StringProperty descriptionProperty() {
        if (descriptionProperty == null) {
            descriptionProperty = new SimpleStringProperty(this, 
                    TAG_MODEL__COLUMN_NAME__DESCRIPTION, _description);
        }
        return descriptionProperty;
    }
    // END  DESCRIPTION --------------------------------------------------------
    
    // START  TITLE -------------------------------------------------------------
    private StringProperty titleProperty = null;
    private String _title = SIGN__EMPTY;
    
    @Column(name = TAG_MODEL__COLUMN_NAME__TITLE)
    public String getTitle() {
        if (titleProperty == null) {
            return _title;
        } else {
            return titleProperty.get();
        }
    }
    
    public void setTitle(String title) {
        if (titleProperty == null) {
            _title = title;
        } else {
            titleProperty.set(title);
        }
    }
    
    public StringProperty titleProperty() {
        if (titleProperty == null) {
            titleProperty = new SimpleStringProperty(this, 
                    TAG_MODEL__COLUMN_NAME__TITLE, _title);
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
        final TagModel other = (TagModel) obj;
        if (!Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public int compareTo(TagModel other) {
        int compareTo = this.getTitle().compareTo(other.getTitle());
        if (compareTo != 0) {
            return compareTo;
        }
        compareTo = Long.compare(this.getGenerationTime(), other.getGenerationTime());
        if (compareTo != 0) {
            return compareTo;
        }
        
        return Long.compare(this.getId(), other.getId());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Tag ["); // NOI18N
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
        out.writeObject(this.getTagCategoryModels());
        out.writeObject(this.getTitle());
        out.writeObject(this.getDescription());
    }

    @Override
    
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readLong());
        this.setGenerationTime(in.readLong());
        this.setTagCategoryModels((List<TagCategoryModel>) in.readObject());
        this.setTitle(String.valueOf(in.readObject()));
        this.setDescription(String.valueOf(in.readObject()));
    }
}
