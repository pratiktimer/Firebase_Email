package prateektimer.com.firebaseemail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

;

public class SignUp_Fragment extends Fragment implements OnClickListener {
    private static FragmentManager fragmentManager;
    private static View view;
    private static EditText fullName, emailId, location,
            password, confirmPassword;
    private static TextView login;
    private static MaterialButton signUpButton;
    private static CheckBox terms_conditions;
    int i = 0;
    int d = 0;
    private ProgressDialog pd;
    private FirebaseAuth mAuth;

    public SignUp_Fragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        d = 0;

        view = inflater.inflate(R.layout.signup_layout, container, false);
        mAuth = FirebaseAuth.getInstance();
        initViews();
        setListeners();
        return view;

    }

    // Initialize all views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        // fullName = (EditText) view.findViewById(R.id.fullName);
        emailId = (EditText) view.findViewById(R.id.userEmailId);
        // location = (EditText) view.findViewById(R.id.location);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        signUpButton = (MaterialButton) view.findViewById(R.id.signUpBtn);
        login = (TextView) view.findViewById(R.id.already_user);
        terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);
        pd = new ProgressDialog(getActivity());

    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:
                i = 0;
                if (d != 1)
                    checkValidation();
                break;
            case R.id.already_user:
                // Replace login fragment
                new MainActivity().replaceLoginFragment();
                break;
        }

    }

    // Check Validation Method
    private void checkValidation() {

        // Get all edittext texts
        final String getEmailId = emailId.getText().toString();
        final String getPassword = password.getText().toString();
        final String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getEmailId.equals("") || getEmailId.length() == 0

                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "All fields are required.");

            //ProgressDialog dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);
        }


        // Check if email id valid or not
        else if (!m.find()) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");
        } else if (getPassword.length() < 6) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Password should be atleast 6 characters Long");
        }

        // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword)) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Both password doesn't match.");
        }

        // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked()) {
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please select Terms and Conditions.");
        }

        // Else do signup or do your stuff
        else {

            pd.setMessage("Signing Up.......:)");
            pd.show();
            if (pd.isShowing()) {
                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }

            if (i == 0) {

                mAuth = FirebaseAuth.getInstance();
                mAuth.createUserWithEmailAndPassword(getEmailId, getConfirmPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    pd.dismiss();
                                    d = 1;

                                    pd.dismiss();
                                    if (getActivity() != null) {
                                        new CustomToast2().Show_Toast(getActivity(), view,
                                                "Sign Up Done Successfully :)!!!.");
                                    }


                                    FirebaseUser user = mAuth.getCurrentUser();

                                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                if (getActivity() != null) {
                                                    new CustomToast2().Show_Toast(getActivity(), view,
                                                            "verification Mail Send Successfully :)!!!.");
                                                    mAuth.signOut();
                                                    Intent conceptIntent = new Intent(getActivity(), MainActivity.class);
                                                    startActivity(conceptIntent);
                                                }
                                            } else {

                                            }

                                        }
                                    });

                                } else {
                                    pd.dismiss();
                                    if (getActivity() != null) {
                                        Toast.makeText(getActivity(), "Failed to Sign Up", Toast.LENGTH_SHORT).show();
                                    }
                                }


                            }
                        });

            } else {
                pd.dismiss();
                if (i == 1) {

                } else {
                    new CustomToast().Show_Toast(getActivity(), view,
                            "Something Went Wrong :(!!!.");
                }
            }

        }

        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

}
