package co.id.fastpay.fastpaynotification.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import co.id.fastpay.fastpaynotification.R;
import co.id.fastpay.fastpaynotification.ui.NotificationContentFragment;
import co.id.fastpay.fastpaynotification.utils.NotificationUtils;
import co.id.fastpay.fastpaynotification.utils.InboxModel;

public class NotificationAdapterByDate extends BaseNotificationAdapter {
    Fragment mfragment;
    NotificationViewHolder holder;
    ActionMode action;
    private boolean multiSelect = false;
    private ArrayList<InboxModel> selectedItems = new ArrayList<>();
    private int page = 0;

    public NotificationAdapterByDate(List<InboxModel> itemList, Fragment fragment) {
        super(itemList);
        mfragment = fragment;
    }

    @Override
    public boolean onPlaceSubheaderBetweenItems(int position) {
        final String inboxDate = inboxList.get(position).getPublishTime();
        final String nextInboxDate = inboxList.get(position + 1).getPublishTime();

        return !inboxDate.equals(nextInboxDate);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindItemViewHolder(final NotificationViewHolder holder, final int position) {
        this.holder = holder;
        final InboxModel inbox = inboxList.get(position);
        holder.titleInbox.setText(inbox.getTitle());
        holder.subtitleInbox.setText(inbox.getContent());

        if ("PROMO".equals(inbox.getType())) {
            holder.icon.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_avatar_promo));
        } else if ("SYSTEM".equals(inbox.getType())) {
            holder.icon.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_avatar_sistem));
        } else if ("NOTIFIKASI".equals(inbox.getType())) {
            holder.icon.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_avatar_notif));
        } else if ("NEWS".equals(inbox.getType())) {
            holder.icon.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_avatar_news));
        } else if ("TRANSAKSI".equals(inbox.getType())) {
            holder.icon.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_avatar_transaksi));
        } else if ("INFO ".equals(inbox.getType())) {
            holder.icon.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.ic_avatar_comment));
        }

        if (inbox.getIsRead() == 1){
            holder.readIcon.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.rippleParent.setRippleBackground(holder.itemView.getContext().getColor(R.color.material_color_grey_300));
            }
        }

        if (selectedItems.contains(inbox)) {
            holder.selected.setVisibility(View.VISIBLE);
        } else {
            holder.selected.setVisibility(View.GONE);
        }

        holder.parent.setOnLongClickListener(view -> {
            action = ((AppCompatActivity)view.getContext()).startSupportActionMode(actionModeCallbacks);
            selectItem(holder, inbox);
            return true;
        });

        holder.parent.setOnClickListener(view -> {
            if (multiSelect){
                selectItem(holder, inbox);
            }
            else{
                ((NotificationContentFragment)mfragment).startActivityDetailInbox(inbox.getId(), inbox.getType());
            }
        });
    }

    @Override
    public void onBindSubheaderViewHolder(SubheaderHolder subheaderHolder, int nextItemPosition) {
        super.onBindSubheaderViewHolder(subheaderHolder, nextItemPosition);
        //final Context context = subheaderHolder.itemView.getContext();
        final InboxModel nextInbox = inboxList.get(nextItemPosition);
        //final int sectionSize = getSectionSize(getSectionIndex(subheaderHolder.getAdapterPosition()));
        final String date = nextInbox.getPublishTime().substring(0, 10);
        final String dateFormatted = NotificationUtils.dateConverter(date);
        subheaderHolder.mTitle.setText(dateFormatted);
    }

    private ActionMode.Callback actionModeCallbacks = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            multiSelect = true;
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_context, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.action_delete){
                for (InboxModel inbox : selectedItems) {
                    ((NotificationContentFragment)mfragment).deleteInbox(inbox.getId());
                }
                mode.finish();
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            multiSelect = false;
            selectedItems.clear();
            notifyDataSetChanged();
        }
    };

    private void selectItem(NotificationViewHolder holder, InboxModel item) {
        if (multiSelect) {
            if (selectedItems.contains(item)) {
                selectedItems.remove(item);
                holder.selected.setVisibility(View.GONE);
            } else {
                selectedItems.add(item);
                holder.selected.setVisibility(View.VISIBLE);
            }
            if (selectedItems.size() == 0){
                action.finish();
            }else{
                action.setTitle(selectedItems.size() + " Selected");
                action.invalidate();
            }
        }
    }

    public int getNextPage(){
        page = page + NotificationUtils.NOTIF_OFFSET;
        return page;
    }

    public void destroyActionMode(){
        action.finish();
    }

    public ActionMode getActionMode(){
        return action;
    }
}
