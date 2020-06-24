package ir.bppir.pishtazan.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdabterContactBinding;
import ir.bppir.pishtazan.databinding.AdabterPersonPanelBinding;
import ir.bppir.pishtazan.models.MD_Contact;
import ir.bppir.pishtazan.models.MD_Person;

public class AB_Contact extends RecyclerView.Adapter<AB_Contact.CustomHolder> {

    private List<MD_Contact> md_contacts;
    private LayoutInflater layoutInflater;
    private Context context;

    public AB_Contact(List<MD_Contact> md_contacts, Context context) {
        this.md_contacts = md_contacts;
        this.context = context;
    }

    public AB_Contact.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        return new AB_Contact.CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adabter_contact, parent, false));
    }

    public void onBindViewHolder(AB_Contact.CustomHolder holder, int position) {
        holder.bind(md_contacts.get(position), position);
    }

    public int getItemCount() {
        return md_contacts.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder {

        AdabterContactBinding binding;

        public CustomHolder(AdabterContactBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_Contact item, final int position) {
            binding.setContact(item);
            binding.executePendingBindings();
        }

    }
}