package com.n1akai.gamesstore.frags;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.n1akai.gamesstore.R;

public class SigninWithEmailAndPassFragment extends Fragment {

    View v;
    TextInputEditText emailEt, passwordEt;
    CircularProgressIndicator progressIndicator;
    Button signinBtn;
    FirebaseAuth mAuth;
    NavController navController;

    public SigninWithEmailAndPassFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin_with_email_and_pass, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v = view;
        initView();
        mAuth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(v);
        signupBtnListener();
    }

    private void initView() {
        emailEt = v.findViewById(R.id.signup_et_email);
        passwordEt = v.findViewById(R.id.signup_et_password);
        progressIndicator = v.findViewById(R.id.circularProgressIndicator);
        signinBtn = v.findViewById(R.id.pay_button);
    }

    private void signupBtnListener() {
        signinBtn.setOnClickListener(v -> {
            inputEnabled(false);
            String email = emailEt.getText().toString();
            String password = passwordEt.getText().toString();
            progressIndicator.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    inputEnabled(true);
                    progressIndicator.setVisibility(View.INVISIBLE);
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                        navigateToUserFrag();
                    } else {
                        Toast.makeText(getContext(), getResources().getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    private void inputEnabled(boolean b) {
        emailEt.setEnabled(b);
        passwordEt.setEnabled(b);
    }

    private void navigateToUserFrag() {
        navController.navigate(SigninWithEmailAndPassFragmentDirections.actionGlobalUserFragment());
    }
}