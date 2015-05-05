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
package de.pro.dbw.util.impl;

import de.pro.dbw.util.api.IDateConverter;
import de.pro.lib.logger.api.LoggerFacade;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import org.joda.time.MutableDateTime;

/**
 *
 * @author PRo
 */
public class DateConverter implements IDateConverter {
    
    public Long addDays(int days) {
        final MutableDateTime mdtNow = MutableDateTime.now();
        mdtNow.addDays(days);
        
        return mdtNow.getMillis();
    }
    
    public Long convertDateTimeToLong(String dateTime, String pattern) {
        LoggerFacade.getDefault().debug(DateConverter.class, String.format(
                    "Convert %s with %s to Long", dateTime, pattern));
        System.out.println("XXX DateConverter.convertDateTimeToLong() -> not threadsave");
        
        try {
            final DateFormat formatter = new SimpleDateFormat(pattern);
            final Date converted = (Date) formatter.parse(dateTime);
            return converted.getTime();
        } catch (ParseException pe) {
            LoggerFacade.getDefault().error(DateConverter.class, String.format(
                    "Can't convert %s with %s to Long", dateTime, pattern), pe);
        }
        
        return 0L;
    }
    
    public String convertLongToDateTime(Long millis, String pattern) {
        System.out.println("XXX DateConverter.convertLongToDateTime() -> remove jodatime");
        final MutableDateTime mdt = new MutableDateTime(millis);
        return mdt.toString(pattern);
    }
    
    public Boolean isAfter(int days, Long time) {
        final MutableDateTime mdtNow = MutableDateTime.now();
        mdtNow.addDays(days);
        
        final MutableDateTime mdtTime = new MutableDateTime(time);
        return mdtTime.isAfter(mdtNow);
    }
    
    public Boolean isBefore(int days, Long time) {
        final MutableDateTime mdtNow = MutableDateTime.now();
        mdtNow.addDays(days);
        
        final MutableDateTime mdtTime = new MutableDateTime(time);
        return mdtTime.isBefore(mdtNow);
    }
    
    public Boolean isValid(String pattern, String dateTime) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            if (pattern.equals(PATTERN__DATE)) {
                LocalDate.parse(dateTime, formatter);
                return Boolean.TRUE;
            }
            if (pattern.equals(PATTERN__TIME)) {
                LocalTime.parse(dateTime, formatter);
                return Boolean.TRUE;
            }
        } catch (DateTimeParseException dtpe) {
            /* not needed */
        }
        
        return Boolean.FALSE;
    }
}
