package ir.bppir.pishtazan.views.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.AdapterQuestionBinding;
import ir.bppir.pishtazan.models.MD_Question;


public class AP_Question extends RecyclerView.Adapter<AP_Question.CustomHolder> {


    private LayoutInflater layoutInflater;
    private MD_Question md_question;
    private clickItemAnswerQuestion clickItem;


    //______________________________________________________________________________________________ AP_Question
    public AP_Question(MD_Question md_question, clickItemAnswerQuestion clickItem) {
        this.md_question = md_question;
        this.clickItem = clickItem;
    }
    //______________________________________________________________________________________________ AP_Question


    //______________________________________________________________________________________________ clickItemAnswerQuestion
    public interface clickItemAnswerQuestion {

        void clickItemAnswer(Integer Answer);
    }
    //______________________________________________________________________________________________ clickItemAnswerQuestion


    //______________________________________________________________________________________________ onCreateViewHolder
    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        return new CustomHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_question, parent, false));
    }
    //______________________________________________________________________________________________ onCreateViewHolder


    //______________________________________________________________________________________________ onBindViewHolder
    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        holder.bind(md_question);
    }
    //______________________________________________________________________________________________ onBindViewHolder


    //______________________________________________________________________________________________ getItemCount
    @Override
    public int getItemCount() {
        return 1;
    }
    //______________________________________________________________________________________________ getItemCount


    //______________________________________________________________________________________________ CustomHolder
    public class CustomHolder extends RecyclerView.ViewHolder {

        AdapterQuestionBinding binding;

        @BindView(R.id.radioGroupAnswer)
        RadioGroup radioGroupAnswer;

        @BindView(R.id.RadioButtonA)
        RadioButton RadioButtonA;

        @BindView(R.id.RadioButtonB)
        RadioButton RadioButtonB;

        @BindView(R.id.RadioButtonC)
        RadioButton RadioButtonC;

        @BindView(R.id.RadioButtonD)
        RadioButton RadioButtonD;


        public CustomHolder(AdapterQuestionBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ButterKnife.bind(this, binding.getRoot());
        }

        public void bind(MD_Question item) {
            binding.setQuestion(item);

            RadioButtonA.setOnClickListener(v -> {
                if (RadioButtonA.isChecked())
                    clickItem.clickItemAnswer(1);
            });


            RadioButtonB.setOnClickListener(v -> {
                if (RadioButtonB.isChecked())
                    clickItem.clickItemAnswer(2);
            });

            RadioButtonC.setOnClickListener(v -> {
                if (RadioButtonC.isChecked())
                    clickItem.clickItemAnswer(3);
            });

            RadioButtonD.setOnClickListener(v -> {
                if (RadioButtonD.isChecked())
                    clickItem.clickItemAnswer(4);
            });

            binding.executePendingBindings();

            RadioButton[] radioButton = new RadioButton[4];
            radioButton[0] = RadioButtonA;
            radioButton[1] = RadioButtonB;
            radioButton[2] = RadioButtonC;
            radioButton[3] = RadioButtonD;
            radioGroupAnswer.removeAllViews();
            randomRadio(radioButton, radioGroupAnswer);

        }

    }
    //______________________________________________________________________________________________ CustomHolder


    //______________________________________________________________________________________________ randomRadio
    private void randomRadio(RadioButton[] radioButton, RadioGroup radioGroup) {

        for (int i = 0; i < 4; i++) {//this loop is randomly changing values 4 times
            int swap_ind1 = ((int) (Math.random() * 10) % radioButton.length);
            int swap_ind2 = ((int) (Math.random() * 10) % radioButton.length);
            RadioButton temp = radioButton[swap_ind1];
            radioButton[swap_ind1] = radioButton[swap_ind2];
            radioButton[swap_ind2] = temp;
        }
        addRadio(radioButton, radioGroup);

    }
    //______________________________________________________________________________________________ randomRadio


    //______________________________________________________________________________________________ addRadio
    private void addRadio(RadioButton[] radioButton, RadioGroup radioGroup) {
        for (RadioButton button : radioButton) {
            radioGroup.addView(button);
        }
    }
    //______________________________________________________________________________________________ addRadio

}
