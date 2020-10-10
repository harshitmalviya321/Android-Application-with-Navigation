package com.halo.signup.ui.password;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PasswordViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PasswordViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Change Password fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}