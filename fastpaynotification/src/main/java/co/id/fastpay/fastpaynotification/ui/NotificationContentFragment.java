package co.id.fastpay.fastpaynotification.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import co.id.fastpay.fastpaynotification.databinding.FragmentContentNotificationBinding;
import co.id.fastpay.fastpaynotification.ui.adapter.EndlessOnScrollListener;
import co.id.fastpay.fastpaynotification.ui.adapter.NotificationAdapterByDate;
import co.id.fastpay.fastpaynotification.utils.InboxModel;
import co.id.fastpay.fastpaynotification.utils.NotificationUtils;

public class NotificationContentFragment extends Fragment{

    private NotificationViewModel viewModel;
    private FragmentContentNotificationBinding binding;

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String TAG = NotificationContentFragment.class.getSimpleName();

    private int mSection;
    public NotificationAdapterByDate mAdapter;
    private Comparator<InboxModel> inboxComparator;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSection = getArguments().getInt(ARG_SECTION_NUMBER);
            Log.d(TAG, "Creating new Fragment for Section " + mSection);
        }
        setHasOptionsMenu(true);
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(NotificationViewModel.class);
    }

    public static NotificationContentFragment newInstance(int sectionNumber) {
        NotificationContentFragment fragment = new NotificationContentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentContentNotificationBinding.inflate(inflater, container, false);
        binding.rvContentNotification.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        //binding.rvContentNotification.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), LinearLayoutManager.VERTICAL));
        binding.rvContentNotification.setHasFixedSize(true);
        subscribeList();
        subscribeRefresh();
        binding.rvContentNotification.addOnScrollListener(scrollData());
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            if ( NotificationUtils.checkInternetConnection(requireContext())){
                viewModel.fetchRepos(true);
                viewModel.fetchReposUnread(true);
                binding.rvContentNotification.addOnScrollListener(scrollData());
            }
            else{
                Toast.makeText(requireContext(), "Anda tidak sedang terhubung ke internet. Tolong periksa kembali koneksi internet Anda!",Toast.LENGTH_SHORT).show();
            }
        });
        return binding.getRoot();
    }


    private void initializeRecyclerView(List<InboxModel> items) {
        mAdapter = new NotificationAdapterByDate(items, this);
        binding.rvContentNotification.setAdapter(mAdapter);
    }

    private void subscribeRefresh(){
        viewModel.getRefreshFinish().observe(getViewLifecycleOwner(), refresh->{
            if (refresh){
                viewModel.setRefreshFinish(false);
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });

        viewModel.getUnreadRefreshFinish().observe(getViewLifecycleOwner(), refresh->{
            if(refresh){
                viewModel.setUnreadRefreshFinish(false);
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });

        viewModel.getLoadMores().observe(getViewLifecycleOwner(), loadMores->{
            if (loadMores.size() != 0)
            {
                for (int i = 0; i < loadMores.size(); i++){
                    mAdapter.getList().add(loadMores.get(i));
                    mAdapter.notifyDataChanged();
                }
            }
        });

        viewModel.getLoadMore().observe(getViewLifecycleOwner(), loadMore->{
            if (loadMore){
                viewModel.setLoadMore(false);
            }
        });
    }

    private void subscribeList() {
        viewModel.getRepos().observe(getViewLifecycleOwner(), new Observer<List<InboxModel>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(List<InboxModel> inboxModels) {
                List<InboxModel> items = new ArrayList<>();
                if (inboxModels.size()>0){
                    List<String> mHeaderDate = new ArrayList<>();
                    for (int i = 0; i < inboxModels.size(); i++) {
                        if (!mHeaderDate.contains(inboxModels.get(i).getPublishTime())){
                            mHeaderDate.add(inboxModels.get(i).getPublishTime());
                        }
                    }
                    for (int i = 0; i < inboxModels.size(); i++){
                        for (int j = 0; j < mHeaderDate.size(); j++){
                            if (inboxModels.get(i).getPublishTime().equals(mHeaderDate.get(j))){
                                switch (mSection){
                                    case 1:{
                                        if (inboxModels.get(i).getType().equals("PROMO")){
                                            items.add(inboxModels.get(i));
                                        }
                                        break;
                                    }
                                    case 2:{
                                        if (inboxModels.get(i).getType().equals("INFO")){
                                            items.add(inboxModels.get(i));
                                        }
                                        break;
                                    }
                                    default:{
                                        items.add(inboxModels.get(i));
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                //inboxComparator = (o1, o2) -> o1.getPublishTime().compareTo(o2.getPublishTime());
                //Collections.sort(items, inboxComparator);
                initializeRecyclerView(items);
            }
        });
    }

    public void destyorActionMode(){
        if(mAdapter != null && mAdapter.getActionMode() != null){
            mAdapter.destroyActionMode();
        }
    }

    public void deleteInbox(int id){
        viewModel.fetchDelete(id);
    }

    public void startActivityDetailInbox(int id, String type){
        Intent intent = new Intent(getActivity(), NotificationDetailActivity.class);
        intent.putExtra("InboxID", id);
        intent.putExtra("InboxType", type);
        startActivity(intent);
    }

    private EndlessOnScrollListener scrollData() {
        return new EndlessOnScrollListener() {
            @Override
            public void onLoadMore() {
                //masukan disini methods atau action mengambil data baru
                viewModel.fetchLoadMore(mAdapter.getNextPage());
            }
        };
    }
}
