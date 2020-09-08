package ir.bppir.pishtazan.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdapterPostBinding;
import ir.bppir.pishtazan.models.MD_EducationCategoryVms;

public class AP_Post extends RecyclerView.Adapter<AP_Post.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_EducationCategoryVms> md_educationCategoryVms;
    private clickItemTutorial clickItem;


    //______________________________________________________________________________________________ AP_Post
    public AP_Post(List<MD_EducationCategoryVms> md_educationCategoryVms, clickItemTutorial clickItem) {
        this.md_educationCategoryVms = md_educationCategoryVms;
        this.clickItem = clickItem;
    }
    //______________________________________________________________________________________________ AP_Post


    //______________________________________________________________________________________________ clickItemTutorial
    public interface clickItemTutorial {

        void clickTutorial(Integer Position, View view);
    }
    //______________________________________________________________________________________________ clickItemTutorial


    //______________________________________________________________________________________________ onCreateViewHolder
    @NonNull
    @Override
    public AP_Post.CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new AP_Post.CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_post, parent, false));
    }
    //______________________________________________________________________________________________ onCreateViewHolder


    //______________________________________________________________________________________________ onBindViewHolder
    @Override
    public void onBindViewHolder(@NonNull AP_Post.CustomHolder holder, int position) {
        holder.bind(md_educationCategoryVms.get(position), position);
    }
    //______________________________________________________________________________________________ onBindViewHolder


    //______________________________________________________________________________________________ getItemCount
    @Override
    public int getItemCount() {
        if (md_educationCategoryVms == null)
            return 0;
        return md_educationCategoryVms.size();
    }
    //______________________________________________________________________________________________ getItemCount


    //______________________________________________________________________________________________ CustomHolder
    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterPostBinding binding;

        public CustomHolder(AdapterPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        public void bind(MD_EducationCategoryVms item, final int itemPosition) {
            binding.setPost(item);
            binding.getRoot().setOnClickListener(v -> clickItem.clickTutorial(itemPosition, binding.getRoot()));
            binding.executePendingBindings();
        }

    }
    //______________________________________________________________________________________________ CustomHolder

}