package co.id.fastpay.notificationfastpay;

import java.util.concurrent.TimeUnit;

import co.id.fastpay.fastpaynotification.utils.ApiCallInterface;
import co.id.fastpay.fastpaynotification.utils.NotificationUtils;
import co.id.fastpay.fastpaynotification.utils.Urls;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
        private static Retrofit retrofit = null;

        public static Retrofit getClient(){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient
                    .Builder()
                    .addInterceptor(chain -> {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .header("APIKEY", NotificationUtils.API_KEY)
                                .header("USERID", NotificationUtils.USER_ID)
                                .header("VIA", "MOBILE")
                                //.method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    })
                    .addInterceptor(interceptor)
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS)
                    .build();

            if (retrofit == null){
                retrofit = new Retrofit.Builder()
                        .baseUrl(Urls.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build();
            }
            return retrofit;
        }
}