package ir.bppir.pishtazan.views.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdapterItemReportDetailBinding;
import ir.bppir.pishtazan.models.MD_ReportDetail;

public class AP_ReportDetail extends RecyclerView.Adapter<AP_ReportDetail.customHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_ReportDetail> details;

    //______________________________________________________________________________________________ AP_ReportDetail
    public AP_ReportDetail(List<MD_ReportDetail> details) {
        this.details = details;
    }
    //______________________________________________________________________________________________ AP_ReportDetail


    //______________________________________________________________________________________________ onCreateViewHolder
    @NonNull
    @Override
    public customHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new customHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_report_detail, parent, false));
    }
    //______________________________________________________________________________________________ onCreateViewHolder


    //______________________________________________________________________________________________ onCreateViewHolder
    @Override
    public void onBindViewHolder(@NonNull customHolder holder, int position) {
        holder.bind(details.get(position));
    }
    //______________________________________________________________________________________________ onCreateViewHolder


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

        AdapterItemReportDetailBinding binding;

        public customHolder(AdapterItemReportDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MD_ReportDetail item) {
            binding.setDetail(item);
            binding.executePendingBindings();
        }
    }
    //______________________________________________________________________________________________ customHolder

}
