package co.id.fastpay.fastpaynotification.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import java.util.Objects;

import co.id.fastpay.fastpaynotification.R;
import co.id.fastpay.fastpaynotification.databinding.ActivityNotificationDetailBinding;
import co.id.fastpay.fastpaynotification.ui.adapter.TagihanAdapter;
import co.id.fastpay.fastpaynotification.utils.NotificationUtils;
import co.id.fastpay.fastpaynotification.utils.InboxModel;
import co.id.fastpay.fastpaynotification.utils.ViewModelFactory;

public class NotificationDetailActivity extends AppCompatActivity {
    ViewModelFactory viewModelFactory;
    private NotificationDetailViewModel viewModel;

    private ActivityNotificationDetailBinding binding;

    TagihanAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelFactory = new ViewModelFactory();
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NotificationDetailViewModel.class);
        viewModel.inboxType = getIntent().getStringExtra("InboxType");
        viewModel.inboxId = getIntent().getIntExtra("InboxID", 0);
        viewModel.userId = NotificationUtils.ID_OUTLET;
        viewModel.deviceInfo = android.os.Build.DEVICE  + " Android/" + android.os.Build.VERSION.SDK;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification_detail);
        init();
        subscribeInboxList();
        subscribeRefresh();
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.fetchRepos(true);
            }
        });
    }

    void init(){
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(viewModel.getToolbarTitle());
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        if (NotificationUtils.hasMarshmallow()) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.material_color_blue_700, this.getTheme()));
        } else if (NotificationUtils.hasLollipop()) {
            //noinspection deprecation
            getWindow().setStatusBarColor(getResources().getColor(R.color.material_color_blue_700));
        }
        binding.layoutBody.rvTagihan.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        binding.layoutBody.rvTagihan.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.layoutBody.rvTagihan.setHasFixedSize(true);
    }


    private void initRecyclerView(InboxModel inboxModel){
        mAdapter = new TagihanAdapter(inboxModel);
        binding.layoutBody.rvTagihan.setAdapter(mAdapter);
    }

    private void subscribeRefresh(){
        viewModel.getRefreshFinish().observe(this, refresh->{
            if (refresh){
                viewModel.setRefreshFinish(false);
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void subscribeInboxList(){
        viewModel.getRepo().observe(this, inboxModel -> {
            if (inboxModel != null){
                binding.divider.setVisibility(View.VISIBLE);
                binding.layoutBody.parent.setVisibility(View.VISIBLE);
                if (inboxModel.getIsRead()==0){
                    viewModel.fetchRead();
                }
                Glide.with(this)
                        .load(inboxModel.getUrlImage())
                        .into(binding.layoutBody.headerImage);
                binding.headerTitle.setText(inboxModel.getTitle());
                if (Build.VERSION.SDK_INT >= 24)
                {
                    binding.layoutBody.headerContent.setText(Html.fromHtml(inboxModel.getContent() , Html.FROM_HTML_MODE_LEGACY));
                }
                else
                {
                    binding.layoutBody.headerContent.setText(Html.fromHtml(inboxModel.getContent()));
                }
                if (inboxModel.getType().equals("INFO")){
                    if (inboxModel.getAdditionalDataModel()!=null){
                        binding.layoutBody.parentTagihan.setVisibility(View.VISIBLE);
                        binding.layoutBody.bodyCustName.setText("Nama: "+inboxModel.getCustomerName());
                        binding.layoutBody.bodyCustTelp.setText("Telepon: " + inboxModel.getCustomerPhone());
                        binding.layoutBody.bodyCustDueDate.setText("Tanggal Tagihan: "+ NotificationUtils.dateConverter(inboxModel.getBillDate()));
                        binding.layoutBody.footerBtnIngatkan.setOnClickListener(v -> {
                            String url = "https://api.whatsapp.com/send?phone=" +
                                    inboxModel.getCustomerPhone() +
                                    "&text=" +
                                    NotificationUtils.getGreetingString() +
                                    " Bapak/Ibu " +
                                    inboxModel.getCustomerName();
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(browserIntent);
                        });
                        initRecyclerView(inboxModel);
                    }
                }else{
                    binding.layoutBody.parentTagihan.setVisibility(View.GONE);
                }
                if (inboxModel.getUrlWeb()!=null){
                    binding.layoutBody.btnLihat.setVisibility(View.VISIBLE);
                    binding.layoutBody.btnLihat.setOnClickListener(v -> {
                        Intent intent = new Intent(NotificationDetailActivity.this, WebViewActivity.class);
                        intent.putExtra("Url", inboxModel.getUrlWeb());
                        startActivity(intent);
                    });
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ( NotificationUtils.checkInternetConnection(this)) {
            viewModel.fetchRepos(false);
        }else{
            //Toast.makeText(this, "Anda tidak sedang terhubung ke internet. Tolong periksa kembali koneksi internet Anda!",Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(this, R.style.AlertDialogCustom)
                    .setTitle("Tidak ada koneksi Internet!")
                    .setMessage("Anda tidak sedang terhubung ke internet. Tolong periksa kembali koneksi internet Anda!")
                    .setCancelable(true)
                    //.setNeutralButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
