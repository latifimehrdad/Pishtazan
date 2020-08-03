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
import ir.bppir.pishtazan.databinding.AdapterTutorialBinding;
import ir.bppir.pishtazan.models.MD_Post;
import ir.bppir.pishtazan.models.MD_Tutorial;

public class AP_Post extends RecyclerView.Adapter<AP_Post.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_Post> md_posts;
    private AP_Post.ClickItemTutorial clickItemTutorial;

    @NonNull
    @Override
    public AP_Post.CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new AP_Post.CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_post, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AP_Post.CustomHolder holder, int position) {
        holder.bind(md_posts.get(position), position);
    }

    @Override
    public int getItemCount() {
        return md_posts.size();
    }

    public interface ClickItemTutorial {//__________________________________________________________ ClickItemTutorial
        void clickItemTutorial(Integer Position, View view);
    }//_____________________________________________________________________________________________ ClickItemTutorial


    public AP_Post(List<MD_Post> md_posts, AP_Post.ClickItemTutorial clickItemTutorial) {
        this.md_posts = md_posts;
        this.clickItemTutorial = clickItemTutorial;
    }



    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterPostBinding binding;

        public CustomHolder(AdapterPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        public void bind(MD_Post item, final int itemPosition) {
            binding.setPost(item);
            binding.getRoot().setOnClickListener(v -> clickItemTutorial.clickItemTutorial(itemPosition, binding.getRoot()));
            binding.executePendingBindings();
        }

    }

}