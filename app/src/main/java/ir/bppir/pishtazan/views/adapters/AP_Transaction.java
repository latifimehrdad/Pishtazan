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
import ir.bppir.pishtazan.databinding.AdabterContactBinding;
import ir.bppir.pishtazan.databinding.AdapterTransactionBinding;
import ir.bppir.pishtazan.models.MD_Contact;
import ir.bppir.pishtazan.models.MD_Transaction;

public class AP_Transaction extends RecyclerView.Adapter<AP_Transaction.customHolder> {

    private LayoutInflater layoutInflater;
    private List<MD_Transaction> md_transactions;

    //______________________________________________________________________________________________ AP_Transaction
    public AP_Transaction(List<MD_Transaction> md_transactions) {
        this.md_transactions = md_transactions;
    }
    //______________________________________________________________________________________________ AP_Transaction




    @NonNull
    @Override
    public customHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        return new customHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_transaction, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull customHolder holder, int position) {
        holder.bind(md_transactions.get(position), position);
    }

    @Override
    public int getItemCount() {
        return md_transactions.size();
    }




    //______________________________________________________________________________________________ customHolder
    public class customHolder extends RecyclerView.ViewHolder {

        AdapterTransactionBinding binding;
        View view;

        public customHolder(AdapterTransactionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_Transaction item, final int position) {
            binding.setTransaction(item);
            binding.executePendingBindings();
        }

    }
    //______________________________________________________________________________________________ customHolder



}
