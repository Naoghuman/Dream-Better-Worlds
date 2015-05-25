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
import de.pro.dbw.core.configuration.api.file.reflection.IReflectionConfiguration;
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

/**
 * TODO Extention voting the commentary
 *
 * @author PRo
 */
@Entity
@Access(AccessType.PROPERTY)
@Table(name = "ReflectionCommentModel")
@NamedQueries(
    @NamedQuery(
            name = IReflectionConfiguration.REFLECTION_COMMENT_MODEL__FIND_ALL_COMMENTS,
            query = "SELECT r FROM ReflectionCommentModel r WHERE r.parentId = :parentId")
)
public class ReflectionCommentModel implements Comparable<ReflectionCommentModel>, Externalizable,
        IDefaultIdConfiguration, IUtilConfiguration {

    private static final long serialVersionUID = 1L;

    public ReflectionCommentModel() {
        
    }
    
    // START  ID ---------------------------------------------------------------
    private LongProperty idProperty;
    private long _id = FILE__REFLECTION_COMMENT__DEFAULT_ID;

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
    
    // START  PARENT-ID --------------------------------------------------------
    private LongProperty parentIdProperty;
    private long _parentId = System.currentTimeMillis();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "parentId")
    public long getParentId() {
        if (this.parentIdProperty == null) {
            return _parentId;
        } else {
            return parentIdProperty.get();
        }
    }

    public final void setParentId(long parentId) {
        if (this.parentIdProperty == null) {
            _parentId = parentId;
        } else {
            this.parentIdProperty.set(parentId);
        }
    }

    public LongProperty parentIdProperty() {
        if (parentIdProperty == null) {
            parentIdProperty = new SimpleLongProperty(this, "parentId", _parentId);
        }
        return parentIdProperty;
    }
    // END  PARENT-ID ----------------------------------------------------------
    
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
    
    // START  TEXT -------------------------------------------------------------
    private StringProperty textProperty = null;
    private String _text = SIGN__EMPTY;
    
    @Column(name = "text")
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
            textProperty = new SimpleStringProperty(this, "text", _text);
        }
        return textProperty;
    }
    // END  TEXT ---------------------------------------------------------------
    
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
        sb.append("ReflectionComment[");
        sb.append("id=").append(this.getId());
        sb.append(", parentId=").append(this.getParentId());
        sb.append(", generationtime=").append(this.getGenerationTime());
        sb.append("]");
        
        return sb.toString();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(this.getId());
        out.writeLong(this.getParentId());
        out.writeLong(this.getGenerationTime());
        out.writeObject(this.getText());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setId(in.readLong());
        this.setParentId(in.readLong());
        this.setGenerationTime(in.readLong());
        this.setText(String.valueOf(in.readObject()));
    }
    
}
