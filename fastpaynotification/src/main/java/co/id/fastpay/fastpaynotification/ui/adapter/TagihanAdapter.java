package co.id.fastpay.fastpaynotification.ui.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.id.fastpay.fastpaynotification.R;
import co.id.fastpay.fastpaynotification.databinding.ItemTagihanBinding;
import co.id.fastpay.fastpaynotification.databinding.ItemTagihanFooterBinding;
import co.id.fastpay.fastpaynotification.utils.AdditionalDataModel;
import co.id.fastpay.fastpaynotification.utils.NotificationUtils;
import co.id.fastpay.fastpaynotification.utils.InboxModel;

public class TagihanAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    InboxModel inbox;
    List<AdditionalDataModel> additionalList;

    private static final int TYPE_FOOTER = 0;
    private static final int TYPE_ITEM = 1;

    public TagihanAdapter(InboxModel inbox){
        this.inbox = inbox;
        additionalList = inbox.getAdditionalDataModel();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM){
            return new ItemViewHolder(ItemTagihanBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
        else if (viewType == TYPE_FOOTER){
            return new FooterViewHolder(ItemTagihanFooterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
        else
            return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).bind(additionalList.get(position));
        }else{
            ((FooterViewHolder)holder).bind(inbox);
        }
    }


    @Override
    public int getItemCount() {
        return inbox.getAdditionalDataModel().size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == additionalList.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        ItemTagihanBinding binding;
        public ItemViewHolder(ItemTagihanBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void bind(AdditionalDataModel item){
            binding.titleTagihan.setText(item.getGroupProduct());
            binding.bodyCustId.setText(item.getCustomerId());
            binding.bodyCustTagihan.setText(NotificationUtils.convertToRupiah(item.getNominal()));
            switch (item.getGroupProduct()){
                case "PULSA": {binding.imgTagihan.setImageDrawable(binding.getRoot().getContext().getDrawable(R.drawable.ic_ubp_pulsa));break;}
                case "GAME": {binding.imgTagihan.setImageDrawable(binding.getRoot().getContext().getDrawable(R.drawable.ic_ubp_game));break;}
                case "FINANCE": {binding.imgTagihan.setImageDrawable(binding.getRoot().getContext().getDrawable(R.drawable.ic_ubp_finance));break;}
                case "PLN": {binding.imgTagihan.setImageDrawable(binding.getRoot().getContext().getDrawable(R.drawable.ic_ubp_pln));break;}
                case "PDAM": {binding.imgTagihan.setImageDrawable(binding.getRoot().getContext().getDrawable(R.drawable.ic_ubp_pdam));break;}
                case "TELKOM": {binding.imgTagihan.setImageDrawable(binding.getRoot().getContext().getDrawable(R.drawable.ic_ubp_telkom));break;}
                case "TV": {binding.imgTagihan.setImageDrawable(binding.getRoot().getContext().getDrawable(R.drawable.ic_ubp_tv));break;}
                case "ZAKAT": {binding.imgTagihan.setImageDrawable(binding.getRoot().getContext().getDrawable(R.drawable.ic_zakat_logo));break;}
                case "BPJS": {binding.imgTagihan.setImageDrawable(binding.getRoot().getContext().getDrawable(R.drawable.ic_ubp_bpjs));break;}
            }
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder{
        ItemTagihanFooterBinding binding;
        public FooterViewHolder(ItemTagihanFooterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(InboxModel inbox){
            double total = 0;
            for(AdditionalDataModel add: additionalList){
                total += add.getNominal();
            }
            binding.bodySubtotal.setText(NotificationUtils.convertToRupiah(total));
            total+=inbox.getAdminFee();
            binding.bodyBiayaAdmin.setText(NotificationUtils.convertToRupiah(inbox.getAdminFee()));
            binding.bodyCashback.setText(NotificationUtils.convertToRupiah(inbox.getCashBack()));
            binding.bodyTotalTagihan.setText(NotificationUtils.convertToRupiah(total));
        }
    }
}
