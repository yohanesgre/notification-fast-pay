package co.id.fastpay.fastpaynotification.utils;

import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ${Saquib} on 12-08-2018.
 */
public interface ApiCallInterface {
    @POST(Urls.FetchInboxList)
    Observable<BaseResponseModel<List<InboxModel>>> fetchInboxList(@Body JsonObject body);

    @POST(Urls.FetchInboxRead)
    Observable<Object> fetchInboxRead(@Body JsonObject body);

    @POST(Urls.FetchInboxDelete)
    Observable<Object> fetchInboxDelete(@Body JsonObject body);

    @POST(Urls.FetchSavePartnerId)
    Observable<Object> fetchSavePartnerId(@Body JsonObject body);

    @POST(Urls.FetchInboxDetail)
    Observable<BaseResponseModel<InboxModel>> fetchInboxDetail(@Body JsonObject body);

    @POST(Urls.FetchInboxCountUnread)
    Observable<BaseResponseModel<UnreadCountModel>> fetchInboxUnreadCount(@Body JsonObject body);
}
