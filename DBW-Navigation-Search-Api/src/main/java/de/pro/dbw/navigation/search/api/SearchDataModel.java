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
package de.pro.dbw.navigation.search.api;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author PRo
 */
public class SearchDataModel {
    
    private String suffixForManyElements = null;
    private String suffixForOneElement = null;
    
    private ObservableList<String> simpleSqlInfo = null;
    private ObservableList<String> sqlStatements = null;
    
    public SearchDataModel() {
        this.initialize();
    }
    
    private void initialize() {
        simpleSqlInfo = FXCollections.observableArrayList();
        sqlStatements = FXCollections.observableArrayList();
    }

    public String getSuffixForManyElements() {
        return suffixForManyElements;
    }

    public void setSuffixForManyElements(String suffixForManyElements) {
        this.suffixForManyElements = suffixForManyElements;
    }

    public String getSuffixForOneElement() {
        return suffixForOneElement;
    }

    public void setSuffixForOneElement(String suffixForOneElement) {
        this.suffixForOneElement = suffixForOneElement;
    }

    public ObservableList<String> getSimpleSqlInfo() {
        return simpleSqlInfo;
    }

    public void setSimpleSqlInfo(ObservableList<String> simpleSqlInfo) {
        this.simpleSqlInfo.clear();
        this.simpleSqlInfo.addAll(simpleSqlInfo);
    }

    public ObservableList<String> getSqlStatements() {
        return sqlStatements;
    }

    public void setSqlStatements(ObservableList<String> sqlStatements) {
        this.sqlStatements.clear();
        this.sqlStatements.addAll(sqlStatements);
    }
    
}
