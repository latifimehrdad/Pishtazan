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
import io.realm.RealmResults;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.database.DB_Persons;
import ir.bppir.pishtazan.databinding.AdabterPersonPanelBinding;
import ir.bppir.pishtazan.utility.StaticValues;
import ir.bppir.pishtazan.views.fragments.Panel;

public class AP_Person extends RecyclerView.Adapter<AP_Person.CustomHolder> {

    private RealmResults<DB_Persons> db_persons;
    private LayoutInflater layoutInflater;
    private Context context;
    private Panel panel;

    public AP_Person(RealmResults<DB_Persons> db_persons, Context context, Panel panel) {
        this.db_persons = db_persons;
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
        holder.bind(db_persons.get(position), position);
    }

    public int getItemCount() {
        return db_persons.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder {

        AdabterPersonPanelBinding binding;

        @BindView(R.id.MaterialSpinnerAction)
        MaterialSpinner MaterialSpinnerAction;

        public CustomHolder(AdabterPersonPanelBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View view = binding.getRoot();
            ButterKnife.bind(this, view);
        }

        public void bind(DB_Persons item, final int itemPosition) {
            binding.setPerson(item);

            if (item.getPersonType() == StaticValues.ML_Maybe) {
                List<String> list = new ArrayList<>();
                list.add(context.getResources().getString(R.string.ActionPrompt));// position 0
                list.add(context.getResources().getString(R.string.CompleteInformation));//position 1
                list.add(context.getResources().getString(R.string.SetCallReminder));//position 2
                list.add(context.getResources().getString(R.string.SetMeetingReminder));//position 3
                list.add(context.getResources().getString(R.string.MoveToPossible));//position 4
                list.add(context.getResources().getString(R.string.DeleteFromList));//position 5

                MaterialSpinnerAction.setItems(list);
            } else if (item.getPersonType() == StaticValues.ML_Possible) {
                List<String> list = new ArrayList<>();
                list.add(context.getResources().getString(R.string.ActionPrompt));// position 0
                list.add(context.getResources().getString(R.string.CompleteInformation));//position 1
                list.add(context.getResources().getString(R.string.SetCallReminder));//position 2
                list.add(context.getResources().getString(R.string.SetMeetingReminder));//position 3
                list.add(context.getResources().getString(R.string.MoveToCertain));//position 4
                list.add(context.getResources().getString(R.string.DeleteFromList));//position 5
                MaterialSpinnerAction.setItems(list);
            }

            MaterialSpinnerAction.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                    if (position != 0) {
                        panel.ChooseActionFromList(itemPosition, position);
                        MaterialSpinnerAction.setSelectedIndex(0);
                    }
                }
            });

            binding.executePendingBindings();

        }

    }
}
