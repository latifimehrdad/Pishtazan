package ir.bppir.pishtazan.views.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdapterItemLearnReportDetailBinding;
import ir.bppir.pishtazan.models.MD_LearnReport;

public class AP_LearnReportDetail extends RecyclerView.Adapter<AP_LearnReportDetail.customHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_LearnReport> details;

    //______________________________________________________________________________________________ AP_LearnReportDetail
    public AP_LearnReportDetail(List<MD_LearnReport> details) {
        this.details = details;
    }
    //______________________________________________________________________________________________ AP_LearnReportDetail


    //______________________________________________________________________________________________ onCreateViewHolder
    @NonNull
    @Override
    public customHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new customHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_learn_report_detail, parent, false));
    }
    //______________________________________________________________________________________________ onCreateViewHolder


    //______________________________________________________________________________________________ onBindViewHolder
    @Override
    public void onBindViewHolder(@NonNull customHolder holder, int position) {
        holder.bind(details.get(position));
    }
    //______________________________________________________________________________________________ onBindViewHolder


    //______________________________________________________________________________________________ getItemCount
    @Override
    public int getItemCount() {
        if (details == null)
            return 0;

        return details.size();
    }
    //______________________________________________________________________________________________ getItemCount


    //______________________________________________________________________________________________ customHolder
    public class customHolder extends RecyclerView.ViewHolder {

        AdapterItemLearnReportDetailBinding binding;

        public customHolder(AdapterItemLearnReportDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MD_LearnReport item) {
            binding.setDetail(item);
            binding.executePendingBindings();
        }
    }
    //______________________________________________________________________________________________ customHolder

}