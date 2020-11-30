package ir.bppir.pishtazan.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdabterContactBinding;
import ir.bppir.pishtazan.databinding.AdapterNotificationBinding;
import ir.bppir.pishtazan.models.MD_Contact;
import ir.bppir.pishtazan.models.MD_Notifications;

public class AP_Notifications extends RecyclerView.Adapter<AP_Notifications.customHolder>{

    private List<MD_Notifications> md_notifications;
    private LayoutInflater layoutInflater;
    private clickItemNotification clickItem;

    //______________________________________________________________________________________________ AP_Notifications
    public AP_Notifications(List<MD_Notifications> md_notifications, clickItemNotification clickItem) {
        this.md_notifications = md_notifications;
        this.clickItem = clickItem;
    }
    //______________________________________________________________________________________________ AP_Notifications



    //______________________________________________________________________________________________ clickItemNotification
    public interface clickItemNotification {

        void itemClick(Integer position);
    }
    //______________________________________________________________________________________________ clickItemNotification




    //______________________________________________________________________________________________ onCreateViewHolder
    @NonNull
    @Override
    public customHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new customHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_notification, parent, false));
    }
    //______________________________________________________________________________________________ onCreateViewHolder


    //______________________________________________________________________________________________ onBindViewHolder
    @Override
    public void onBindViewHolder(@NonNull customHolder holder, int position) {
        holder.bind(md_notifications.get(position), position);
    }
    //______________________________________________________________________________________________ onBindViewHolder


    //______________________________________________________________________________________________ getItemCount
    @Override
    public int getItemCount() {
        return md_notifications.size();
    }
    //______________________________________________________________________________________________ getItemCount


    //______________________________________________________________________________________________ customHolder
    public class customHolder extends RecyclerView.ViewHolder {

        AdapterNotificationBinding binding;
        View view;

        public customHolder(AdapterNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_Notifications item, final int position) {
            binding.setNotification(item);
            view.setOnClickListener(v -> clickItem.itemClick(position));
            binding.executePendingBindings();
        }

    }
    //______________________________________________________________________________________________ customHolder

}
