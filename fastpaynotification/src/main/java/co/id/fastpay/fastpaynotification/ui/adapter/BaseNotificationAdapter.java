package co.id.fastpay.fastpaynotification.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.zhukic.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.util.List;

import co.id.fastpay.fastpaynotification.R;
import co.id.fastpay.fastpaynotification.utils.InboxModel;

public abstract class BaseNotificationAdapter extends SectionedRecyclerViewAdapter<BaseNotificationAdapter.SubheaderHolder, BaseNotificationAdapter.NotificationViewHolder> {

    List<InboxModel> inboxList;
    protected OnItemClick itemClick;

    public void setItemClick(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    static class SubheaderHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        ImageView mArrow;

        SubheaderHolder(View itemView) {
            super(itemView);
            this.mTitle = (TextView) itemView.findViewById(R.id.subheaderText);
            this.mArrow = (ImageView) itemView.findViewById(R.id.arrow);
        }

    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView titleInbox;
        TextView subtitleInbox;
        ImageView icon;
        ImageView readIcon;
        ImageView selected;
        View parent;
        View frontView;
        View divider;
        MaterialRippleLayout rippleParent;

        NotificationViewHolder(View itemView) {
            super(itemView);
            this.titleInbox = (TextView) itemView.findViewById(R.id.title);
            this.subtitleInbox = (TextView) itemView.findViewById(R.id.subtitle);
            this.icon = (ImageView) itemView.findViewById(R.id.left_image);
            this.selected = (ImageView) itemView.findViewById(R.id.left_selected);
            this.readIcon = (ImageView) itemView.findViewById(R.id.left_image_unread);
            this.rippleParent = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_parent);
            this.parent = (View) itemView.findViewById(R.id.parent);
            this.frontView = (View) itemView.findViewById(R.id.front_view);
            this.divider = (View) itemView.findViewById(R.id.divider);
        }
    }

    BaseNotificationAdapter(List<InboxModel> itemList) {
        super();
        this.inboxList = itemList;
    }

    @Override
    public NotificationViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inbox, parent, false));
    }

    @Override
    public SubheaderHolder onCreateSubheaderViewHolder(ViewGroup parent, int viewType) {
        return new SubheaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false));
    }

    @Override
    @CallSuper
    public void onBindSubheaderViewHolder(SubheaderHolder subheaderHolder, int nextItemPosition) {

        boolean isSectionExpanded = isSectionExpanded(getSectionIndex(subheaderHolder.getAdapterPosition()));

        /*
        if (isSectionExpanded) {
            subheaderHolder.mArrow.setImageDrawable(ContextCompat.getDrawable(subheaderHolder.itemView.getContext(), R.drawable.ic_arrow_up_black_24dp));
        } else {
            subheaderHolder.mArrow.setImageDrawable(ContextCompat.getDrawable(subheaderHolder.itemView.getContext(), R.drawable.ic_arrow_down_black_24dp));
        }*/
    }

    @Override
    public int getItemSize() {
        return inboxList.size();
    }

    public interface OnItemClick {

        void onItemClick(View view, InboxModel inbox, int position);

        void onLongPress(View view, InboxModel inbox, int position);
    }

    public void submitList(List<InboxModel> list){
        inboxList = list;
        notifyDataSetChanged();
    }

    public List<InboxModel> getList(){
        return inboxList;
    }
}