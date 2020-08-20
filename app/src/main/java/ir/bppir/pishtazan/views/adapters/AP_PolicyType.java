package ir.bppir.pishtazan.views.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdabterPersonPanelBinding;
import ir.bppir.pishtazan.databinding.AdapterItemPolicyBinding;
import ir.bppir.pishtazan.models.MD_Policy;

public class AP_PolicyType extends RecyclerView.Adapter<AP_PolicyType.CustomHolder> {

    private List<MD_Policy> md_policies;
    private LayoutInflater layoutInflater;

    public AP_PolicyType(List<MD_Policy> md_policies) {
        this.md_policies = md_policies;
    }

    public AP_PolicyType.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        return new AP_PolicyType.CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_policy, parent, false));
    }

    public void onBindViewHolder(AP_PolicyType.CustomHolder holder, int position) {
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

        public void bind(MD_Policy item, final int itemPosition) {
            binding.setPolicy(item);
            binding.executePendingBindings();

        }

    }
}
