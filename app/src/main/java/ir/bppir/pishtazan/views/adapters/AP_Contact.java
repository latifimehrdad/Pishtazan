package ir.bppir.pishtazan.views.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdabterContactBinding;
import ir.bppir.pishtazan.models.MD_Contact;
import ir.bppir.pishtazan.views.fragments.AddPerson;

public class AP_Contact extends RecyclerView.Adapter<AP_Contact.customHolder> {

    private List<MD_Contact> md_contacts;
    private LayoutInflater layoutInflater;
    private AddPerson addPerson;


    //______________________________________________________________________________________________ AP_Contact
    public AP_Contact(List<MD_Contact> md_contacts, AddPerson addPerson) {
        this.md_contacts = md_contacts;
        this.addPerson = addPerson;
    }
    //______________________________________________________________________________________________ AP_Contact


    //______________________________________________________________________________________________ onCreateViewHolder
    @NotNull
    public customHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        return new customHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adabter_contact, parent, false));
    }
    //______________________________________________________________________________________________ onCreateViewHolder


    //______________________________________________________________________________________________ onBindViewHolder
    public void onBindViewHolder(customHolder holder, int position) {
        holder.bind(md_contacts.get(position), position);
    }
    //______________________________________________________________________________________________ onBindViewHolder


    //______________________________________________________________________________________________ getItemCount
    public int getItemCount() {
        if (md_contacts == null)
            return 0;
        return md_contacts.size();
    }
    //______________________________________________________________________________________________ getItemCount


    //______________________________________________________________________________________________ customHolder
    public class customHolder extends RecyclerView.ViewHolder {

        AdabterContactBinding binding;
        View view;

        public customHolder(AdabterContactBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(MD_Contact item, final int position) {
            binding.setContact(item);
            view.setOnClickListener(view -> addPerson.clickContact(position));
            binding.executePendingBindings();
        }

    }
    //______________________________________________________________________________________________ customHolder



}