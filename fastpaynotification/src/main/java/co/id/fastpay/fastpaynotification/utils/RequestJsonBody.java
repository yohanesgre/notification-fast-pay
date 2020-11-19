package co.id.fastpay.fastpaynotification.utils;

import com.google.gson.annotations.SerializedName;

public class RequestJsonBody<T> {
    @SerializedName("user_id")
    private String userId;
    private T data;
    @SerializedName("credential_data")
    private CredentialDataRequest credentialData;
    @SerializedName("additional_data")
    private AdditionalDataRequest additionalData;

    public RequestJsonBody(T data, CredentialDataRequest credentialData, AdditionalDataRequest additionalData){
        this.data = data;
        this.credentialData = credentialData;
        this.additionalData = additionalData;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public CredentialDataRequest getCredentialData() {
        return credentialData;
    }

    public void setCredentialData(CredentialDataRequest credentialData) {
        this.credentialData = credentialData;
    }

    public AdditionalDataRequest getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(AdditionalDataRequest additionalData) {
        this.additionalData = additionalData;
    }
}

