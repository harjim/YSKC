package com.yskc.rs.models.ownership;

import com.yskc.rs.entity.OwnershipEntity;

import java.io.Serializable;
import java.util.List;

public class OwnershipModel implements Serializable {
    private List<OwnershipEntity> ownershipList;
    private String capitalUnit;

    public List<OwnershipEntity> getOwnershipList() {
        return ownershipList;
    }

    public void setOwnershipList(List<OwnershipEntity> ownershipList) {
        this.ownershipList = ownershipList;
    }

    public String getCapitalUnit() {
        return capitalUnit;
    }

    public void setCapitalUnit(String capitalUnit) {
        this.capitalUnit = capitalUnit;
    }
}
