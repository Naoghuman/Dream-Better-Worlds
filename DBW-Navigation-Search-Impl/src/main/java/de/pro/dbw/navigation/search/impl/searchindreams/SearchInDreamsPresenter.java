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
package de.pro.dbw.navigation.search.impl.searchindreams;

import de.pro.dbw.base.component.api.ExtendedTabModel;
import de.pro.dbw.base.component.api.IExtendedTextField;
import de.pro.dbw.core.configuration.api.action.IActionConfiguration;
import de.pro.dbw.core.configuration.api.navigation.search.ISearchConfiguration;
import de.pro.dbw.base.provider.BaseProvider;
import de.pro.dbw.navigation.search.api.SearchDataModel;
import de.pro.dbw.navigation.search.impl.searchnavigation.api.SqlStatementHelper;
import de.pro.dbw.navigation.search.impl.searchindreamsresult.SearchInDreamsResultPresenter;
import de.pro.dbw.navigation.search.impl.searchindreamsresult.SearchInDreamsResultView;
import de.pro.dbw.navigation.search.impl.searchnavigation.api.ISqlStatementHelper;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

/**
 *
 * @author PRo
 */
public class SearchInDreamsPresenter implements 
        Initializable, IActionConfiguration,
        ISearchConfiguration, ISqlStatementHelper
{
    @FXML private Button bSearchInDreams;
    @FXML private ScrollPane spSearchComponents;
    @FXML private VBox vbSearchComponents;
    @FXML private VBox vbSimpleSqlInfo;
    
    private IntegerProperty counterActiveComponentsProperty = null;
    
    private int indexSearchInDreams = 0;
    
    private IExtendedTextField extendedTextFieldDescription;
    private IExtendedTextField extendedTextFieldTitle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize SearchInDreamsPresenter"); // NOI18N
        
        assert (spSearchComponents != null) : "fx:id=\"spSearchComponents\" was not injected: check your FXML file 'SearchInDreams.fxml'."; // NOI18N
        assert (vbSearchComponents != null) : "fx:id=\"vbSearchComponents\" was not injected: check your FXML file 'SearchInDreams.fxml'."; // NOI18N
        assert (vbSimpleSqlInfo != null)    : "fx:id=\"vbSimpleSqlInfo\" was not injected: check your FXML file 'SearchInDreams.fxml'."; // NOI18N
    
        this.initializeScrollPane();
        SqlStatementHelper.initializeSimpleSqlInfo(vbSimpleSqlInfo, Boolean.FALSE);
        this.initializeSearchButton();
        
        final double minWidth = 72.0d;
        extendedTextFieldTitle = SqlStatementHelper.initializeTextField(vbSearchComponents, "Title:", minWidth); // NOI18N // XXX properties
        extendedTextFieldDescription = SqlStatementHelper.initializeTextField(vbSearchComponents, "Description:", minWidth); // NOI18N
        
        this.initializeChangeListeners();
    }
    
    private void initializeChangeListeners() {
        final ChangeListener<Boolean> listener = (ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            int counterActive = counterActiveComponentsProperty.getValue();
            counterActive = newValue ? ++counterActive : --counterActive;
            counterActiveComponentsProperty.setValue(counterActive);
            
            this.updateSimpleSqlInfo();
        };
        
        extendedTextFieldTitle.getCheckBox().selectedProperty().addListener(listener);
        extendedTextFieldDescription.getCheckBox().selectedProperty().addListener(listener);
    }
    
    private void initializeScrollPane() {
        this.setWidthForScrollPaneComponent();
        
        spSearchComponents.viewportBoundsProperty().addListener((ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) -> {
            this.setWidthForScrollPaneComponent();
        });
    }
    private void initializeSearchButton() {
        bSearchInDreams.setDisable(Boolean.TRUE);
        
        counterActiveComponentsProperty = new SimpleIntegerProperty(0);
        bSearchInDreams.disableProperty().bind(counterActiveComponentsProperty.isEqualTo(0)); 
    }
    
    private ObservableList<String> createSqlStatementsForSearchInDreams() {
        final ObservableList<String> statements = FXCollections.observableArrayList();
        statements.add(SqlStatementHelper.createSqlStatementForEntity(ENTITY__DREAM_FILE_MODEL));
        
        if (extendedTextFieldTitle.hasParameter()) {
            statements.add(SqlStatementHelper.createSqlStatementForTextField(
                    Boolean.FALSE, TITLE, extendedTextFieldTitle.getText()));
        }
        
        if (extendedTextFieldDescription.hasParameter()) {
            statements.add(SqlStatementHelper.createSqlStatementForTextField(
                    (statements.size() > 1), DESCRIPTION, extendedTextFieldDescription.getText()));
        }
        
        return statements;
    }
    
    public void onActionSearchInDreams() {
        final SearchDataModel searchDataModel = new SearchDataModel();
        searchDataModel.setSimpleSqlInfo(SqlStatementHelper.createSimpleSqlInfoForSearchIn(vbSimpleSqlInfo));
        searchDataModel.setSqlStatements(this.createSqlStatementsForSearchInDreams());
        searchDataModel.setSuffixForManyElements(" dreams"); // NOI18N
        searchDataModel.setSuffixForOneElement(" dream"); // NOI18N
        
        final SearchInDreamsResultView view = new SearchInDreamsResultView();
        final SearchInDreamsResultPresenter presenter = view.getRealPresenter();
        presenter.configure(searchDataModel);
        
        final ExtendedTabModel model = new ExtendedTabModel();
        model.setActionKey(null); /* not needed */
        model.setCloseable(Boolean.TRUE);
        model.setId(System.currentTimeMillis());
        model.setImage(null); // XXX
        model.setMarkAsChanged(Boolean.FALSE);
        model.setModel(null); /* not needed */
        model.setPresenter(presenter);
        model.setTitle("Search in Dreams #" + ++indexSearchInDreams); // NOI18N XXX properties
        model.setView(view.getView());
        
        final ActionTransferModel transferModel = new ActionTransferModel();
        transferModel.setActionKey(ACTION__SEARCH_IN__DREAMS);
        transferModel.setObject(model);
        
        ActionFacade.getDefault().handle(transferModel);
    }
    
    private void setWidthForScrollPaneComponent() {
        // TODO check if possible with binding
        double width = spSearchComponents.getViewportBounds().getWidth() - 5;
        width = (width < 175) ? 175 : width;
        vbSearchComponents.setMaxWidth(width);
        vbSearchComponents.setMinWidth(width);
    }
    
    private void updateSimpleSqlInfo() {
        final Boolean isVisibleAndManaged = counterActiveComponentsProperty.getValue() > 0;
        SqlStatementHelper.initializeSimpleSqlInfo(vbSimpleSqlInfo, isVisibleAndManaged);
        if (!isVisibleAndManaged) {
            return;
        }
        
//        vbSimpleSqlInfo.getChildren().add(new Separator());
        vbSimpleSqlInfo.getChildren().add(new Label("SEARCH in your dreams"));// XXX properties
        vbSimpleSqlInfo.getChildren().add(new Label("WHERE"));
        
        Boolean isSimpleSqlAdded = Boolean.FALSE;
        if (extendedTextFieldTitle.getCheckBox().isSelected()) {
            vbSimpleSqlInfo.getChildren().add(this.createSimpleSqlForText( Boolean.FALSE, "Title")); // NOI18N
            isSimpleSqlAdded = Boolean.TRUE;
        }
        if (extendedTextFieldDescription.getCheckBox().isSelected()) {
            vbSimpleSqlInfo.getChildren().add(this.createSimpleSqlForText(isSimpleSqlAdded, "Description")); // NOI18N
//            isSimpleSqlAdded = Boolean.TRUE;
        }
        
        vbSimpleSqlInfo.getChildren().add(new Separator());
    }
    
    private Label createSimpleSqlForText(Boolean isSimpleSqlAdded, String text) {
        final StringBuilder sb = new StringBuilder();
        sb.append("    "); // NOI18N
        sb.append(isSimpleSqlAdded ? "AND " : ""); // NOI18N
        sb.append(text);
        sb.append(" LIKE '%Xy%'"); // NOI18N
        
        return new Label(sb.toString());
    }
    
}
