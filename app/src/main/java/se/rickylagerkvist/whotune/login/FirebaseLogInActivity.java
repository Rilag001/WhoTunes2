package se.rickylagerkvist.whotune.login;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import se.rickylagerkvist.whotune.R;
import se.rickylagerkvist.whotune.view.SelectNyOfPlayersActivity;

public class FirebaseLogInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    SignInButton mSignInButton;
    GoogleApiClient mGoogleApiClient;
    private static final String TAG = "SignInactivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference myProfileRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        mSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent startMainActivity = new Intent(FirebaseLogInActivity.this, SelectNyOfPlayersActivity.class);
                    startMainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(startMainActivity);
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //handleSignInResult(result);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        //AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // get info from acct
                        String displayName = acct.getDisplayName();
                        String photoURL = acct.getPhotoUrl().toString();
                        String uid = mAuth.getCurrentUser().getUid();

                        // save profile to database
                        myProfileRef = FirebaseDatabase.getInstance().getReference("users").child(uid);
                        Profile myProfile = new Profile(displayName, photoURL, "");
                        myProfileRef.setValue(myProfile);

                        // save mUserUid to sharedPref
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("USERUID", uid).apply();
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("DISPLAY_NAME", displayName).apply();
                        if (!TextUtils.isEmpty(photoURL)){
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("PHOTO_URL", photoURL).apply();
                        } else {
                            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("PHOTO_URL", "defaultStringIfNothingFound").apply();
                        }

                        // start MainActivity
                        Intent startMainActivity = new Intent(FirebaseLogInActivity.this, SelectNyOfPlayersActivity.class);
                        startMainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(startMainActivity);

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(FirebaseLogInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onContactionFaild:" + connectionResult);
    }
}
