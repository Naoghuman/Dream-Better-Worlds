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
package de.pro.dbw.navigation.history.impl.listview.childelement;

import com.airhacks.afterburner.views.FXMLView;
import de.pro.dbw.util.api.IDateConverter;
import de.pro.dbw.util.provider.UtilProvider;

/**
 *
 * @author PRo
 */
public class ChildElementView extends FXMLView implements Comparable<ChildElementView> {

    public ChildElementPresenter getRealPresenter() {
        return (ChildElementPresenter) super.getPresenter();
    }
    
    @Override
    public int compareTo(ChildElementView other) {
        final ChildElementPresenter otherPresenter = other.getRealPresenter();
        final String dateTimeOther = UtilProvider.getDefault().getDateConverter()
                .convertLongToDateTime(otherPresenter.getGenerationTime(), IDateConverter.PATTERN__DATETIME);
        
        final ChildElementPresenter thisPresenter = this.getRealPresenter();
        final String dateTimeThis = UtilProvider.getDefault().getDateConverter()
                .convertLongToDateTime(thisPresenter.getGenerationTime(), IDateConverter.PATTERN__DATETIME);
        
        int compare = dateTimeOther.compareTo(dateTimeThis);
        if (compare != 0) {
            return compare;
        }
        
        compare = thisPresenter.getTitle().compareTo(otherPresenter.getTitle());
        if (compare != 0) {
            return compare;
        }
        
        return Long.compare(otherPresenter.getGenerationTime(), thisPresenter.getGenerationTime());
    }
    
}
