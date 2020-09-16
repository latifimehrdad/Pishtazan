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
import ir.bppir.pishtazan.databinding.AdapterItemLearnReportBinding;
import ir.bppir.pishtazan.models.MD_Report;

public class AP_LearnReport extends RecyclerView.Adapter<AP_LearnReport.customHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_Report> md_reports;
    private Context context;
    private ClickItemDetail clickItemDetail;


    //______________________________________________________________________________________________ ClickItemPerson
    public interface ClickItemDetail {
        void clickItemDetail(Integer Position);
    }
    //______________________________________________________________________________________________ ClickItemPerson


    //______________________________________________________________________________________________ AP_LearnReport
    public AP_LearnReport(List<MD_Report> md_reports, Context context, ClickItemDetail clickItemDetail) {
        this.md_reports = md_reports;
        this.context = context;
        this.clickItemDetail = clickItemDetail;
    }
    //______________________________________________________________________________________________ AP_LearnReport


    //______________________________________________________________________________________________ onCreateViewHolder
    @NonNull
    @Override
    public customHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new customHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_learn_report, parent, false));
    }
    //______________________________________________________________________________________________ onCreateViewHolder


    //______________________________________________________________________________________________ onBindViewHolder
    @Override
    public void onBindViewHolder(@NonNull customHolder holder, int position) {
        holder.bind(md_reports.get(position), position);
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

        AdapterItemLearnReportBinding binding;

        @BindView(R.id.LinearLayoutExpandClick)
        LinearLayout LinearLayoutExpandClick;

        @BindView(R.id.ExpandableLayoutItem)
        ExpandableLayout ExpandableLayoutItem;

        @BindView(R.id.RecyclerViewDetails)
        RecyclerView RecyclerViewDetails;

        @BindView(R.id.LinearLayoutExamResultDetail)
        LinearLayout LinearLayoutExamResultDetail;

        public customHolder(AdapterItemLearnReportBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        public void bind(MD_Report item, final int itemPosition) {
            binding.setReport(item);
            AP_LearnReportDetail ap_reportDetail = new AP_LearnReportDetail(item.getLearningReports());
            RecyclerViewDetails.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
            RecyclerViewDetails.setAdapter(ap_reportDetail);
            LinearLayoutExpandClick.setOnClickListener(v -> {
                if (ExpandableLayoutItem.isExpanded())
                    ExpandableLayoutItem.collapse();
                else
                    ExpandableLayoutItem.expand();
            });
            LinearLayoutExamResultDetail.setOnClickListener(v -> clickItemDetail.clickItemDetail(itemPosition));
            binding.executePendingBindings();

        }

    }
    //______________________________________________________________________________________________ customHolder


}