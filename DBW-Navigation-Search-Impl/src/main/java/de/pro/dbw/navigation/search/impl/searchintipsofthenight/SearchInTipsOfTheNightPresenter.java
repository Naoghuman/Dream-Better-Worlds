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
package de.pro.dbw.navigation.search.impl.searchintipsofthenight;

import de.pro.dbw.base.component.api.ExtendedTabModel;
import de.pro.dbw.base.component.api.IExtendedTextField;
import de.pro.dbw.core.configuration.api.application.action.IActionConfiguration;
import de.pro.dbw.base.provider.BaseProvider;
import de.pro.lib.action.api.ActionFacade;
import de.pro.lib.action.api.ActionTransferModel;
import de.pro.lib.logger.api.LoggerFacade;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

/**
 *
 * @author PRo
 */
public class SearchInTipsOfTheNightPresenter implements Initializable, IActionConfiguration {
    
    @FXML private VBox vBox;
    
    private int indexSearchInTipsOfTheNight = 0;
    
    private IExtendedTextField extendedTextFieldDescription = null;
    private IExtendedTextField extendedTextFieldTitle = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoggerFacade.getDefault().info(this.getClass(), "Initialize SearchInTipsOfTheNightPresenter"); // NOI18N
        
        assert (vBox != null) : "fx:id=\"vBox\" was not injected: check your FXML file 'SearchInTipsOfTheNight.fxml'."; // NOI18N
    
        this.initializeTitle(0);
        this.initializeDescription(1);
    }
    
    private void initializeTitle(int index) {
        extendedTextFieldTitle = BaseProvider.getDefault().getComponentProvider().getExtendedTextField();
        extendedTextFieldTitle.configure("Title: ", 96.0d); // XXX properties
        
        vBox.getChildren().add(index, extendedTextFieldTitle.getView());
    }
    
    private void initializeDescription(int index) {
        extendedTextFieldDescription = BaseProvider.getDefault().getComponentProvider().getExtendedTextField();
        extendedTextFieldDescription.configure("Description: ", 96.0d); // XXX properties
        
        vBox.getChildren().add(index, extendedTextFieldDescription.getView());
    }
    
    public void onActionSearchInTipsOfTheNight() {
        LoggerFacade.getDefault().debug(this.getClass(), "On action search in Tip of the Nights"); // NOI18N
        
        /*
        TODO
         - catch data for search
         - create extendedtab
         - handle action
        
         in file-module
           - add extended tab
        */
        final ExtendedTabModel model = new ExtendedTabModel();
        model.setActionKey(null);
        model.setCloseable(Boolean.TRUE);
        model.setId(System.currentTimeMillis());
        model.setImage(null);
        model.setMarkAsChanged(Boolean.FALSE);
        model.setModel(null);
        model.setPresenter(null);
        model.setTitle("Search in Tips of the Night #" + ++indexSearchInTipsOfTheNight);
        model.setView(null);
        
        final ActionTransferModel transferModel = new ActionTransferModel();
        transferModel.setActionKey(ACTION__SEARCH_IN__TIPS_OF_THE_NIGHT);
        transferModel.setObject(model);
        
        ActionFacade.getDefault().handle(transferModel);
    }
    
}
