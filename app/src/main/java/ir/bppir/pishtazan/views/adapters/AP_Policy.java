package ir.bppir.pishtazan.views.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdapterItemPolicyBinding;
import ir.bppir.pishtazan.models.MD_Policy;

public class AP_Policy extends RecyclerView.Adapter<AP_Policy.customHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_Policy> md_policies;
    private ClickItemPolicy clickItemPolicy;


    //______________________________________________________________________________________________ AP_Policy
    public AP_Policy(List<MD_Policy> md_policies, ClickItemPolicy clickItemPolicy) {
        this.md_policies = md_policies;
        this.clickItemPolicy = clickItemPolicy;
    }
    //______________________________________________________________________________________________ AP_Policy


    //______________________________________________________________________________________________ ClickItemPolicy
    public interface ClickItemPolicy {
        void clickItemPolicy(Integer Position);
    }
    //______________________________________________________________________________________________ ClickItemPolicy


    //______________________________________________________________________________________________ onCreateViewHolder
    @NotNull
    public customHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        return new customHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_policy, parent, false));
    }
    //______________________________________________________________________________________________ onCreateViewHolder


    //______________________________________________________________________________________________ onBindViewHolder
    public void onBindViewHolder(customHolder holder, int position) {
        holder.bind(md_policies.get(position), position);
    }
    //______________________________________________________________________________________________ onBindViewHolder


    //______________________________________________________________________________________________ getItemCount
    public int getItemCount() {
        if (md_policies == null)
            return 0;
        return md_policies.size();
    }
    //______________________________________________________________________________________________ getItemCount


    //______________________________________________________________________________________________ customHolder
    public class customHolder extends RecyclerView.ViewHolder {
        AdapterItemPolicyBinding binding;

        public customHolder(AdapterItemPolicyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MD_Policy item, final int position) {
            binding.setPolicy(item);
            binding.getRoot().setOnClickListener(v -> clickItemPolicy.clickItemPolicy(position));
            binding.executePendingBindings();
        }
    }
    //______________________________________________________________________________________________ customHolder

}
