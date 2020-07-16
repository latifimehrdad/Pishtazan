package ir.bppir.pishtazan.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.database.DB_Persons;
import ir.bppir.pishtazan.databinding.AdabterPersonPanelBinding;
import ir.bppir.pishtazan.databinding.AdapterItemPolicyBinding;
import ir.bppir.pishtazan.models.MD_Person;
import ir.bppir.pishtazan.models.MD_PolicyType;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.views.fragments.Panel;

public class AP_PolicyType extends RecyclerView.Adapter<AP_PolicyType.CustomHolder> {

    private List<MD_PolicyType> md_policyTypes;
    private LayoutInflater layoutInflater;

    public AP_PolicyType(List<MD_PolicyType> md_policyTypes) {
        this.md_policyTypes = md_policyTypes;
    }

    public AP_PolicyType.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        return new AP_PolicyType.CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_item_policy, parent, false));
    }

    public void onBindViewHolder(AP_PolicyType.CustomHolder holder, int position) {
        holder.bind(md_policyTypes.get(position), position);
    }

    public int getItemCount() {
        return md_policyTypes.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterItemPolicyBinding binding;

        public CustomHolder(AdapterItemPolicyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(MD_PolicyType item, final int itemPosition) {
            binding.setPolicy(item);
            binding.executePendingBindings();

        }

    }
}
