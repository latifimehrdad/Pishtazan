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
import ir.bppir.pishtazan.databinding.AdapterTutorialMovieBinding;
import ir.bppir.pishtazan.models.MD_EducationFiles;
import ir.bppir.pishtazan.models.MD_Tutorial;
import ir.bppir.pishtazan.models.MD_TutorialMovie;

public class AP_Movie extends RecyclerView.Adapter<AP_Movie.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_EducationFiles> md_educationFiles;
    private ClickItemTutorialMovie clickItemTutorialMovie;

    public AP_Movie(List<MD_EducationFiles> md_educationFiles, ClickItemTutorialMovie clickItemTutorialMovie) {
        this.md_educationFiles = md_educationFiles;
        this.clickItemTutorialMovie = clickItemTutorialMovie;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_tutorial_movie, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_educationFiles.get(position), position);
    }

    @Override
    public int getItemCount() {
        return md_educationFiles.size();
    }

    public interface ClickItemTutorialMovie {//_____________________________________________________ ClickItemTutorialMovie
        void clickItemTutorialMovie(Integer Position, View view);
    }//_____________________________________________________________________________________________ ClickItemTutorialMovie



    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterTutorialMovieBinding binding;

        public CustomHolder(AdapterTutorialMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        public void bind(MD_EducationFiles item, final int itemPosition) {
            binding.setMovie(item);
            binding.getRoot().setOnClickListener(v -> clickItemTutorialMovie.clickItemTutorialMovie(itemPosition, binding.getRoot()));
            binding.executePendingBindings();
        }

    }

}
