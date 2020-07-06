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
import ir.bppir.pishtazan.models.MD_Person;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.views.fragments.Panel;

public class AP_Person extends RecyclerView.Adapter<AP_Person.CustomHolder> {

    private List<MD_Person> md_personList;
    private LayoutInflater layoutInflater;
    private Context context;
    private Panel panel;

    public AP_Person(List<MD_Person> md_personList, Context context, Panel panel) {
        this.md_personList = md_personList;
        this.context = context;
        this.panel = panel;
    }

    public AP_Person.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        return new AP_Person.CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adabter_person_panel, parent, false));
    }

    public void onBindViewHolder(AP_Person.CustomHolder holder, int position) {
        holder.bind(md_personList.get(position), position);
    }

    public int getItemCount() {
        return md_personList.size();
    }


    public class CustomHolder extends RecyclerView.ViewHolder {

        AdabterPersonPanelBinding binding;
        View viewParent;

        @BindView(R.id.TextViewAction)
        TextView TextViewAction;

        @BindView(R.id.ImageViewDelete)
        ImageView ImageViewDelete;

        @BindView(R.id.LinearLayoutAction)
        LinearLayout LinearLayoutAction;

        public CustomHolder(AdabterPersonPanelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            viewParent = binding.getRoot();
            ButterKnife.bind(this, viewParent);
        }

        public void bind(MD_Person item, final int itemPosition) {
            binding.setPerson(item);

            if (panel.PersonType == StaticValues.ML_Maybe) {
                TextViewAction.setText(context.getResources().getString(R.string.MoveToPossible));

                LinearLayoutAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        panel.AdapterMoveToPossible(itemPosition);
                    }
                });
            } else if (panel.PersonType == StaticValues.ML_Maybe) {

                TextViewAction.setText(context.getResources().getString(R.string.ChooseAction));

                LinearLayoutAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        panel.ChooseActionFromList(itemPosition);
                    }
                });
            }

            ImageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    panel.ShowDeleteQuestion(itemPosition, viewParent);
                }
            });

            binding.executePendingBindings();

        }

    }
}
