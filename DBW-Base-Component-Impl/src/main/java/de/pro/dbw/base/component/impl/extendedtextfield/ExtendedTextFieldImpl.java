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
package de.pro.dbw.base.component.impl.extendedtextfield;

import de.pro.dbw.base.component.api.IExtendedTextField;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;

/**
 *
 * @author PRo
 */
public class ExtendedTextFieldImpl implements IExtendedTextField {
    
    private ExtendedTextFieldView view = null;
    private ExtendedTextFieldPresenter presenter = null;
    
    public ExtendedTextFieldImpl() {
        this.initialize();
    }
    
    private void initialize() {
        view = new ExtendedTextFieldView();
        presenter = view.getRealPresenter();
    }

    @Override
    public void configure(String titleForCheckBox, double minWidthForLabel) {
        presenter.configure(titleForCheckBox, minWidthForLabel);
    }

    @Override
    public CheckBox getCheckBox() {
        return presenter.getCheckBox();
    }

    @Override
    public String getText() {
        return presenter.getText();
    }

    @Override
    public Parent getView() {
        return view.getView();
    }

    @Override
    public Boolean hasParameter() {
        return presenter.getCheckBox().isSelected()
                && !presenter.getText().isEmpty();
    }
    
}
