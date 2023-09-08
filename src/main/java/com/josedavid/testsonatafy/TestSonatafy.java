/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.josedavid.testsonatafy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author josedavidochoaortiz
 */
public class TestSonatafy {

    public static void main(String[] args) {
        // Create previous and current objects
        Map<String, Object> previous = new HashMap<>();
        previous.put("firstName", "James");
        previous.put("subscription.status", "ACTIVE");
        Map<String, Object> inner = new HashMap<>();
        inner.put("name", "cinemark");
        inner.put("status", "ACTIVE");
        previous.put("subscriptionG", inner);
        Services toTest= new Services();
            toTest.add("Oil Change");
            toTest.add( "Tire Rotation");
            toTest.add("test");
        previous.put("services", toTest);

        Map<String, Object> current = new HashMap<>();
        current.put("firstName", "Jim");
        current.put("subscription.status", "EXPIRED");
        Services toTestCurrent= new Services();
            toTestCurrent.add("Tire Rotation");
            toTestCurrent.add( "Brake Service");
        current.put("services", toTestCurrent);
            Map<String, Object> innerc = new HashMap<>();
            innerc.put("status", "expired");
            current.put("subscriptionG", innerc);        try {
            DiffTool objt = new DiffTool(true);
            
            List<ChangeType> changes;
            // i'll get the differences between the objects
            changes = objt.diff(previous, current);
        
            // Print the changes
            for (ChangeType change : changes) {
                
                if (change instanceof PropertyUpdate) {
                    
                    PropertyUpdate propertyUpdate = (PropertyUpdate) change;
                    System.out.println("Property: " + propertyUpdate.getProperty() + " Previous: " + propertyUpdate.getPrevious() + " Current: " + propertyUpdate.getCurrent());
                }
                if (change instanceof ListUpdate) {
                    
                    ListUpdate listUpdate = (ListUpdate) change;
                    System.out.println("Property:  " + listUpdate.getProperty() + " Removed: " + listUpdate.getRemoved() +" Added: " + listUpdate.getAdded());
                }
            }
        } 
        catch (IdFieldOrAuditKeyException ex) {
            ex.printStackTrace();
        }

    }
}
