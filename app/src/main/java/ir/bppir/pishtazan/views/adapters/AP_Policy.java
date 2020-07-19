package ir.bppir.pishtazan.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdabterContactBinding;
import ir.bppir.pishtazan.databinding.AdapterItemPolicyBinding;
import ir.bppir.pishtazan.models.MD_Contact;
import ir.bppir.pishtazan.models.MD_Policy;
import ir.bppir.pishtazan.models.MD_PolicyType;

public class AP_Policy extends RecyclerView.Adapter<AP_Policy.CustomHolder>  {

    private LayoutInflater layoutInflater;
    private List<MD_Policy> md_policies;

    public AP_Policy(List<MD_Policy> md_policies) {
        this.md_policies = md_policies;
    }


    public AP_Policy.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        return new AP_Policy.CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_policy,parent, false));
    }


    public void onBindViewHolder(AP_Policy.CustomHolder holder, int position) {
        holder.bind(md_policies.get(position), position);
    }


    public int getItemCount() {
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
            binding.executePendingBindings();
        }
    }


}
