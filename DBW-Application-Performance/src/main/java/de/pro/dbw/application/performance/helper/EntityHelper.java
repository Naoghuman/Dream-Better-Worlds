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

import java.util.List;
import javafx.collections.FXCollections;

/**
 *
 * @author PRo
 */
public class EntityHelper {
    
    private static final List<Integer> REPEAT_ENTITIES = FXCollections.observableArrayList();
    private static final List<Integer> WITH_INTERVAL = FXCollections.observableArrayList();
    
    private static EntityHelper instance = null;
    
    public static EntityHelper getDefault() {
        if (instance == null) {
            instance = new EntityHelper();
        }
        
        return instance;
    }
    
    private EntityHelper() { 
        this.initialize();
    }
    
    private void initialize() {
        this.initializeWithInterval();
        this.initializeRepeatEntities();
    }

    private void initializeRepeatEntities() {
        REPEAT_ENTITIES.add(50);
        REPEAT_ENTITIES.add(100);
        REPEAT_ENTITIES.add(250);
        REPEAT_ENTITIES.add(500);
        REPEAT_ENTITIES.add(1000);
        REPEAT_ENTITIES.add(2500);
        REPEAT_ENTITIES.add(5000);
    }

    private void initializeWithInterval() {
        WITH_INTERVAL.add(50);
        WITH_INTERVAL.add(100);
        WITH_INTERVAL.add(150);
        WITH_INTERVAL.add(200);
        WITH_INTERVAL.add(250);
        WITH_INTERVAL.add(300);
        WITH_INTERVAL.add(350);
        WITH_INTERVAL.add(400);
        WITH_INTERVAL.add(450);
        WITH_INTERVAL.add(500);
        WITH_INTERVAL.add(600);
        WITH_INTERVAL.add(700);
        WITH_INTERVAL.add(800);
        WITH_INTERVAL.add(900);
        WITH_INTERVAL.add(1000);
        WITH_INTERVAL.add(1250);
        WITH_INTERVAL.add(1500);
        WITH_INTERVAL.add(1750);
        WITH_INTERVAL.add(2000);
    }
    
    public List<Integer> getRepeatEntities() {
        return REPEAT_ENTITIES;
    }
    
    public List<Integer> getWithInterval() {
        return WITH_INTERVAL;
    }
    
}
