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
package de.pro.dbw.application.performance.helper;

import de.pro.dbw.util.api.IDateConverter;
import de.pro.dbw.util.provider.UtilProvider;
import de.pro.lib.logger.api.LoggerFacade;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.collections.FXCollections;

/**
 *
 * @author PRo
 */
public class PerformanceHelper {
    
    private static PerformanceHelper instance = null;
    
    public static PerformanceHelper getDefault() {
        if (instance == null) {
            instance = new PerformanceHelper();
        }
        
        return instance;
    }
    
    public void logPerformanceCheck(Map<String, Integer> executedTimes, int repeatEntities) {
        LoggerFacade.INSTANCE.debug(this.getClass(), "================================================================================"); // NOI18N
        LoggerFacade.INSTANCE.debug(this.getClass(), "Execute action " + repeatEntities + " times."); // NOI18N

        final Set<String> keys = executedTimes.keySet();
        final List<String> keys2 = FXCollections.observableArrayList();
        final List<Long> millis = FXCollections.observableArrayList();
        keys2.addAll(keys);
        Collections.sort(keys2);
        for (String key : keys2) {
            final StringBuilder msg = new StringBuilder();
            msg.append("  + 0");
            msg.append(key);
            msg.append(" (");
            msg.append(this.convertCounts(executedTimes.get(key)));
            msg.append(")");
            LoggerFacade.INSTANCE.debug(this.getClass(), msg.toString());
            
            millis.add(UtilProvider.getDefault().getDateConverter().convertDateTimeToLong(key, IDateConverter.PATTERN__TIME_WITH_MILLIS));
        }
        
        Long sum = 0L;
        for (Long milli : millis) {
            sum += milli;
        }
        
        LoggerFacade.INSTANCE.debug(this.getClass(), "  --------------"); // NOI18N
        
        final long average = sum / millis.size();
        final String average2 = UtilProvider.getDefault().getDateConverter().convertLongToDateTime(average, IDateConverter.PATTERN__TIME_WITH_MILLIS);
        LoggerFacade.INSTANCE.debug(this.getClass(), "    " + average2 + " Average"); // NOI18N
        
        LoggerFacade.INSTANCE.debug(this.getClass(), "================================================================================"); // NOI18N
    }
    
    private String convertCounts(int counts) {
        if (counts < 10) {
            return "00" + counts; // NOI18N
        }
        
        if (counts < 100) {
            return "0" + counts; // NOI18N
        }
        
        return "" + counts; // NOI18N
    }
    
}
