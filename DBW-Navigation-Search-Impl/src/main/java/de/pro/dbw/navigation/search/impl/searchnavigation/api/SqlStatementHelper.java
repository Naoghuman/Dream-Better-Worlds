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
package de.pro.dbw.navigation.search.impl.searchnavigation.api;

import de.pro.dbw.base.component.api.IExtendedTextField;
import de.pro.dbw.base.provider.BaseProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author PRo
 */
public final class SqlStatementHelper {
    
    public static ObservableList<String> createSimpleSqlInfoForSearchIn(VBox vbSimpleSqlInfo) {
        final ObservableList<String> statements = FXCollections.observableArrayList();
        for (Node node : vbSimpleSqlInfo.getChildren()) {
            if (node instanceof Label) {
                final Label lbl = (Label) node;
                statements.add(lbl.getText());
            }
        }
        
        return statements;
    }
    
    public static String createSqlStatementForEntity(String entity) {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT e FROM ").append(entity).append(" e"); // NOI18N
        sb.append(" WHERE "); // NOI18N
        
        return sb.toString();
    }
    
    public static  String createSqlStatementForTextField(Boolean addPrefix, String column, String parameter) {
        final StringBuilder sb = new StringBuilder();
        sb.append(addPrefix ? "AND " : ""); // NOI18N
        sb.append("e.").append(column); // NOI18N
        sb.append(" LIKE '%").append(parameter).append("%'"); // NOI18N
        
        return sb.toString();
    }
    
    public static void initializeSimpleSqlInfo(VBox vbSimpleSqlInfo, Boolean visibleAndManaged) {
        vbSimpleSqlInfo.getChildren().clear();
        vbSimpleSqlInfo.setVisible(visibleAndManaged);
        vbSimpleSqlInfo.setManaged(visibleAndManaged);
    }
    
    public static IExtendedTextField initializeTextField(
            VBox vbSearchComponents, String title, double minWidth
    ) {
        final IExtendedTextField textField = BaseProvider.getDefault().getComponentProvider().getExtendedTextField();
        textField.configure(title, minWidth);
        
        vbSearchComponents.getChildren().add(textField.getView());
        
        return textField;
    }
    
}
