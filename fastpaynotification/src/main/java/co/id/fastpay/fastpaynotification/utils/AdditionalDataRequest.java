package co.id.fastpay.fastpaynotification.utils;

import com.google.gson.annotations.SerializedName;

public class AdditionalDataRequest{
    @SerializedName("transmission_datetime")
    private String transmissionDateTime;
    private String uuid;
    private String tokenizer;
    @SerializedName("app_id")
    private String appId;
    @SerializedName("device_information")
    private String deviceInformation;

    public AdditionalDataRequest(String transmissionDateTime, String uuid, String tokenizer, String appId, String deviceInformation){
        this.transmissionDateTime = transmissionDateTime;
        this.uuid = uuid;
        this.tokenizer = tokenizer;
        this.appId = appId;
        this.deviceInformation = deviceInformation;
    }

    public String getTransmissionDateTime() {
        return transmissionDateTime;
    }

    public void setTransmissionDateTime(String transmissionDateTime) {
        this.transmissionDateTime = transmissionDateTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTokenizer() {
        return tokenizer;
    }

    public void setTokenizer(String tokenizer) {
        this.tokenizer = tokenizer;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDeviceInformation() {
        return deviceInformation;
    }

    public void setDeviceInformation(String deviceInformation) {
        this.deviceInformation = deviceInformation;
    }
}