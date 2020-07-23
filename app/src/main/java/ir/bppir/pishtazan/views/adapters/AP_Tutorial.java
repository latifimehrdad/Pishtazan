package ir.bppir.pishtazan.views.adapters;

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
import ir.bppir.pishtazan.databinding.AdapterItemReportBinding;
import ir.bppir.pishtazan.databinding.AdapterTutorialBinding;
import ir.bppir.pishtazan.models.MD_Report;
import ir.bppir.pishtazan.models.MD_Tutorial;

public class AP_Tutorial extends RecyclerView.Adapter<AP_Tutorial.CustomHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_Tutorial> md_tutorials;
    private ClickItemTutorial clickItemTutorial;

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_tutorial, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_tutorials.get(position), position);
    }

    @Override
    public int getItemCount() {
        return md_tutorials.size();
    }

    public interface ClickItemTutorial {//__________________________________________________________ ClickItemTutorial
        void clickItemTutorial(Integer Position, View view);
    }//_____________________________________________________________________________________________ ClickItemTutorial


    public AP_Tutorial(List<MD_Tutorial> md_tutorials, ClickItemTutorial clickItemTutorial) {
        this.md_tutorials = md_tutorials;
        this.clickItemTutorial = clickItemTutorial;
    }



    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterTutorialBinding binding;

        public CustomHolder(AdapterTutorialBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        public void bind(MD_Tutorial item, final int itemPosition) {
            binding.setTutorial(item);
            binding.getRoot().setOnClickListener(v -> clickItemTutorial.clickItemTutorial(itemPosition, binding.getRoot()));
            binding.executePendingBindings();
        }

    }

}
