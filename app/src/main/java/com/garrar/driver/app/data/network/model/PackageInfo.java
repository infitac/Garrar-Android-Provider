package com.garrar.driver.app.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageInfo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_request_id")
    @Expose
    private Integer userRequestId;
    @SerializedName("p_address")
    @Expose
    private String pAddress;
    @SerializedName("p_latitude")
    @Expose
    private String pLatitude;
    @SerializedName("p_longitude")
    @Expose
    private String pLongitude;
    @SerializedName("package_type")
    @Expose
    private String packageType;
    @SerializedName("no_of_box")
    @Expose
    private String noOfBox;
    @SerializedName("box_height")
    @Expose
    private String boxHeight;
    @SerializedName("box_width")
    @Expose
    private String boxWidth;
    @SerializedName("box_weight")
    @Expose
    private String boxWeight;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserRequestId() {
        return userRequestId;
    }

    public void setUserRequestId(Integer userRequestId) {
        this.userRequestId = userRequestId;
    }

    public String getPAddress() {
        return pAddress;
    }

    public void setPAddress(String pAddress) {
        this.pAddress = pAddress;
    }

    public String getPLatitude() {
        return pLatitude;
    }

    public void setPLatitude(String pLatitude) {
        this.pLatitude = pLatitude;
    }

    public String getPLongitude() {
        return pLongitude;
    }

    public void setPLongitude(String pLongitude) {
        this.pLongitude = pLongitude;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getNoOfBox() {
        return noOfBox;
    }

    public void setNoOfBox(String noOfBox) {
        this.noOfBox = noOfBox;
    }

    public String getBoxHeight() {
        return boxHeight;
    }

    public void setBoxHeight(String boxHeight) {
        this.boxHeight = boxHeight;
    }

    public String getBoxWidth() {
        return boxWidth;
    }

    public void setBoxWidth(String boxWidth) {
        this.boxWidth = boxWidth;
    }

    public String getBoxWeight() {
        return boxWeight;
    }

    public void setBoxWeight(String boxWeight) {
        this.boxWeight = boxWeight;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
