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
import ir.bppir.pishtazan.databinding.AdapterTutorialMovieBinding;
import ir.bppir.pishtazan.models.MD_EducationFiles;

public class AP_Movie extends RecyclerView.Adapter<AP_Movie.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_EducationFiles> md_educationFiles;
    private ClickItemTutorialMovie clickItemTutorialMovie;

    //______________________________________________________________________________________________ AP_Movie
    public AP_Movie(List<MD_EducationFiles> md_educationFiles, ClickItemTutorialMovie clickItemTutorialMovie) {
        this.md_educationFiles = md_educationFiles;
        this.clickItemTutorialMovie = clickItemTutorialMovie;
    }
    //______________________________________________________________________________________________ AP_Movie


    //______________________________________________________________________________________________ onCreateViewHolder
    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_tutorial_movie, parent, false));
    }
    //______________________________________________________________________________________________ onCreateViewHolder


    //______________________________________________________________________________________________ onBindViewHolder
    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_educationFiles.get(position), position);
    }
    //______________________________________________________________________________________________ onBindViewHolder


    //______________________________________________________________________________________________ getItemCount
    @Override
    public int getItemCount() {
        if (md_educationFiles == null)
            return 0;

        return md_educationFiles.size();
    }
    //______________________________________________________________________________________________ getItemCount


    //______________________________________________________________________________________________ ClickItemTutorialMovie
    public interface ClickItemTutorialMovie {
        void clickItemTutorialMovie(Integer Position, View view);
    }
    //______________________________________________________________________________________________ ClickItemTutorialMovie


    //______________________________________________________________________________________________ CustomHolder
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
    //______________________________________________________________________________________________ CustomHolder

}
