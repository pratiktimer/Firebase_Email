package prateektimer.com.firebaseemail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    public static String emailver;
    static RelativeLayout loginLayout;
    private static FragmentManager fragmentManager;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseAuth mAuth;

    private void updateUI(FirebaseUser user) {
        if (user != null && user.isEmailVerified()) {
            //Toast.makeText(getApplicationContext(),""+user.isEmailVerified(),Toast.LENGTH_SHORT).show();

            startActivity(new Intent(MainActivity.this, Home.class));
            finish();


        } else if (user != null && !user.isEmailVerified()) {
            //	Toast.makeText(getApplicationContext(),""+user.isEmailVerified(),Toast.LENGTH_SHORT).show();
            try {
                replaceverifyFragment();
            } catch (Exception e) {
            }
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.frameContainer, new Login_Fragment(),
                            Utils.Login_Fragment).commit();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
//		FirebaseUser cuser=mAuth.getCurrentUser();
        //updateUI(cuser);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main34);

        fragmentManager = getSupportFragmentManager();
        //loginLayout = (RelativeLayout) findViewById(R.id.Relative1);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                updateUI(mAuth.getCurrentUser());
            }
        };


        // If savedinstnacestate is null then replace login fragment
        //if (savedInstanceState == null) {
        //	loginLayout.setBackgroundColor(Color.parseColor("#010101"));

        //loginLayout.setBackgroundResource(R.mipmap.p23);
//			fragmentManager
//					.beginTransaction()
//					.replace(R.id.frameContainer, new Login_Fragment(),
//							Utils.Login_Fragment).commit();
        //	}

        // On close icon click finish activity
        findViewById(R.id.close_activity).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        finish();

                    }
                });

    }

    public void forgotpassword() {
        //	loginLayout.setBackgroundColor(Color.parseColor("#010101"));
        // Replace forgot password fragment with animation
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.frameContainer,
                        new ForgotPassword_Fragment(),
                        Utils.ForgotPassword_Fragment).commit();
    }

    public void createaccount() {

        //loginLayout.setBackgroundColor(Color.parseColor("#010101"));
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.frameContainer, new SignUp_Fragment(),
                        Utils.SignUp_Fragment).commit();
    }

    // Replace Login Fragment with animation
    public void replaceLoginFragment() {
        //	loginLayout.setBackgroundColor(Color.parseColor("#010101"));
//		loginLayout.setBackgroundResource(R.mipmap.p23);
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new Login_Fragment(),
                        Utils.Login_Fragment).commit();

    }

    public void replaceverifyFragment() {
        //	loginLayout.setBackgroundColor(Color.parseColor("#010101"));
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.frameContainer,
                        new verifyfragment(),
                        Utils.verifyemail).commit();

    }

    @Override
    public void onBackPressed() {

        // Find the tag of signup and forgot password fragment
        Fragment SignUp_Fragment = fragmentManager
                .findFragmentByTag(Utils.SignUp_Fragment);
        Fragment ForgotPassword_Fragment = fragmentManager
                .findFragmentByTag(Utils.ForgotPassword_Fragment);
        Fragment verifyemail = fragmentManager
                .findFragmentByTag(Utils.verifyemail);

        if (SignUp_Fragment != null)
            replaceLoginFragment();
        else if (ForgotPassword_Fragment != null)
            replaceLoginFragment();
        else if (verifyemail != null)
            replaceLoginFragment();
        else
            super.onBackPressed();
    }


    @Override
    public void onResume() {
        super.onResume();
//		mAuth = FirebaseAuth.getInstance();
//		FirebaseUser currentUser = mAuth.getCurrentUser();
//		updateUI(currentUser);
    }


}
