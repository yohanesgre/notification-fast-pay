package co.id.fastpay.fastpaynotification.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Objects;

import co.id.fastpay.fastpaynotification.R;
import co.id.fastpay.fastpaynotification.databinding.ActivityNotificationBinding;
import co.id.fastpay.fastpaynotification.ui.adapter.SectionsPagerAdapter;
import co.id.fastpay.fastpaynotification.utils.DummyContent;
import co.id.fastpay.fastpaynotification.utils.InboxModel;
import co.id.fastpay.fastpaynotification.utils.NotificationUtils;
import co.id.fastpay.fastpaynotification.utils.ViewModelFactory;

public class NotificationActivity extends AppCompatActivity{

    ViewModelFactory viewModelFactory;
    private NotificationViewModel viewModel;

    private ActivityNotificationBinding binding;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModelFactory = new ViewModelFactory();
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NotificationViewModel.class);
        viewModel.userId = NotificationUtils.ID_OUTLET;
        viewModel.deviceInfo = android.os.Build.DEVICE  + " Android/" + android.os.Build.VERSION.SDK;
        Log.d("Device Info", android.os.Build.DEVICE + " Android/" + android.os.Build.VERSION.SDK);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);
        subscribeInboxList();
        //subscribeUnreadCount();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ( NotificationUtils.checkInternetConnection(this)){
            viewModel.fetchRepos(false);
            viewModel.fetchReposUnread(true);
        }
        else{
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
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore previous state
    }


    private void subscribeInboxList(){
        viewModel.getRepos().observe(this, result->{
            viewModel.inboxModelList = result;
        });
//        viewModel.inboxModelList = DummyContent.getInstance().GetListInbox();
    }

    private void subscribeUnreadCount(){
        viewModel.getReposUnread().observe(this, result->{
            TabLayout.Tab tab = binding.tabs.getTabAt(0);
            View view = tab.getCustomView();
            TextView textView = view.findViewById(R.id.unread_count);
            if (result.getUnreadCount() == 0){
                textView.setVisibility(View.GONE);
            }else{
                textView.setVisibility(View.VISIBLE);
            }
            textView.setText(String.valueOf(result.getUnreadCount()));
        });
//        int unreadCount = 0;
//        List<InboxModel> listDummy = DummyContent.getInstance().GetListInbox();
//        for (int i = 0; i < listDummy.size(); i ++){
//            if (listDummy.get(i).getIsRead() != 1) {
//                unreadCount++;
//            }
//        }
//        TabLayout.Tab tab = binding.tabs.getTabAt(0);
//        View view = tab.getCustomView();
//        TextView textView = view.findViewById(R.id.unread_count);
//        if (unreadCount == 0){
//            textView.setVisibility(View.GONE);
//        }else{
//            textView.setVisibility(View.VISIBLE);
//        }
//        textView.setText(String.valueOf(unreadCount));
    }

    private void init(){
        if (NotificationUtils.hasMarshmallow()) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.material_color_blue_700, this.getTheme()));
        } else if (NotificationUtils.hasLollipop()) {
            //noinspection deprecation
            getWindow().setStatusBarColor(getResources().getColor(R.color.material_color_blue_700));
        }
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Notifikasi");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        binding.viewPager.setAdapter(mSectionsPagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);
        currentPage = binding.viewPager.getCurrentItem();
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (currentPage != position){
                    ((NotificationContentFragment)mSectionsPagerAdapter.getItem(currentPage)).destyorActionMode();
                    currentPage = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        for (int i = 0; i < binding.tabs.getTabCount(); i++) {
            TabLayout.Tab tab = binding.tabs.getTabAt(i);
            assert tab != null;
            tab.setCustomView(mSectionsPagerAdapter.getTabView(this, i));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
