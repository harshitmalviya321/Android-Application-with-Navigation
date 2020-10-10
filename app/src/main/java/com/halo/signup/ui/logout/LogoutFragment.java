package com.halo.signup.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.halo.signup.R;
import com.halo.signup.login;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class LogoutFragment extends Fragment {

    Button logoutBtn;
    FirebaseAuth fAuth;

    private LogoutViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(LogoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_logout, container, false);

        logoutBtn = root.findViewById(R.id.logoutBtn);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            fAuth.getInstance().signOut();
                Intent closeAll = new Intent(getActivity(), login.class);
                startActivity(closeAll);
                getActivity().finishAffinity();
            }
        });
        return root;
    }
}