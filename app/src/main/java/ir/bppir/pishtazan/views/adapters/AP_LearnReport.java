package ir.bppir.pishtazan.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
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
import ir.bppir.pishtazan.databinding.AdapterItemReportBinding;
import ir.bppir.pishtazan.models.MD_Report;

public class AP_LearnReport extends RecyclerView.Adapter<AP_LearnReport.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_Report> md_reports;
    private Context context;
    private ClickItemPerson clickItemPerson;


    public interface ClickItemPerson {//____________________________________________________________ ClickItemPerson
        void clickItemPerson(Integer Position);
    }//_____________________________________________________________________________________________ ClickItemPerson


    public AP_LearnReport(List<MD_Report> md_reports, Context context, ClickItemPerson clickItemPerson) {
        this.md_reports = md_reports;
        this.context = context;
        this.clickItemPerson = clickItemPerson;
    }

    @NonNull
    @Override
    public AP_LearnReport.CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new AP_LearnReport.CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_learn_report, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AP_LearnReport.CustomHolder holder, int position) {
        holder.bind(md_reports.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (md_reports == null)
            return 0;

        return md_reports.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemLearnReportBinding binding;

        @BindView(R.id.LinearLayoutExpandClick)
        LinearLayout LinearLayoutExpandClick;

        @BindView(R.id.ExpandableLayoutItem)
        ExpandableLayout ExpandableLayoutItem;

        @BindView(R.id.RecyclerViewDetails)
        RecyclerView RecyclerViewDetails;

        @BindView(R.id.LinearLayoutExamResultDetail)
        LinearLayout LinearLayoutExamResultDetail;

        public CustomHolder(AdapterItemLearnReportBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        public void bind(MD_Report item, final int itemPosition) {
            binding.setReport(item);
            AP_LearnReportDetail ap_reportDetail = new AP_LearnReportDetail(item.getLearningReports());
            RecyclerViewDetails.setLayoutManager(new LinearLayoutManager(context,RecyclerView.VERTICAL,false));
            RecyclerViewDetails.setAdapter(ap_reportDetail);
            LinearLayoutExpandClick.setOnClickListener(v -> {
                if (ExpandableLayoutItem.isExpanded())
                    ExpandableLayoutItem.collapse();
                else
                    ExpandableLayoutItem.expand();
            });
            LinearLayoutExamResultDetail.setOnClickListener(v -> clickItemPerson.clickItemPerson(itemPosition));
            binding.executePendingBindings();

        }

    }
}