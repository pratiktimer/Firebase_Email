package prateektimer.com.firebaseemail;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword_Fragment extends Fragment implements
        OnClickListener {
    private static View view;
    private static EditText emailId;
    private static TextView submit, back, verifyemail2;
    FirebaseAuth mAuth;
    int k = 0;
    private ProgressDialog dialog;

    public ForgotPassword_Fragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null && !user.isEmailVerified()) {
            verifyemail2.setVisibility(TextView.VISIBLE);
        } else {
            verifyemail2.setVisibility(TextView.GONE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgotpassword_layout, container,
                false);
        initViews();
        setListeners();
        return view;
    }

    // Initialize the views
    private void initViews() {
        emailId = (EditText) view.findViewById(R.id.registered_emailid);
        verifyemail2 = (TextView) view.findViewById(R.id.verifyemail2);
        submit = (TextView) view.findViewById(R.id.forgot_button);
        back = (TextView) view.findViewById(R.id.backToLoginBtn);
        dialog = new ProgressDialog(getActivity());
        mAuth = FirebaseAuth.getInstance();


        // Setting text selector over textviews


    }

    // Set Listeners over buttons
    private void setListeners() {
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
        verifyemail2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToLoginBtn:

                // Replace Login Fragment on Back Presses
                new MainActivity().replaceLoginFragment();

                break;

            case R.id.forgot_button:

                submitButtonTask();
                break;
            case R.id.verifyemail2:

                new MainActivity().replaceverifyFragment();

                break;

        }

    }

    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString();

        // Pattern for email id validation
        Pattern p = Pattern.compile(Utils.regEx);

        // Match the pattern
        Matcher m = p.matcher(getEmailId);

        // First check if email id is not null else show error toast
        if (getEmailId.equals("") || getEmailId.length() == 0)

            new CustomToast().Show_Toast(getActivity(), view,
                    "Please enter your Email Id.");

            // Check if email id is valid or not
        else if (!m.find())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Your Email Id is Invalid.");

            // Else submit email id and fetch passwod or do your stuff
        else {
            dialog.setMessage("Checking Information.....");
            dialog.show();

            checkIfEmailVerified(getEmailId);

        }


    }


    private void checkIfEmailVerified(String email) {

        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        //if (user!=null&&user.isEmailVerified()) {
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "Check Your Mail To Reset Password.",
                                    Toast.LENGTH_LONG).show();
//								mAuth.signOut();
                            new MainActivity().replaceLoginFragment();
                        } else {
                            dialog.dismiss();
                            if (getActivity() != null) {
                                Toast.makeText(getActivity(), "Please Register!!!", Toast.LENGTH_LONG).show();
                            }

                        }
                    }
                });


    }

}