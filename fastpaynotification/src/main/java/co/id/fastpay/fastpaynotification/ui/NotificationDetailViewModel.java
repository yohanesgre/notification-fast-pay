package co.id.fastpay.fastpaynotification.ui;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.Date;

import co.id.fastpay.fastpaynotification.utils.NotificationUtils;
import co.id.fastpay.fastpaynotification.utils.InboxModel;
import co.id.fastpay.fastpaynotification.utils.Repository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NotificationDetailViewModel extends ViewModel {
    private final MutableLiveData<InboxModel> repo = new MutableLiveData<>();
    private final MutableLiveData<Boolean> refreshFinish = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable;
    private Repository repository;
    public String userId;
    public String deviceInfo;
    public int inboxId;
    public String inboxType;

    public NotificationDetailViewModel(Repository repository) {
        this.repository = repository;
        compositeDisposable = new CompositeDisposable();
    }

    LiveData<InboxModel> getRepo() {
        return repo;
    }
    public LiveData<Boolean> getRefreshFinish() {
        return refreshFinish;
    }
    public void setRefreshFinish(boolean refresh){
        refreshFinish.postValue(refresh);
    }
    LiveData<Boolean> getLoading() {
        return loading;
    }

    public void fetchRepos(Boolean isRefresh) {
        loading.setValue(true);
        compositeDisposable.add(repository.executeFetchInboxDetail(createBodyRequest(userId, inboxId, deviceInfo))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> {
                    if (isRefresh)
                        refreshFinish.postValue(false);
                    loading.postValue(true);
                })
                .doOnTerminate(()->{
                    if (isRefresh)
                        refreshFinish.postValue(true);
                })
                .subscribe(
                        result -> {
                            repo.postValue(result.getData());
                        },
                        throwable -> {
                            loading.postValue(false);
                        }
                ));
    }

    public void fetchRead() {
        compositeDisposable.add(repository.executeFetchInboxRead(createReadRequest(userId, inboxId, deviceInfo))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    private JsonObject createBodyRequest(
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

    private JsonObject createReadRequest(
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

    public String getToolbarTitle(){
        String title = "";
        if (inboxType.equals("INFO")) {
            return "Ingatkan Pelanggan";
        } else if (inboxType.equals("PROMO")) {
            return "Promo";
        }
        return title;
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

