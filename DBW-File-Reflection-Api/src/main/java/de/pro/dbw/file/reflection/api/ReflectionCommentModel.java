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
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author PRo
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = IReflectionCommentConfiguration.ENTITY__TABLE_NAME__REFLECTON_COMMENT_MODEL)
public class ReflectionCommentModel implements Comparable<ReflectionCommentModel>, Externalizable,
        IDefaultIdConfiguration, IReflectionCommentConfiguration, IUtilConfiguration {

    private static final long serialVersionUID = 1L;

    public ReflectionCommentModel() {
        this.initialize();
    }
    
    private void initialize() {
        
    }
    
    // START  ID ---------------------------------------------------------------
    private LongProperty idProperty;
    private long _id = FILE__REFLECTION_COMMENT__DEFAULT_ID;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = REFLECTION_COMMENT_MODEL__COLUMN_NAME__ID)
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
            idProperty = new SimpleLongProperty(this,
                    REFLECTION_COMMENT_MODEL__COLUMN_NAME__ID, _id);
        }
        return idProperty;
    }
    // END  ID -----------------------------------------------------------------

    // START  GENERATIONTIME ---------------------------------------------------
    private LongProperty generationTimeProperty;
    private long _generationTime = System.currentTimeMillis();

    @Column(name = REFLECTION_COMMENT_MODEL__COLUMN_NAME__GENERATION_TIME)
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
                    REFLECTION_COMMENT_MODEL__COLUMN_NAME__GENERATION_TIME, _generationTime);
        }
        return generationTimeProperty;
    }
    // END  GENERATIONTIME -----------------------------------------------------
    
    // START  TEXT -------------------------------------------------------------
    private StringProperty textProperty = null;
    private String _text = SIGN__EMPTY;
    
    @Column(name = REFLECTION_COMMENT_MODEL__COLUMN_NAME__TEXT)
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
                    REFLECTION_COMMENT_MODEL__COLUMN_NAME__TEXT, _text);
        }
        return textProperty;
    }
    // END  TEXT ---------------------------------------------------------------

    private transient Boolean markAsDeleted = Boolean.FALSE;

    @Transient
    public Boolean isMarkAsDeleted() {
        return markAsDeleted;
    }
    
    public void setMarkAsDeleted(Boolean markAsDeleted) {
        this.markAsDeleted = markAsDeleted;
    }
    
    @Override
    public int compareTo(ReflectionCommentModel other) {
        int compareTo = Long.compare(other.getGenerationTime(), this.getGenerationTime());
        if (compareTo != 0) {
            return compareTo;
        }
        
        return Long.compare(other.getId(), this.getId());
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ReflectionComment ["); // NOI18N
        sb.append("id=").append(this.getId()); // NOI18N
        sb.append(", generationtime=").append(this.getGenerationTime()); // NOI18N
        sb.append("]"); // NOI18N
        
        return sb.toString();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getId());
        out.writeLong(this.getGenerationTime());
        out.writeObject(this.getText());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readLong());
        this.setGenerationTime(in.readLong());
        this.setText(String.valueOf(in.readObject()));
    }
    
}
