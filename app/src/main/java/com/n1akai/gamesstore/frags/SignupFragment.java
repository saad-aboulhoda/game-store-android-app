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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.n1akai.gamesstore.R;
import com.n1akai.gamesstore.models.User;

import java.util.regex.Pattern;

public class SignupFragment extends Fragment {

    View v;
    TextInputEditText fNameEt, lNameEt, emailEt, passwordEt, confirmPasswordEt;
    String fName, lName, email, password, confirmPassword;
    CircularProgressIndicator progressIndicator;
    Button signInBtn;
    FirebaseAuth mAuth;
    DatabaseReference mRef;
    NavController navController;

    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v = view;
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference("users");
        initView();
        createUser();
        navController = Navigation.findNavController(v);
        emailEt.setText(SignupFragmentArgs.fromBundle(getArguments()).getEmail());
    }

    private void initView() {
        fNameEt = v.findViewById(R.id.register_et_first_name);
        lNameEt = v.findViewById(R.id.register_et_last_name);
        emailEt = v.findViewById(R.id.signup_et_email);
        passwordEt = v.findViewById(R.id.signup_et_password);
        confirmPasswordEt = v.findViewById(R.id.register_et_confirm_password);
        signInBtn = v.findViewById(R.id.pay_button);
        progressIndicator = v.findViewById(R.id.circularProgressIndicator);
    }

    private boolean validateInputs() {
        fName = fNameEt.getText().toString().trim();
        lName = lNameEt.getText().toString().trim();
        email = emailEt.getText().toString().trim();
        password = passwordEt.getText().toString().trim();
        confirmPassword = confirmPasswordEt.getText().toString().trim();
        TextInputLayout confirmPasswordLayout = v.findViewById(R.id.register_til_confirm_password);
        if (fName.isEmpty() || lName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(getActivity().getBaseContext(), getResources().getString(R.string.all_fields_are_required), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (Pattern.compile("^(.+)@(\\\\S+)$").matcher(email).matches()) {
            Toast.makeText(getActivity().getBaseContext(), getResources().getString(R.string.email_is_invalid), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordLayout.setError(getResources().getString(R.string.passwords_dont_match));
            return false;
        }
        if (password.length() < 8) {
            confirmPasswordLayout.setError(getResources().getString(R.string.more_than_7));
            return false;
        }
        confirmPasswordLayout.setError(null);
        return true;
    }

    private void createUser() {
        signInBtn.setOnClickListener(v -> {
            if (!validateInputs()) return;
            inputEnabled(false);
            progressIndicator.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            inputEnabled(true);
                            progressIndicator.setVisibility(View.INVISIBLE);
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                mRef.child(user.getUid()).setValue(new User(fName, lName, email));
                                Toast.makeText(getActivity().getBaseContext(), getResources().getString(R.string.success), Toast.LENGTH_SHORT).show();
                                navigateToUser();
                            } else {
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthUserCollisionException e) {
                                    Toast.makeText(getActivity().getBaseContext(), getResources().getString(R.string.use_another_email), Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    Toast.makeText(getActivity().getBaseContext(), getResources().getString(R.string.something_went_wrong) + " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });
        });
    }

    private void inputEnabled(boolean b) {
        fNameEt.setEnabled(b);
        lNameEt.setEnabled(b);
        emailEt.setEnabled(b);
        passwordEt.setEnabled(b);
        confirmPasswordEt.setEnabled(b);
    }

    private void navigateToUser() {
        navController.navigate(SignupFragmentDirections.actionGlobalUserFragment());
    }
}