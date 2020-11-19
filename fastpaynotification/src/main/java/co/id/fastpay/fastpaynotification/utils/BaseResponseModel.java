package co.id.fastpay.fastpaynotification.utils;

import com.google.gson.annotations.SerializedName;

public class BaseResponseModel<T> {
    @SerializedName("response_code")
    private String responseCode;
    @SerializedName("response_desc")
    private String responseDesc;
    @SerializedName("time_request")
    private String timeRequest;
    private T data;
    @SerializedName("time_response")
    private String timeResponse;

    public BaseResponseModel(
            String responseCode,
            String responseDesc,
            String timeRequest,
            T data,
            String timeResponse){
        this.responseCode = responseCode;
        this.responseDesc = responseDesc;
        this.data = data;
        this.timeRequest = timeRequest;
        this.timeResponse = timeResponse;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public String getTimeRequest() {
        return timeRequest;
    }

    public T getData() {
        return data;
    }

    public String getTimeResponse() {
        return timeResponse;
    }
}
