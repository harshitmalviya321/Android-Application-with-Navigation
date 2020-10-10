package com.halo.signup.ui.password;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.halo.signup.HomePage;
import com.halo.signup.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import static com.halo.signup.Details.TAG;

public class PasswordFragment extends Fragment {

    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    Button changePass;
    private PasswordViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(PasswordViewModel.class);
        View root = inflater.inflate(R.layout.fragment_password, container, false);
        changePass = root.findViewById(R.id.changePass);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Update Password");
        builder.setMessage("Do you really wish to change password?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                firebaseAuth = FirebaseAuth.getInstance();
            }
        });
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });

        return root;

    }

    private void updatePassword() {

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        final String newPwd = ((EditText) getView().findViewById(R.id.update_password_t)).getText().toString();
        if(TextUtils.isEmpty(newPwd) || newPwd.length() < 6){
            Toast.makeText(getActivity(),
                    "Invalid password, please enter valid password",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        user.updatePassword(newPwd)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(),
                                    "Password has been updated",
                                    Toast.LENGTH_SHORT).show();
                            Intent hp = new Intent(getActivity(), HomePage.class);
                            startActivity(hp);
                            getActivity().finish();
                        } else {
                            Log.e(TAG, "Error in updating passowrd",
                                    task.getException());
                            Toast.makeText(getActivity(),
                                    "Failed to update passwrod.",
                                    Toast.LENGTH_SHORT).show();
                            Intent hp = new Intent(getActivity(), HomePage.class);
                            startActivity(hp);
                            getActivity().finish();
                        }
                    }
                });
    }

}