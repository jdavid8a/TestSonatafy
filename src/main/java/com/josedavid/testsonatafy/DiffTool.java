package com.josedavid.testsonatafy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author josedavidochoaortiz
 */
public class DiffTool {
    
    
    //list of changes
    public List<ChangeType> changes;
    // parameter to indicate if the id field will be validated
    private boolean validateIdList= false;

    public DiffTool() {
    }
    
    public DiffTool(boolean pvalidateId) {
        this.validateIdList = pvalidateId;
    }

    //in this method i added the change to the list once the objets were analized
    public  List<ChangeType> diff(Object previous, Object current) throws IdFieldOrAuditKeyException {
        changes = new ArrayList<>();
        diffObjects("", current, previous);
        return changes;
    }

    //
    private void diffObjects(String prop, Object currentObject , Object previousObject) throws IdFieldOrAuditKeyException {
        
        //i validate if the previous object is not the equal to the current object
        if (!Objects.equals(previousObject, currentObject)) {
            if(!prop.equals("")){
                if(currentObject!=null && previousObject!= null){
                    PropertyUpdate toAdd = new PropertyUpdate(prop, previousObject, currentObject);
                    changes.add(toAdd);
                }
            }
        }

        //In this section I'll validate if the objects are list or else I'll analize the objects as map
        if (previousObject instanceof List && currentObject instanceof List) {
            List<Object> prevList = (List<Object>) previousObject;
            List<Object> currList = (List<Object>) currentObject;
            
            List<Object> remList = new ArrayList<>();
            List<Object> addList = new ArrayList<>();
            
            boolean flag = false;
            
            for (int i = 0; i < Math.max(prevList.size(), currList.size()); i++) {
                
                Object prevItem = i < prevList.size() ? prevList.get(i) : null;
                Object currItem = i < currList.size() ? currList.get(i) : null;
                
                String itemPath = prop + "[" + i + "]";
                
                if (currItem != null && validateObject(currItem)) {
                    flag = true;
                }
                
                diffObjects(itemPath, currItem, prevItem);
            }
            for (int i = 0; i < prevList.size(); i++) {
                if(!currList.contains(prevList.get(i)))
                    remList.add(prevList.get(i));
                
            }
            for (int i = 0; i < currList.size(); i++) {
                if(!prevList.contains(currList.get(i)))
                    addList.add(currList.get(i));
                
            }
            
            //i add to de list of removed and added services 
            ListUpdate toAddListUpdate =  new ListUpdate(prop, remList, addList);
            changes.add(toAddListUpdate);
            // i valaidate if the object have a id field or auditkey annotation
            if(this.validateIdList)
            {
                if (flag) {
                    throw new IdFieldOrAuditKeyException("The audit system lacks the information it needs to determine what has changed.");
                }
            }
        } else if (previousObject instanceof Map && currentObject instanceof Map) {
            Map<String, Object> previous = (Map<String, Object>) previousObject;
            Map<String, Object> current = (Map<String, Object>) currentObject;

            for (String key : previous.keySet()) {
                String nestedPath = prop.isEmpty() ? key : prop + "." + key;
                Object prevValue = previous.get(key);
                Object currValue = current.get(key);
                
                diffObjects(nestedPath,  currValue,prevValue);
            }

            //analize in recursive mode for adding dot notation to nested properties
            for (String key : current.keySet()) {
                if (!previous.containsKey(key)) {
                    String nestedPath = prop.isEmpty() ? key : prop + "." + key;
                    Object value = current.get(key);
                    ListUpdate toAddListUpdate =  new ListUpdate(nestedPath, null, value);
                    changes.add(toAddListUpdate);
                }
                
            }
           
        }
    }
    
    //in this method I'll validate if the id of a list item a field annotated with @AuditKey or have the name 'id', using java reflection
    private static boolean validateObject(Object obj) {
        Class<?> classforreflection = obj.getClass();
        Field[] fields = classforreflection.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(AuditKey.class)) {
                return true;
            }

            if (field.getName().equals("id")) {
                return true;
            }
        }

        return false;
    }
}


