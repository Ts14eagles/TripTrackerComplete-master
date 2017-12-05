package org.pltw.examples.triptracker;

import java.util.Date;
import android.util.Log;
import java.util.Comparator;
import com.backendless.persistence.DataQueryBuilder;

/**
 * Created by jfeli_000 on 11/19/2017.
 */

public class Trip implements IntentData, Comparable {
    private String objectId;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private boolean shared;
    private String ownerId;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        if (startDate==null){
            startDate = new Date();
        }

        return startDate;
    }

    public void setStartDate(Date startDate) {this.startDate = startDate;
    }

    public Date getEndDate() {

        if (endDate==null){
            endDate = new Date();
        }
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }
    public String getOwnerId() {return ownerId;}
    public void setOwnerId(String ownerId){
        this.ownerId = ownerId;
    }
    @Override
    public int compareTo(Object o){
        return name.compareTo(((Trip)o).getName());
    }
}


