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
import ir.bppir.pishtazan.databinding.AdapterItemReportBinding;
import ir.bppir.pishtazan.databinding.AdapterTutorialBinding;
import ir.bppir.pishtazan.models.MD_Education;

public class AP_Tutorial extends RecyclerView.Adapter<AP_Tutorial.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_Education> md_educations;
    private ClickItemTutorial clickItemTutorial;
    private String post;

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_tutorial, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_educations.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (md_educations == null)
            return 0;
        return md_educations.size();
    }

    public interface ClickItemTutorial {//__________________________________________________________ ClickItemTutorial
        void clickItemTutorial(Integer Position, View view);
    }//_____________________________________________________________________________________________ ClickItemTutorial


    public AP_Tutorial(List<MD_Education> md_educations, ClickItemTutorial clickItemTutorial, String post) {
        this.md_educations = md_educations;
        this.clickItemTutorial = clickItemTutorial;
        this.post = post;
    }



    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterTutorialBinding binding;

        public CustomHolder(AdapterTutorialBinding binding) {
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

}
