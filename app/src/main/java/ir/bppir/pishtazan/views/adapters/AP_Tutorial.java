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
import ir.bppir.pishtazan.databinding.AdapterTutorialBinding;
import ir.bppir.pishtazan.models.MD_Education;

public class AP_Tutorial extends RecyclerView.Adapter<AP_Tutorial.customHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_Education> md_educations;
    private ClickItemTutorial clickItemTutorial;
    private String post;

    //______________________________________________________________________________________________ onCreateViewHolder
    @NonNull
    @Override
    public customHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new customHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_tutorial, parent, false));
    }
    //______________________________________________________________________________________________ onCreateViewHolder


    //______________________________________________________________________________________________ onBindViewHolder
    @Override
    public void onBindViewHolder(@NonNull customHolder holder, int position) {
        holder.bind(md_educations.get(position), position);
    }
    //______________________________________________________________________________________________ onBindViewHolder


    //______________________________________________________________________________________________ getItemCount
    @Override
    public int getItemCount() {
        if (md_educations == null)
            return 0;
        return md_educations.size();
    }
    //______________________________________________________________________________________________ getItemCount


    //______________________________________________________________________________________________ ClickItemTutorial
    public interface ClickItemTutorial {
        void clickItemTutorial(Integer Position, View view);
    }
    //______________________________________________________________________________________________ ClickItemTutorial


    //______________________________________________________________________________________________ AP_Tutorial
    public AP_Tutorial(List<MD_Education> md_educations, ClickItemTutorial clickItemTutorial, String post) {
        this.md_educations = md_educations;
        this.clickItemTutorial = clickItemTutorial;
        this.post = post;
    }
    //______________________________________________________________________________________________ AP_Tutorial


    //______________________________________________________________________________________________ customHolder
    public class customHolder extends RecyclerView.ViewHolder {

        AdapterTutorialBinding binding;

        public customHolder(AdapterTutorialBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        public void bind(MD_Education item, final int itemPosition) {
            binding.setTutorial(item);
            binding.setPost(post);
            binding.getRoot().setOnClickListener(v -> clickItemTutorial.clickItemTutorial(itemPosition, binding.getRoot()));
            binding.executePendingBindings();
        }

    }
    //______________________________________________________________________________________________ customHolder

}
