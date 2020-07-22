package ir.bppir.pishtazan.views.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdapterItemReportBinding;
import ir.bppir.pishtazan.databinding.AdapterItemReportDetailBinding;
import ir.bppir.pishtazan.models.MD_Report;
import ir.bppir.pishtazan.models.MD_ReportDetail;

public class AP_ReportDetail extends RecyclerView.Adapter<AP_ReportDetail.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_ReportDetail> details;

    public AP_ReportDetail(List<MD_ReportDetail> details) {
        this.details = details;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_report_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(details.get(position), position);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }




    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemReportDetailBinding binding;

        public CustomHolder(AdapterItemReportDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MD_ReportDetail item, final int itemPosition) {
            binding.setDetail(item);
            binding.executePendingBindings();
        }
    }

}
