package prateektimer.com.firebaseemail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class verifyfragment extends Fragment implements
        OnClickListener {
    private static View view;
    private static EditText emailId;
    private static TextView submit, back, tv, tv8;
    FirebaseAuth mAuth;
    int k = 0;

    FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog pd;

    public verifyfragment() {

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null && !user.isEmailVerified()) {

            tv8.setText("" + user.getEmail() + " " + String.format(getString(R.string.notverified)));
            emailId.setText(user.getEmail());
        } else if (user != null && user.isEmailVerified()) {
            tv8.setText("You Already Have Verified!!!:)");
            tv.setVisibility(TextView.INVISIBLE);
            emailId.setVisibility(EditText.INVISIBLE);
            submit.setVisibility(TextView.GONE);
        } else {
            startActivity(new Intent(getActivity(), Home.class));
            getActivity().finishAffinity();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.verifyemail, container,
                false);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mAuth.getCurrentUser() == null) {
                    new MainActivity().replaceLoginFragment();
                    getActivity().finishAffinity();
                }
            }
        };
        initViews();
        setListeners();
        return view;
    }

    // Initialize the views
    private void initViews() {
        emailId = (EditText) view.findViewById(R.id.registered_emailid);

        submit = (TextView) view.findViewById(R.id.forgot_button);
        back = (TextView) view.findViewById(R.id.backToLoginBtn);
        tv = (TextView) view.findViewById(R.id.tv);
        tv8 = (TextView) view.findViewById(R.id.textView8);

        tv.setVisibility(TextView.VISIBLE);
        emailId.setText(MainActivity.emailver);

        // Setting text selector over textviews
        pd = new ProgressDialog(getActivity());


    }

    // Set Listeners over buttons
    private void setListeners() {
        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backToLoginBtn:

                // Replace Login Fragment on Back Presses
                new MainActivity().replaceLoginFragment();
                break;

            case R.id.forgot_button:

                // Call Submit button task

                submitButtonTask();
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
            pd.setMessage("Checking Information.......");
            pd.show();
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null)
                checkIfEmailVerified(user);
        }


    }


    private void checkIfEmailVerified(FirebaseUser user) {


        if (!user.isEmailVerified()) {
            pd.dismiss();

            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        mAuth.signOut();
                        new CustomToast2().Show_Toast(getActivity(), view,
                                "Verification Mail Sent Successfully :)!!!.");

                        Intent conceptIntent = new Intent(getActivity(), MainActivity.class);
                        startActivity(conceptIntent);
                    } else {
                        //Toast.makeText(getActivity(), "Failed to send verification", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else {
            pd.dismiss();
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user2 = mAuth.getCurrentUser();
            updateUI(user2);
        }
    }
}