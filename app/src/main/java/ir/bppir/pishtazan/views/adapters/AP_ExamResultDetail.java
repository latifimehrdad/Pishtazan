package ir.bppir.pishtazan.views.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdapterItemReportDetailBinding;
import ir.bppir.pishtazan.databinding.AdapterItemResultDetailBinding;
import ir.bppir.pishtazan.databinding.AdapterPostBinding;
import ir.bppir.pishtazan.models.MD_EducationCategoryVms;
import ir.bppir.pishtazan.models.MD_ExamResultDetail;

public class AP_ExamResultDetail extends RecyclerView.Adapter<AP_ExamResultDetail.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_ExamResultDetail> md_examResultDetails;

    public AP_ExamResultDetail(List<MD_ExamResultDetail> md_examResultDetails) {
        this.md_examResultDetails = md_examResultDetails;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_result_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_examResultDetails.get(position), position);
    }

    @Override
    public int getItemCount() {
        return md_examResultDetails.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemResultDetailBinding binding;

        public CustomHolder(AdapterItemResultDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        public void bind(MD_ExamResultDetail item, final int itemPosition) {
            binding.setDetail(item);
            binding.executePendingBindings();
        }

    }

}
