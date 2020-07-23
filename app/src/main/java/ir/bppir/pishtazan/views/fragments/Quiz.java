package ir.bppir.pishtazan.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import ir.bppir.pishtazan.R;
import ir.bppir.pishtazan.databinding.FragmentQuizBinding;
import ir.bppir.pishtazan.viewmodels.fragments.VM_Quiz;


public class Quiz extends FragmentPrimary implements FragmentPrimary.GetMessageFromObservable {


    private VM_Quiz vm_quiz;
    private Integer movieId;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {//__________________________________________________________ onCreateView
        if (getView() == null) {
            FragmentQuizBinding binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_quiz, container, false);
            vm_quiz = new VM_Quiz(getActivity());
            binding.setQuiz(vm_quiz);
            setView(binding.getRoot());
            movieId = getArguments().getInt(getContext().getResources().getString(R.string.ML_Id), 0);
            init();
        }
        return getView();
    }//_____________________________________________________________________________________________ onCreateView


    @Override
    public void onStart() {//_______________________________________________________________________ onStart
        super.onStart();
        setGetMessageFromObservable(
                Quiz.this,
                vm_quiz.getPublishSubject(),
                vm_quiz);
    }//_____________________________________________________________________________________________ onStart


    private void init() {//_________________________________________________________________________ init

    }//_____________________________________________________________________________________________ init



    @Override
    public void GetMessageFromObservable(Byte action) {//___________________________________________ GetMessageFromObservable

    }//_____________________________________________________________________________________________ GetMessageFromObservable
}
