package co.id.fastpay.fastpaynotification.utils;

import com.google.gson.annotations.SerializedName;

public class CredentialDataRequest{
    @SerializedName("id_outlet")
    private String outletId;
    private String pin;
    @SerializedName("api_key")
    private String apiKey;

    public CredentialDataRequest(String outletId, String pin, String apiKey){
        this.outletId = outletId;
        this.pin = pin;
        this.apiKey = apiKey;
    }

    public String getOutletId() {
        return outletId;
    }

    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}