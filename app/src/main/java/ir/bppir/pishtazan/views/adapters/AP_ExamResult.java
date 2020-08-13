package ir.bppir.pishtazan.views.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdapterItemExamReportBinding;
import ir.bppir.pishtazan.databinding.AdapterTutorialMovieBinding;
import ir.bppir.pishtazan.models.MD_EducationFiles;
import ir.bppir.pishtazan.models.MD_ExamResult;

public class AP_ExamResult extends RecyclerView.Adapter<AP_ExamResult.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_ExamResult> md_examResults;

    public AP_ExamResult(List<MD_ExamResult> md_examResults) {
        this.md_examResults = md_examResults;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_exam_report, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_examResults.get(position), position);
    }

    @Override
    public int getItemCount() {
        return md_examResults.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemExamReportBinding binding;

        public CustomHolder(AdapterItemExamReportBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        public void bind(MD_ExamResult item, final int itemPosition) {
            binding.setExamResult(item);
            binding.executePendingBindings();
        }

    }

}
