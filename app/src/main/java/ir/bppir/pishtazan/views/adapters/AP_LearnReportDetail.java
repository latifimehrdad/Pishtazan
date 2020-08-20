package ir.bppir.pishtazan.views.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdapterItemLearnReportDetailBinding;
import ir.bppir.pishtazan.databinding.AdapterItemReportDetailBinding;
import ir.bppir.pishtazan.models.MD_LearnReport;

public class AP_LearnReportDetail extends RecyclerView.Adapter<AP_LearnReportDetail.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_LearnReport> details;

    public AP_LearnReportDetail(List<MD_LearnReport> details) {
        this.details = details;
    }

    @NonNull
    @Override
    public AP_LearnReportDetail.CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new AP_LearnReportDetail.CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_learn_report_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AP_LearnReportDetail.CustomHolder holder, int position) {
        holder.bind(details.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (details == null)
            return 0;

        return details.size();
    }




    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemLearnReportDetailBinding binding;

        public CustomHolder(AdapterItemLearnReportDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MD_LearnReport item, final int itemPosition) {
            binding.setDetail(item);
            binding.executePendingBindings();
        }
    }

}