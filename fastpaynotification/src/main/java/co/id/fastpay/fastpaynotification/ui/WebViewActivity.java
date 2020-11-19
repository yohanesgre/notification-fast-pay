package co.id.fastpay.fastpaynotification.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import java.util.Objects;

import co.id.fastpay.fastpaynotification.R;
import co.id.fastpay.fastpaynotification.databinding.ActivityWebviewBinding;
import co.id.fastpay.fastpaynotification.utils.NotificationUtils;

public class WebViewActivity extends AppCompatActivity {
    private String postUrl = "";
    private ActivityWebviewBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        postUrl = getIntent().getStringExtra("Url");
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_webview);
        if (NotificationUtils.hasMarshmallow()) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.material_color_blue_700, this.getTheme()));
        } else if (NotificationUtils.hasLollipop()) {
            //noinspection deprecation
            getWindow().setStatusBarColor(getResources().getColor(R.color.material_color_blue_700));
        }
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.loadUrl(postUrl);
        binding.webView.setHorizontalScrollBarEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_webview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        if (id == R.id.menu_open_browser) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(postUrl));
            startActivity(browserIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
