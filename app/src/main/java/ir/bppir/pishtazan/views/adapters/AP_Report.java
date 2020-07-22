package ir.bppir.pishtazan.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdabterPersonPanelBinding;
import ir.bppir.pishtazan.databinding.AdapterItemReportBinding;
import ir.bppir.pishtazan.models.MD_Person;
import ir.bppir.pishtazan.models.MD_Report;
import ir.bppir.pishtazan.utility.StaticValues;

public class AP_Report extends RecyclerView.Adapter<AP_Report.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_Report> md_reports;
    private Context context;


    public AP_Report(List<MD_Report> md_reports, Context context) {
        this.md_reports = md_reports;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_report, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_reports.get(position), position);
    }

    @Override
    public int getItemCount() {
        return md_reports.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemReportBinding binding;

        @BindView(R.id.LinearLayoutExpandClick)
        LinearLayout LinearLayoutExpandClick;

        @BindView(R.id.ExpandableLayoutItem)
        ExpandableLayout ExpandableLayoutItem;

        @BindView(R.id.RecyclerViewDetails)
        RecyclerView RecyclerViewDetails;


        public CustomHolder(AdapterItemReportBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        public void bind(MD_Report item, final int itemPosition) {
            binding.setReport(item);
            AP_ReportDetail ap_reportDetail = new AP_ReportDetail(item.getDetails());
            RecyclerViewDetails.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
            RecyclerViewDetails.setAdapter(ap_reportDetail);
            LinearLayoutExpandClick.setOnClickListener(v -> {
                if (ExpandableLayoutItem.isExpanded())
                    ExpandableLayoutItem.collapse();
                else
                    ExpandableLayoutItem.expand();
            });
            binding.executePendingBindings();

        }

    }
}
