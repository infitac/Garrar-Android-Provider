package com.garrar.driver.app.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EstimateFareNew implements Serializable {
    @SerializedName("status")
    @Expose
    private Boolean status;

    @SerializedName("total_estimated_fare")
    @Expose
    private Integer total_estimated_fare;

    @SerializedName("total_distance")
    @Expose
    private Double total_distance;

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Integer getTotal_estimated_fare() {
        return total_estimated_fare;
    }

    public Double getTotal_distance() {
        return total_distance;
    }

    public class Datum {

        @SerializedName("estimated_fare")
        @Expose
        private Integer estimatedFare;
        @SerializedName("distance")
        @Expose
        private Double distance;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("tax_price")
        @Expose
        private Integer taxPrice;
        @SerializedName("base_price")
        @Expose
        private Integer basePrice;
        @SerializedName("service_type")
        @Expose
        private Integer serviceType;
        @SerializedName("service")
        @Expose
        private Service service;

        public Integer getEstimatedFare() {
            return estimatedFare;
        }

        public void setEstimatedFare(Integer estimatedFare) {
            this.estimatedFare = estimatedFare;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public Integer getTaxPrice() {
            return taxPrice;
        }

        public void setTaxPrice(Integer taxPrice) {
            this.taxPrice = taxPrice;
        }

        public Integer getBasePrice() {
            return basePrice;
        }

        public void setBasePrice(Integer basePrice) {
            this.basePrice = basePrice;
        }

        public Integer getServiceType() {
            return serviceType;
        }

        public void setServiceType(Integer serviceType) {
            this.serviceType = serviceType;
        }

        public Service getService() {
            return service;
        }

        public void setService(Service service) {
            this.service = service;
        }

    }

    public class Service {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("provider_name")
        @Expose
        private String providerName;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("marker")
        @Expose
        private String marker;
        @SerializedName("capacity")
        @Expose
        private Integer capacity;
        @SerializedName("fixed")
        @Expose
        private Integer fixed;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("minute")
        @Expose
        private Integer minute;
        @SerializedName("hour")
        @Expose
        private Integer hour;
        @SerializedName("distance")
        @Expose
        private Integer distance;
        @SerializedName("calculator")
        @Expose
        private String calculator;
        @SerializedName("description")
        @Expose
        private Object description;
        @SerializedName("waiting_free_mins")
        @Expose
        private Integer waitingFreeMins;
        @SerializedName("waiting_min_charge")
        @Expose
        private Integer waitingMinCharge;
        @SerializedName("status")
        @Expose
        private Integer status;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProviderName() {
            return providerName;
        }

        public void setProviderName(String providerName) {
            this.providerName = providerName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getMarker() {
            return marker;
        }

        public void setMarker(String marker) {
            this.marker = marker;
        }

        public Integer getCapacity() {
            return capacity;
        }

        public void setCapacity(Integer capacity) {
            this.capacity = capacity;
        }

        public Integer getFixed() {
            return fixed;
        }

        public void setFixed(Integer fixed) {
            this.fixed = fixed;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getMinute() {
            return minute;
        }

        public void setMinute(Integer minute) {
            this.minute = minute;
        }

        public Integer getHour() {
            return hour;
        }

        public void setHour(Integer hour) {
            this.hour = hour;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public String getCalculator() {
            return calculator;
        }

        public void setCalculator(String calculator) {
            this.calculator = calculator;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public Integer getWaitingFreeMins() {
            return waitingFreeMins;
        }

        public void setWaitingFreeMins(Integer waitingFreeMins) {
            this.waitingFreeMins = waitingFreeMins;
        }

        public Integer getWaitingMinCharge() {
            return waitingMinCharge;
        }

        public void setWaitingMinCharge(Integer waitingMinCharge) {
            this.waitingMinCharge = waitingMinCharge;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

    }
}