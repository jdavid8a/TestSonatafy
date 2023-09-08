package com.josedavid.testsonatafy;

import java.util.ArrayList;

/**
 *
 * @author josedavidochoaortiz
 */

public class Services extends ArrayList{
    @AuditKey
    private String id;

    public Services() {
    }

    public Services(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
}
