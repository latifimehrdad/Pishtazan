package ir.bppir.pishtazan.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdapterReminderBinding;
import ir.bppir.pishtazan.models.MD_Reminder;


public class AP_Reminder extends RecyclerView.Adapter<AP_Reminder.CustomHolder> {


    private LayoutInflater layoutInflater;
    private List<MD_Reminder> md_reminders;
    private clickReminderItem clickItem;


    //______________________________________________________________________________________________ AP_Reminder
    public AP_Reminder(List<MD_Reminder> md_reminders, clickReminderItem clickItem) {
        this.md_reminders = md_reminders;
        this.clickItem = clickItem;
    }
    //______________________________________________________________________________________________ AP_Reminder



    //______________________________________________________________________________________________ clickReminderItem
    public interface clickReminderItem {
        void deleteClick(Integer position, View view);
    }
    //______________________________________________________________________________________________ clickReminderItem



    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_reminder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_reminders.get(position), position);
    }

    @Override
    public int getItemCount() {
        return md_reminders.size();
    }





    //______________________________________________________________________________________________ CustomHolder
    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterReminderBinding binding;
        View viewParent;

        @BindView(R.id.imageViewDelete)
        ImageView imageViewDelete;

        public CustomHolder(AdapterReminderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            viewParent = binding.getRoot();
            ButterKnife.bind(this, viewParent);
        }

        public void bind(MD_Reminder item, final int itemPosition) {
            binding.setReminder(item);

            //imageViewDelete.setOnClickListener(v -> clickItem.deleteClick(itemPosition, viewParent));

            binding.executePendingBindings();

        }

    }
    //______________________________________________________________________________________________ CustomHolder


}
