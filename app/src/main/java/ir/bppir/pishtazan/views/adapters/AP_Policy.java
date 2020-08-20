package ir.bppir.pishtazan.views.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdabterContactBinding;
import ir.bppir.pishtazan.databinding.AdapterItemPolicyBinding;
import ir.bppir.pishtazan.models.MD_Policy;

public class AP_Policy extends RecyclerView.Adapter<AP_Policy.CustomHolder>  {

    private LayoutInflater layoutInflater;
    private List<MD_Policy> md_policies;
    private ClickItemPolicy clickItemPolicy;

    public AP_Policy(List<MD_Policy> md_policies, ClickItemPolicy clickItemPolicy) {
        this.md_policies = md_policies;
        this.clickItemPolicy = clickItemPolicy;
    }



    public interface ClickItemPolicy {//____________________________________________________________ ClickItemPolicy
        void clickItemPolicy(Integer Position);
    }//_____________________________________________________________________________________________ ClickItemPolicy



    public AP_Policy.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        return new AP_Policy.CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_policy,parent, false));
    }


    public void onBindViewHolder(AP_Policy.CustomHolder holder, int position) {
        holder.bind(md_policies.get(position), position);
    }


    public int getItemCount() {
        if (md_policies == null)
            return 0;

        return md_policies.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {
        AdapterItemPolicyBinding binding;

        public CustomHolder(AdapterItemPolicyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MD_Policy item, final int position) {
            binding.setPolicy(item);
            binding.getRoot().setOnClickListener(v -> {clickItemPolicy.clickItemPolicy(position);});
            binding.executePendingBindings();
        }
    }


}
