package com.example.personaldev.policeonduty.dashboard.view_criminal_report;

import java.io.Serializable;

public class CriminalModel implements Serializable {

    private String criminalName;
    public String Id;

    public CriminalModel(String criminalName, String id) {
        this.criminalName = criminalName;
        this.Id = id;
    }

    public String getCriminalName() {
        return criminalName;
    }

    public void setCriminalName(String criminalName) {
        this.criminalName = criminalName;
    }
}
