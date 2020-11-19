package co.id.fastpay.notificationfastpay;

import com.google.gson.JsonObject;

import co.id.fastpay.fastpaynotification.utils.Urls;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

interface APIInterface {

    @POST(Urls.FetchSavePartnerId)
    Call<Object> fetchSavePartnerId(@Body JsonObject body);
}