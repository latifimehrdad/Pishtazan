package ir.bppir.pishtazan.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdapterItemReportBinding;
import ir.bppir.pishtazan.models.MD_Report;

public class AP_Report extends RecyclerView.Adapter<AP_Report.customHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_Report> md_reports;
    private Context context;

    //______________________________________________________________________________________________ AP_Report
    public AP_Report(List<MD_Report> md_reports, Context context) {
        this.md_reports = md_reports;
        this.context = context;
    }
    //______________________________________________________________________________________________ AP_Report


    //______________________________________________________________________________________________ onCreateViewHolder
    @NonNull
    @Override
    public customHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new customHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_report, parent, false));
    }
    //______________________________________________________________________________________________ onCreateViewHolder


    //______________________________________________________________________________________________ onBindViewHolder
    @Override
    public void onBindViewHolder(@NonNull customHolder holder, int position) {
        holder.bind(md_reports.get(position));
    }
    //______________________________________________________________________________________________ onBindViewHolder


    //______________________________________________________________________________________________ getItemCount
    @Override
    public int getItemCount() {
        if (md_reports == null)
            return 0;
        return md_reports.size();
    }
    //______________________________________________________________________________________________ getItemCount


    //______________________________________________________________________________________________ customHolder
    public class customHolder extends RecyclerView.ViewHolder {

        AdapterItemReportBinding binding;

        @BindView(R.id.LinearLayoutExpandClick)
        LinearLayout LinearLayoutExpandClick;

        @BindView(R.id.ExpandableLayoutItem)
        ExpandableLayout ExpandableLayoutItem;

        @BindView(R.id.RecyclerViewDetails)
        RecyclerView RecyclerViewDetails;


        public customHolder(AdapterItemReportBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        public void bind(MD_Report item) {
            binding.setReport(item);
            AP_ReportDetail ap_reportDetail = new AP_ReportDetail(item.getReports());
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
    //______________________________________________________________________________________________ customHolder

}
