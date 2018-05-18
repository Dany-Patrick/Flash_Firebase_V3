package cl.dany.flash_firebase_v3.Views.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;

import cl.dany.flash_firebase_v3.Data.CurrentUser;
import cl.dany.flash_firebase_v3.Data.LoginCallback;
import cl.dany.flash_firebase_v3.MainActivity;
import cl.dany.flash_firebase_v3.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity implements LoginCallback{
    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        if (new CurrentUser().getCurrentUser() != null) {
            logged();
        } else {
            signUp();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(RC_SIGN_IN == requestCode)
        {
            if(RESULT_OK  == resultCode)
            {
                logged();
            }
        }
    }

    @Override
    public void signUp() {


            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
                                    new AuthUI.IdpConfig.EmailBuilder().build(),
                                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                                    new AuthUI.IdpConfig.FacebookBuilder().build(),
                                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                                    new AuthUI.IdpConfig.TwitterBuilder().build()))
                            .setTheme(R.style.LoginTheme)
                            .setLogo(R.mipmap.logo)
                            .build(),
                    RC_SIGN_IN);


    }

    @Override
    public void logged() {


            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();



    }
}
