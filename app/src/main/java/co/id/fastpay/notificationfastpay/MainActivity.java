package co.id.fastpay.notificationfastpay;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import co.id.fastpay.fastpaynotification.ui.NotificationActivity;
import co.id.fastpay.fastpaynotification.utils.NotificationUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    APIInterface apiInterface;
    String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        setContentView(R.layout.activity_main);
        EditText inputOutletId = findViewById(R.id.input_outlet_id);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            if (inputOutletId.getText().toString() == null) {
                NotificationUtils.ID_OUTLET="FA0002";
            } else{
                NotificationUtils.ID_OUTLET=inputOutletId.getText().toString();
            }
            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(intent);
        });
        Button button2 = findViewById(R.id.register_push_notif);
//        button2.setOnClickListener(v -> {
//            fetchSavePartnerId(inputOutletId.getText().toString(),deviceId);
//        });
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationChannel channel = new NotificationChannel("NotifApps", "NotifyApps", NotificationManager.IMPORTANCE_HIGH);
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }
//        getCurrentFirebaseToken();
    }

    private void getCurrentFirebaseToken(){
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        Log.e("currentToken", token);
                        deviceId = token;
                        // Log and toast
                        String msg = getString(co.id.fastpay.fastpaynotification.R.string.msg_token_fmt, token);
                        Log.d("TAG", msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private JsonObject createBodyRequest(
            String outletId,
            String deviceId
    ){
        String requestBody = "{id_outlet: \""+outletId+"\"," +
                "device_id: \""+deviceId+"\"}";
        JsonParser jsonParser = new JsonParser();
        return (JsonObject)jsonParser.parse(requestBody);
    }

    private void fetchSavePartnerId(String outletId, String deviceId) {
        Call<Object> call1 = apiInterface.fetchSavePartnerId(createBodyRequest(outletId, deviceId));
        call1.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal mengirim Device ID dan Outlet ID ke server!", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }
}
