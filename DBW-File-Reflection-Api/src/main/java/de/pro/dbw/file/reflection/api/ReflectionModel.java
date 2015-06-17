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
package de.pro.dbw.file.reflection.api;

import de.pro.dbw.core.configuration.api.application.defaultid.IDefaultIdConfiguration;
import de.pro.dbw.core.configuration.api.application.util.IUtilConfiguration;
import de.pro.dbw.core.configuration.api.file.reflection.IReflectionCommentConfiguration;
import de.pro.dbw.core.configuration.api.file.reflection.IReflectionConfiguration;
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
@Table(name = IReflectionConfiguration.ENTITY__TABLE_NAME__REFLECTON_MODEL)
@NamedQueries({
    @NamedQuery(
            name = IReflectionConfiguration.NAMED_QUERY__NAME__FIND_ALL,
            query = IReflectionConfiguration.NAMED_QUERY__QUERY__FIND_ALL),
    @NamedQuery(
            name = IReflectionConfiguration.NAMED_QUERY__NAME__FIND_ALL_FOR_NAVIGATION__HISTORY,
            query = IReflectionConfiguration.NAMED_QUERY__QUERY__FIND_ALL_FOR_NAVIGATION_HISTORY)
})
public class ReflectionModel implements Comparable<ReflectionModel>, Externalizable, 
        IDefaultIdConfiguration, IReflectionConfiguration, IUtilConfiguration {

    private static final long serialVersionUID = 1L;
    
    public static ReflectionModel copy(ReflectionModel toCopy) {
        final ReflectionModel copy = new ReflectionModel();
        copy.setGenerationTime(toCopy.getGenerationTime());
        copy.setId(toCopy.getId());
        copy.setMarkAsChanged(Boolean.FALSE);
        copy.setReflectionCommentModels(toCopy.getReflectionCommentModels());
        copy.setSource(toCopy.getSource());
        copy.setText(toCopy.getText());
        copy.setTitle(toCopy.getTitle());
        
        return copy;
    }
    
    public ReflectionModel() {
        this.initialize();
    }
    
    private void initialize() {
        markAsChangedProperty = new SimpleBooleanProperty(Boolean.FALSE);
    }
    
    // START  ID ---------------------------------------------------------------
    private LongProperty idProperty;
    private long _id = FILE__REFLECTION__DEFAULT_ID;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = REFLECTION_MODEL__COLUMN_NAME__ID)
    public long getId() {
        if (idProperty == null) {
            return _id;
        } else {
            return idProperty.getValue();
        }
    }

    public final void setId(long id) {
        if (idProperty == null) {
            _id = id;
        } else {
            idProperty.setValue(id);
        }
    }

    public LongProperty idProperty() {
        if (idProperty == null) {
            idProperty = new SimpleLongProperty(this, REFLECTION_MODEL__COLUMN_NAME__ID, _id);
        }
        return idProperty;
    }
    // END  ID -----------------------------------------------------------------
    
    // START  REFLECTIONCOMMENT ------------------------------------------------
    private ObjectProperty reflectionCommentModelsProperty;
    private List<ReflectionCommentModel> _reflectionCommentModels = FXCollections.observableArrayList();
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = JOIN_TABLE__NAME__MAPPING_REFLECTION_COMMENT,
            joinColumns = @JoinColumn(name = IReflectionCommentConfiguration.REFLECTION_COMMENT_MODEL__COLUMN_NAME__ID),
            inverseJoinColumns = @JoinColumn(name = IReflectionCommentConfiguration.REFLECTION_COMMENT_MODEL__COLUMN_NAME__ID)
    )
    public List<ReflectionCommentModel> getReflectionCommentModels() {
        if (reflectionCommentModelsProperty == null) {
            return _reflectionCommentModels;
        } else {
            return (List<ReflectionCommentModel>) reflectionCommentModelsProperty.getValue();
        }
    }
    
    public void setReflectionCommentModels(List<ReflectionCommentModel> reflectionCommentModels) {
        if (reflectionCommentModelsProperty == null) {
            _reflectionCommentModels = reflectionCommentModels;
        } else {
            reflectionCommentModelsProperty.setValue(reflectionCommentModels);
        }
    }
    
    public ObjectProperty reflectionCommentModelsProperty() {
        if (reflectionCommentModelsProperty == null) {
            reflectionCommentModelsProperty = new SimpleObjectProperty(this,
                    REFLECTION_MODEL__COLUMN_NAME__REFLECTION_COMMENT, _reflectionCommentModels);
        }
        
        return reflectionCommentModelsProperty;
    }
    // END  REFLECTIONCOMMENT --------------------------------------------------
    
    // START  GENERATIONTIME ---------------------------------------------------
    private LongProperty generationTimeProperty;
    private long _generationTime = System.currentTimeMillis();

    @Column(name = REFLECTION_MODEL__COLUMN_NAME__GENERATION_TIME)
    public long getGenerationTime() {
        if (generationTimeProperty == null) {
            return _generationTime;
        } else {
            return generationTimeProperty.getValue();
        }
    }

    public final void setGenerationTime(long generationTime) {
        if (generationTimeProperty == null) {
            _generationTime = generationTime;
        } else {
            generationTimeProperty.setValue(generationTime);
        }
    }

    public LongProperty generationTimeProperty() {
        if (generationTimeProperty == null) {
            generationTimeProperty = new SimpleLongProperty(this,
                    REFLECTION_MODEL__COLUMN_NAME__GENERATION_TIME, _generationTime);
        }
        return generationTimeProperty;
    }
    // END  GENERATIONTIME -----------------------------------------------------
    
    // START  SOURCE -----------------------------------------------------------
    private StringProperty sourceProperty = null;
    private String _source = SIGN__EMPTY;
    
    @Column(name = REFLECTION_MODEL__COLUMN_NAME__SOURCE)
    public String getSource() {
        if (sourceProperty == null) {
            return _source;
        } else {
            return sourceProperty.getValue();
        }
    }
    
    public void setSource(String source) {
        if (sourceProperty == null) {
            _source = source;
        } else {
            sourceProperty.setValue(source);
        }
    }
    
    public StringProperty sourceProperty() {
        if (sourceProperty == null) {
            sourceProperty = new SimpleStringProperty(this,
                    REFLECTION_MODEL__COLUMN_NAME__SOURCE, _source);
        }
        return sourceProperty;
    }
    // END  SOURCE -------------------------------------------------------------
    
    // START  TEXT -------------------------------------------------------------
    private StringProperty textProperty = null;
    private String _text = SIGN__EMPTY;
    
    @Column(name = REFLECTION_MODEL__COLUMN_NAME__TEXT)
    public String getText() {
        if (textProperty == null) {
            return _text;
        } else {
            return textProperty.getValue();
        }
    }
    
    public void setText(String text) {
        if (textProperty == null) {
            _text = text;
        } else {
            textProperty.setValue(text);
        }
    }
    
    public StringProperty textProperty() {
        if (textProperty == null) {
            textProperty = new SimpleStringProperty(this,
                    REFLECTION_MODEL__COLUMN_NAME__TEXT, _text);
        }
        return textProperty;
    }
    // END  TEXT ---------------------------------------------------------------
    
    // START  TITLE -------------------------------------------------------------
    private StringProperty titleProperty = null;
    private String _title = SIGN__EMPTY;
    
    @Column(name = REFLECTION_MODEL__COLUMN_NAME__TITLE)
    public String getTitle() {
        if (titleProperty == null) {
            return _title;
        } else {
            return titleProperty.getValue();
        }
    }
    
    public void setTitle(String title) {
        if (titleProperty == null) {
            _title = title;
        } else {
            titleProperty.setValue(title);
        }
    }
    
    public StringProperty titleProperty() {
        if (titleProperty == null) {
            titleProperty = new SimpleStringProperty(this,
                    REFLECTION_MODEL__COLUMN_NAME__TITLE, _title);
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
        final ReflectionModel other = (ReflectionModel) obj;
        if (!Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        
        if (!Objects.equals(this.getGenerationTime(), other.getGenerationTime())) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public int compareTo(ReflectionModel other) {
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
        sb.append("Reflection ["); // NOI18N
        sb.append("id=").append(this.getId()); // NOI18N
        sb.append(", title=").append(this.getTitle()); // NOI18N
        sb.append(", generationtime=").append(this.getGenerationTime()); // NOI18N
        sb.append("]"); // NOI18N
        
        return sb.toString();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getId());
        out.writeObject(this.getReflectionCommentModels());
        out.writeLong(this.getGenerationTime());
        out.writeObject(this.getSource());
        out.writeObject(this.getText());
        out.writeObject(this.getTitle());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readLong());
        this.setReflectionCommentModels((List<ReflectionCommentModel>) in.readObject());
        this.setGenerationTime(in.readLong());
        this.setSource(String.valueOf(in.readObject()));
        this.setText(String.valueOf(in.readObject()));
        this.setTitle(String.valueOf(in.readObject()));
    }
    
}
