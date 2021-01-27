package com.garrar.driver.app.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CourierDetail {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("braintree_nonce")
    @Expose
    private Object braintreeNonce;
    @SerializedName("provider_id")
    @Expose
    private Integer providerId;
    @SerializedName("current_provider_id")
    @Expose
    private Integer currentProviderId;
    @SerializedName("service_type_id")
    @Expose
    private Integer serviceTypeId;
    @SerializedName("promocode_id")
    @Expose
    private Integer promocodeId;
    @SerializedName("rental_hours")
    @Expose
    private Object rentalHours;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("cancelled_by")
    @Expose
    private String cancelledBy;
    @SerializedName("cancel_reason")
    @Expose
    private Object cancelReason;
    @SerializedName("payment_mode")
    @Expose
    private String paymentMode;
    @SerializedName("paid")
    @Expose
    private Integer paid;
    @SerializedName("is_track")
    @Expose
    private String isTrack;
    @SerializedName("distance")
    @Expose
    private Integer distance;
    @SerializedName("travel_time")
    @Expose
    private Object travelTime;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("otp")
    @Expose
    private String otp;
    @SerializedName("s_address")
    @Expose
    private String sAddress;
    @SerializedName("s_latitude")
    @Expose
    private Double sLatitude;
    @SerializedName("s_longitude")
    @Expose
    private Double sLongitude;
    @SerializedName("d_address")
    @Expose
    private String dAddress;
    @SerializedName("d_latitude")
    @Expose
    private Double dLatitude;
    @SerializedName("d_longitude")
    @Expose
    private Double dLongitude;
    @SerializedName("track_distance")
    @Expose
    private Integer trackDistance;
    @SerializedName("track_latitude")
    @Expose
    private Double trackLatitude;
    @SerializedName("track_longitude")
    @Expose
    private Double trackLongitude;
    @SerializedName("destination_log")
    @Expose
    private String destinationLog;
    @SerializedName("is_drop_location")
    @Expose
    private Integer isDropLocation;
    @SerializedName("is_instant_ride")
    @Expose
    private Integer isInstantRide;
    @SerializedName("is_dispute")
    @Expose
    private Integer isDispute;
    @SerializedName("assigned_at")
    @Expose
    private String assignedAt;
    @SerializedName("schedule_at")
    @Expose
    private Object scheduleAt;
    @SerializedName("started_at")
    @Expose
    private Object startedAt;
    @SerializedName("finished_at")
    @Expose
    private Object finishedAt;
    @SerializedName("is_scheduled")
    @Expose
    private String isScheduled;
    @SerializedName("user_rated")
    @Expose
    private Integer userRated;
    @SerializedName("provider_rated")
    @Expose
    private Integer providerRated;
    @SerializedName("use_wallet")
    @Expose
    private Integer useWallet;
    @SerializedName("surge")
    @Expose
    private Integer surge;
    @SerializedName("route_key")
    @Expose
    private String routeKey;
    @SerializedName("nonce")
    @Expose
    private Object nonce;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("receiverinfo")
    @Expose
    private Receiverinfo receiverinfo;
    @SerializedName("packageinfo")
    @Expose
    private List<Packageinfo> packageinfo = null;
    @SerializedName("packageimages")
    @Expose
    private List<Packageimage> packageimages = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Object getBraintreeNonce() {
        return braintreeNonce;
    }

    public void setBraintreeNonce(Object braintreeNonce) {
        this.braintreeNonce = braintreeNonce;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Integer getCurrentProviderId() {
        return currentProviderId;
    }

    public void setCurrentProviderId(Integer currentProviderId) {
        this.currentProviderId = currentProviderId;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public Integer getPromocodeId() {
        return promocodeId;
    }

    public void setPromocodeId(Integer promocodeId) {
        this.promocodeId = promocodeId;
    }

    public Object getRentalHours() {
        return rentalHours;
    }

    public void setRentalHours(Object rentalHours) {
        this.rentalHours = rentalHours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public Object getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(Object cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public String getIsTrack() {
        return isTrack;
    }

    public void setIsTrack(String isTrack) {
        this.isTrack = isTrack;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public Object getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Object travelTime) {
        this.travelTime = travelTime;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getSAddress() {
        return sAddress;
    }

    public void setSAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public Double getSLatitude() {
        return sLatitude;
    }

    public void setSLatitude(Double sLatitude) {
        this.sLatitude = sLatitude;
    }

    public Double getSLongitude() {
        return sLongitude;
    }

    public void setSLongitude(Double sLongitude) {
        this.sLongitude = sLongitude;
    }

    public String getDAddress() {
        return dAddress;
    }

    public void setDAddress(String dAddress) {
        this.dAddress = dAddress;
    }

    public Double getDLatitude() {
        return dLatitude;
    }

    public void setDLatitude(Double dLatitude) {
        this.dLatitude = dLatitude;
    }

    public Double getDLongitude() {
        return dLongitude;
    }

    public void setDLongitude(Double dLongitude) {
        this.dLongitude = dLongitude;
    }

    public Integer getTrackDistance() {
        return trackDistance;
    }

    public void setTrackDistance(Integer trackDistance) {
        this.trackDistance = trackDistance;
    }

    public Double getTrackLatitude() {
        return trackLatitude;
    }

    public void setTrackLatitude(Double trackLatitude) {
        this.trackLatitude = trackLatitude;
    }

    public Double getTrackLongitude() {
        return trackLongitude;
    }

    public void setTrackLongitude(Double trackLongitude) {
        this.trackLongitude = trackLongitude;
    }

    public String getDestinationLog() {
        return destinationLog;
    }

    public void setDestinationLog(String destinationLog) {
        this.destinationLog = destinationLog;
    }

    public Integer getIsDropLocation() {
        return isDropLocation;
    }

    public void setIsDropLocation(Integer isDropLocation) {
        this.isDropLocation = isDropLocation;
    }

    public Integer getIsInstantRide() {
        return isInstantRide;
    }

    public void setIsInstantRide(Integer isInstantRide) {
        this.isInstantRide = isInstantRide;
    }

    public Integer getIsDispute() {
        return isDispute;
    }

    public void setIsDispute(Integer isDispute) {
        this.isDispute = isDispute;
    }

    public String getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(String assignedAt) {
        this.assignedAt = assignedAt;
    }

    public Object getScheduleAt() {
        return scheduleAt;
    }

    public void setScheduleAt(Object scheduleAt) {
        this.scheduleAt = scheduleAt;
    }

    public Object getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Object startedAt) {
        this.startedAt = startedAt;
    }

    public Object getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Object finishedAt) {
        this.finishedAt = finishedAt;
    }

    public String getIsScheduled() {
        return isScheduled;
    }

    public void setIsScheduled(String isScheduled) {
        this.isScheduled = isScheduled;
    }

    public Integer getUserRated() {
        return userRated;
    }

    public void setUserRated(Integer userRated) {
        this.userRated = userRated;
    }

    public Integer getProviderRated() {
        return providerRated;
    }

    public void setProviderRated(Integer providerRated) {
        this.providerRated = providerRated;
    }

    public Integer getUseWallet() {
        return useWallet;
    }

    public void setUseWallet(Integer useWallet) {
        this.useWallet = useWallet;
    }

    public Integer getSurge() {
        return surge;
    }

    public void setSurge(Integer surge) {
        this.surge = surge;
    }

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }

    public Object getNonce() {
        return nonce;
    }

    public void setNonce(Object nonce) {
        this.nonce = nonce;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
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

    public Receiverinfo getReceiverinfo() {
        return receiverinfo;
    }

    public void setReceiverinfo(Receiverinfo receiverinfo) {
        this.receiverinfo = receiverinfo;
    }

    public List<Packageinfo> getPackageinfo() {
        return packageinfo;
    }

    public void setPackageinfo(List<Packageinfo> packageinfo) {
        this.packageinfo = packageinfo;
    }

    public List<Packageimage> getPackageimages() {
        return packageimages;
    }

    public void setPackageimages(List<Packageimage> packageimages) {
        this.packageimages = packageimages;
    }

    public class Packageimage {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_request_id")
        @Expose
        private Integer userRequestId;
        @SerializedName("parcel_image")
        @Expose
        private String parcelImage;
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

        public String getParcelImage() {
            return parcelImage;
        }

        public void setParcelImage(String parcelImage) {
            this.parcelImage = parcelImage;
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

    public class Packageinfo {

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

    public class Receiverinfo {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_request_id")
        @Expose
        private Integer userRequestId;
        @SerializedName("receiver_name")
        @Expose
        private String receiverName;
        @SerializedName("receiver_phone")
        @Expose
        private String receiverPhone;
        @SerializedName("pickup_instructions")
        @Expose
        private String pickupInstructions;
        @SerializedName("delivery_instructions")
        @Expose
        private String deliveryInstructions;
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

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getReceiverPhone() {
            return receiverPhone;
        }

        public void setReceiverPhone(String receiverPhone) {
            this.receiverPhone = receiverPhone;
        }

        public String getPickupInstructions() {
            return pickupInstructions;
        }

        public void setPickupInstructions(String pickupInstructions) {
            this.pickupInstructions = pickupInstructions;
        }

        public String getDeliveryInstructions() {
            return deliveryInstructions;
        }

        public void setDeliveryInstructions(String deliveryInstructions) {
            this.deliveryInstructions = deliveryInstructions;
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
}
