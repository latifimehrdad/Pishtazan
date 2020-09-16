package ir.bppir.pishtazan.views.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdapterItemResultDetailBinding;
import ir.bppir.pishtazan.models.MD_ExamResultDetail;

public class AP_ExamResultDetail extends RecyclerView.Adapter<AP_ExamResultDetail.customHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_ExamResultDetail> md_examResultDetails;

    //______________________________________________________________________________________________ AP_ExamResultDetail
    public AP_ExamResultDetail(List<MD_ExamResultDetail> md_examResultDetails) {
        this.md_examResultDetails = md_examResultDetails;
    }
    //______________________________________________________________________________________________ AP_ExamResultDetail


    //______________________________________________________________________________________________ onCreateViewHolder
    @NonNull
    public AP_ExamResultDetail.customHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new AP_ExamResultDetail.customHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_result_detail, parent, false));
    }
    //______________________________________________________________________________________________ onCreateViewHolder


    //______________________________________________________________________________________________ onBindViewHolder
    @Override
    public void onBindViewHolder(AP_ExamResultDetail.customHolder holder, int position) {
        holder.bind(md_examResultDetails.get(position));
    }
    //______________________________________________________________________________________________ onBindViewHolder


    //______________________________________________________________________________________________ getItemCount
    @Override
    public int getItemCount() {
        if (md_examResultDetails == null)
            return 0;
        return md_examResultDetails.size();
    }
    //______________________________________________________________________________________________ getItemCount


    //______________________________________________________________________________________________ customHolder
    public class customHolder extends RecyclerView.ViewHolder {

        AdapterItemResultDetailBinding binding;

        public customHolder(AdapterItemResultDetailBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        public void bind(MD_ExamResultDetail item) {
            binding.setDetail(item);
            binding.executePendingBindings();
        }

    }
    //______________________________________________________________________________________________ customHolder

}
