package co.id.fastpay.fastpaynotification.utils;

import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.Observable;

public class Repository {

    private ApiCallInterface apiCallInterface;

    public Repository() {
        apiCallInterface = ApiService.getClient().create(ApiCallInterface.class);
    }

    public Observable<BaseResponseModel<List<InboxModel>>> executeFetchInboxList(JsonObject body) {
        return apiCallInterface.fetchInboxList(body);
    }

    public Observable<BaseResponseModel<InboxModel>> executeFetchInboxDetail(JsonObject body) {
        return apiCallInterface.fetchInboxDetail(body);
    }

    public Observable<Object> executeFetchInboxDelete(JsonObject body) {
        return apiCallInterface.fetchInboxDelete(body);
    }

    public Observable<Object> executeFetchInboxRead(JsonObject body) {
        return apiCallInterface.fetchInboxRead(body);
    }

    public Observable<Object> executeFetchSavePartnerId(JsonObject body) {
        return apiCallInterface.fetchSavePartnerId(body);
    }

    public Observable<BaseResponseModel<UnreadCountModel>> executeInboxUnreadCount(JsonObject body) {
        return apiCallInterface.fetchInboxUnreadCount(body);
    }
}
