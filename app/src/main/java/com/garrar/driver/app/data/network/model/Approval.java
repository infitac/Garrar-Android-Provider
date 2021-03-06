package com.garrar.driver.app.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by suthakar@appoets.com on 29-06-2018.
 */
public class Approval {

    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("contact_email")
    @Expose
    private String contactEmail;

    public String approvalContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String approvalContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
