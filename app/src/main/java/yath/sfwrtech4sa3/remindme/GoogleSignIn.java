package yath.sfwrtech4sa3.remindme;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

public class GoogleSignIn extends Activity {
    private SignInClient oneTapClient;
    private static final int REQ_ONE_TAP = 98;
    private BeginSignInRequest signInRequest;
    private boolean showSignUpUI = false;
    private Intent splashIntent;
    private FirebaseAuth firebaseAuth;
    private User user;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.splashIntent = getIntent();
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.createSignIn();
        this.displayOneTapUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    SignInCredential credential = this.oneTapClient.getSignInCredentialFromIntent(data);
                    String id_token = credential.getGoogleIdToken();
                    if (id_token != null) {
                        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(id_token, null);
                        this.firebaseAuth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            firebaseUser = firebaseAuth.getCurrentUser();
                                            splashIntent.putExtra("UID", firebaseUser.getUid());
                                            splashIntent.putExtra("DISPLAY_NAME", firebaseUser.getDisplayName());
                                            splashIntent.putExtra("PICTURE_URI", Objects.requireNonNull(firebaseUser.getPhotoUrl()).toString());
                                            splashIntent.putExtra("EMAIL", firebaseUser.getEmail());
                                            setResult(RESULT_OK, splashIntent);
                                            finish();
                                        } else {
                                            Log.d("yathavan", "sign in with credential FAILURE");
                                        }
                                    }
                                });
                    }
                } catch (ApiException exception) {
                    switch (exception.getStatusCode()) {
                        case CommonStatusCodes.CANCELED:
                            Log.d("yathavan", "One tap dialog was closed");
                            this.showSignUpUI = false;
                            break;
                        case CommonStatusCodes.NETWORK_ERROR:
                            Log.d("yathavan", "One tap encountered a network error");
                            this.showSignUpUI = false;
                            break;
                        default:
                            Log.d("yathavan", "Couldn't get credential from result");
                            break;
                    }
                }
                break;
        }
    }

    private void createSignIn() {
        this.oneTapClient = Identity.getSignInClient(this.getApplicationContext());
        this.signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                        BeginSignInRequest.GoogleIdTokenRequestOptions
                                .builder()
                                .setSupported(true)
                                .setServerClientId(this.getApplicationContext().getString(R.string.web_client_id)) // o-auth server id stored in strings.xml
                                .setFilterByAuthorizedAccounts(false)
                                .build())
                .setAutoSelectEnabled(true)
                .build();
    }

    public void displayOneTapUI() {
        this.oneTapClient.beginSignIn(this.signInRequest)
                .addOnSuccessListener(this, beginSignInResult -> {
                    try {
                        startIntentSenderForResult(
                                beginSignInResult.getPendingIntent().getIntentSender(),
                                REQ_ONE_TAP,
                                null,
                                0,
                                0,
                                0
                        );
                    } catch (IntentSender.SendIntentException error) {
                        Log.e("yathavan", "Couldn't start One Tap UI: " + error.getLocalizedMessage());
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("yathavan", "Display One Tap UI Failure: " + e.getLocalizedMessage());
                    }
                });
    }
}