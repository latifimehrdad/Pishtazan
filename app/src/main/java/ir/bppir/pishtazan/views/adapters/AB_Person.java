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
import ir.bppir.pishtazan.databinding.AdabterPersonPanelBinding;
import ir.bppir.pishtazan.models.MD_Person;

public class AB_Person extends RecyclerView.Adapter<AB_Person.CustomHolder> {

    private List<MD_Person> personList;
    private LayoutInflater layoutInflater;
    private Context context;

    public AB_Person(List<MD_Person> personList, Context context) {
        this.personList = personList;
        this.context = context;
    }

    public AB_Person.CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        return new AB_Person.CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adabter_person_panel, parent, false));
    }

    public void onBindViewHolder(AB_Person.CustomHolder holder, int position) {
        holder.bind(personList.get(position), position);
    }

    public int getItemCount() {
        return personList.size();
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

        public void bind(MD_Person item, final int positon) {
            binding.setPerson(item);

            List<String> list = new ArrayList<>();
            list.add("تکمیل اطلاعات");
            list.add("ثبت یادآوری");
            list.add("ثبت تماس");
            MaterialSpinnerAction.setItems(list);
            binding.executePendingBindings();


        }

    }
}
