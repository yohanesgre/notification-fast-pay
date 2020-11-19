package co.id.fastpay.fastpaynotification.ui;


import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import co.id.fastpay.fastpaynotification.ui.adapter.NotificationAdapterByDate;
import co.id.fastpay.fastpaynotification.utils.NotificationUtils;
import co.id.fastpay.fastpaynotification.utils.InboxModel;
import co.id.fastpay.fastpaynotification.utils.Repository;
import co.id.fastpay.fastpaynotification.utils.UnreadCountModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NotificationViewModel extends ViewModel {
    private final MutableLiveData<List<InboxModel>> repos = new MutableLiveData<>();
    private final MutableLiveData<List<InboxModel>> loadMores = new MutableLiveData<>();
    private final MutableLiveData<UnreadCountModel> reposUnread = new MutableLiveData<>();
    private final MutableLiveData<Boolean> refreshFinish = new MutableLiveData<>();
    private final MutableLiveData<Boolean> refreshUnreadFinish = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadMore = new MutableLiveData<>();
    private final MutableLiveData<NotificationAdapterByDate[]> liveAdapters =  new MutableLiveData<>();
    private CompositeDisposable compositeDisposable;
    private Repository repository;
    public String userId;
    public String deviceInfo;
    public List<InboxModel> inboxModelList;
    public List<InboxModel> newPageInboxList;

    public NotificationViewModel(Repository repository) {
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
    }

    LiveData<Boolean> getLoading() {
        return loading;
    }
    public LiveData<Boolean> getLoadMore() {
        return loadMore;
    }
    public LiveData<List<InboxModel>> getLoadMores() {
        return loadMores;
    }
    public LiveData<List<InboxModel>> getRepos() { return repos; }
    public LiveData<UnreadCountModel> getReposUnread() { return reposUnread; }
    public LiveData<Boolean> getRefreshFinish() {
        return refreshFinish;
    }
    public LiveData<Boolean> getUnreadRefreshFinish() {
        return refreshUnreadFinish;
    }
    public void setRefreshFinish(boolean refresh){
        refreshFinish.postValue(refresh);
    }
    public void setUnreadRefreshFinish(boolean refresh){
        refreshUnreadFinish.postValue(refresh);
    }
    public void setLoadMore(boolean load){
        loadMore.postValue(load);
    }

    public void fetchRepos(Boolean isRefresh) {
        //loading.setValue(true);
            compositeDisposable.add(repository.executeFetchInboxList(createBodyRequest(userId, deviceInfo, 0))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe((d) -> {
                        if (isRefresh)
                            refreshFinish.postValue(false);
                        //refreshFinish.postValue(false);
                        loading.postValue(true);
                    })
                    .doOnTerminate(()->{
                        if (isRefresh)
                            refreshFinish.postValue(true);
                    })
                    .subscribe(
                            result -> {
                                repos.postValue(result.getData());
                            },
                            throwable -> {
                                loading.postValue(false);
                            }
                    ));
    }

    public void fetchLoadMore(int page) {
        loading.setValue(true);
        compositeDisposable.add(repository.executeFetchInboxList(createBodyRequest(userId, deviceInfo, page))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> {
                    loadMore.postValue(false);
                    loading.postValue(true);
                })
                .doOnTerminate(()->{
                    loadMore.postValue(true);
                })
                .subscribe(
                        result -> {
                            loadMores.postValue(result.getData());
                        },
                        throwable -> {
                            loading.postValue(false);
                        }
                ));
    }

    public void fetchReposUnread(Boolean isRefresh) {
        loading.setValue(true);
        compositeDisposable.add(repository.executeInboxUnreadCount(createUnreadCountRequest(userId, deviceInfo))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> {
                    if(isRefresh)
                        refreshUnreadFinish.postValue(false);
                    loading.postValue(true);
                })
                .doOnTerminate(()->{
                    if (isRefresh)
                        refreshUnreadFinish.postValue(true);
                })
                .subscribe(
                        result -> {
                            reposUnread.postValue(result.getData());
                        },
                        throwable -> {
                            loading.postValue(false);
                        }
                ));
    }

    public void fetchDelete(int inboxId) {
        compositeDisposable.add(repository.executeFetchInboxDelete(createDeleteRequest(userId, inboxId, deviceInfo))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result->{
                    fetchRepos(false);
                }));
    }

    private JsonObject createBodyRequest(
            String userId,
            String deviceInfo,
            int page
    ){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String timestamp = simpleDateFormat.format(new Date());
        String requestBody = "{\"user_id\" : \""+NotificationUtils.ID_OUTLET+"\"," +
                "\"data\" : {" +
                "\"type\" : \"ALL\"," +
                "\"offset\" : "+page+"," +
                "\"limit\" : 15}," +
                "\"credential_data\" : {" +
                "\"id_outlet\" :\""+ NotificationUtils.ID_OUTLET+"\"," +
                "\"pin\" : \"000000\"," +
                "\"api_key\" : \""+ NotificationUtils.API_KEY_BODY+"\"}," +
                "\"additional_data\" :{" +
                "\"transmission_datetime\" : \""+timestamp+"\"," +
                "\"uuid\" : \"\"," +
                "\"tokenizer\" : \"\"," +
                "\"app_id\" : \"MOBILESBF\"," +
                "\"device_information\" : \""+deviceInfo+"\"}}";
        JsonParser jsonParser = new JsonParser();
        return (JsonObject)jsonParser.parse(requestBody);
    }

    private JsonObject createDeleteRequest(
            String userId,
            int inboxId,
            String deviceInfo
    ){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String timestamp = simpleDateFormat.format(new Date());
        String requestBody = "{\"user_id\" : \""+NotificationUtils.ID_OUTLET+"\"," +
                "\"data\" : {" +
                "\"inboxid\" : "+inboxId+"}," +
                "\"credential_data\" : {" +
                "\"id_outlet\" :\""+ NotificationUtils.ID_OUTLET+"\"," +
                "\"pin\" : \"000000\"," +
                "\"api_key\" : \""+ NotificationUtils.API_KEY_BODY+"\"}," +
                "\"additional_data\" :{" +
                "\"transmission_datetime\" : \""+timestamp+"\"," +
                "\"uuid\" : \"\"," +
                "\"tokenizer\" : \"\"," +
                "\"app_id\" : \"MOBILESBF\"," +
                "\"device_information\" : \""+deviceInfo+"\"}}";
        JsonParser jsonParser = new JsonParser();
        return (JsonObject)jsonParser.parse(requestBody);
    }

    private JsonObject createUnreadCountRequest(
            String userId,
            String deviceInfo
    ){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String timestamp = simpleDateFormat.format(new Date());
        String requestBody = "{\"user_id\" : \""+NotificationUtils.ID_OUTLET+"\"," +
                "\"data\" : {" +
                "\"type\" : \"ALL\"}," +
                "\"credential_data\" : {" +
                "\"id_outlet\" :\""+ NotificationUtils.ID_OUTLET+"\"," +
                "\"pin\" : \"000000\"," +
                "\"api_key\" : \""+ NotificationUtils.API_KEY_BODY+"\"}," +
                "\"additional_data\" :{" +
                "\"transmission_datetime\" : \""+timestamp+"\"," +
                "\"uuid\" : \"\"," +
                "\"tokenizer\" : \"\"," +
                "\"app_id\" : \"MOBILESBF\"," +
                "\"device_information\" : \""+deviceInfo+"\"}}";
        JsonParser jsonParser = new JsonParser();
        return (JsonObject)jsonParser.parse(requestBody);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }
}