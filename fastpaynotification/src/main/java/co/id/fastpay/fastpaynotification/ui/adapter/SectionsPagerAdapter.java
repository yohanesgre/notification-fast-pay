package co.id.fastpay.fastpaynotification.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import co.id.fastpay.fastpaynotification.R;
import co.id.fastpay.fastpaynotification.databinding.NotifTabsBinding;
import co.id.fastpay.fastpaynotification.ui.NotificationContentFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Fragment[] childFragments;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        childFragments = new Fragment[] {
                NotificationContentFragment.newInstance(0), //0
                NotificationContentFragment.newInstance(1), //1
                NotificationContentFragment.newInstance(2) //2
        };
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return childFragments[position];
    }

    @Override
    public int getCount() {
        return childFragments.length; //3 items
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Semua";
            case 1:
                return "Promo";
            case 2:
                return "Info";
        }
        return null;
    }
    private String tabTitles[] = new String[] { "Semua", "Promo", "Info" };

    public View getTabView( Context context, int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        NotifTabsBinding bindingUnread = NotifTabsBinding.inflate(LayoutInflater.from(context));
        bindingUnread.titleTab.setText(tabTitles[position]);
        bindingUnread.unreadCount.setVisibility(View.GONE);
        return bindingUnread.getRoot();
    }
}
