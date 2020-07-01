package ir.bppir.pishtazan.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdabterContactBinding;
import ir.bppir.pishtazan.models.MD_Contact;
import ir.bppir.pishtazan.views.fragments.AddPerson;

public class AP_Contact extends RecyclerView.Adapter<AP_Contact.CustomHolder> {

    private List<MD_Contact> md_contacts;
    private LayoutInflater layoutInflater;
    private Context context;
    private AddPerson addPerson;

    public AP_Contact(List<MD_Contact> md_contacts, Context context, AddPerson addPerson) {
        this.md_contacts = md_contacts;
        this.context = context;
        this.addPerson = addPerson;
    }

    public AP_Contact.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        return new AP_Contact.CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adabter_contact, parent, false));
    }

    public void onBindViewHolder(AP_Contact.CustomHolder holder, int position) {
        holder.bind(md_contacts.get(position), position);
    }

    public int getItemCount() {
        return md_contacts.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder {

        AdabterContactBinding binding;
        View view;

        public CustomHolder(AdabterContactBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_Contact item, final int position) {
            binding.setContact(item);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addPerson.ClickContact(position);
                }
            });
            binding.executePendingBindings();
        }

    }
}