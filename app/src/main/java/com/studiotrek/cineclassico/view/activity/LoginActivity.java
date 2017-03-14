package com.studiotrek.cineclassico.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.studiotrek.cineclassico.R;
import com.studiotrek.cineclassico.model.Usuario;
import com.studiotrek.cineclassico.util.CinePreference;
import com.studiotrek.cineclassico.util.FirebaseUtil;
import com.studiotrek.cineclassico.util.Util;
import com.studiotrek.cineclassico.view.fragment.LoginFragment;
import com.studiotrek.cineclassico.view.fragment.SignUpFragment;

public class LoginActivity extends BaseActivity {

    private ProgressDialog progressDialog;
    private FragmentTransaction fragmentTransaction;
    private FrameLayout fragmentContainer;

    private LoginButton lbFacebook;
    private CallbackManager callbackManager;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);

        if(!Util.isOnline(LoginActivity.this)) {
            SnackbarOffline(fragmentContainer);
        }

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseUtil.getDatabase().getReference();
        callbackManager = CallbackManager.Factory.create();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }

            Fragment fragment = new LoginFragment();
            fragmentTransaction.add(R.id.fragment_container, fragment).commit();
        }

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    iniciar();
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

    public void goToLogin(View view) {
        Util.navigate(getSupportFragmentManager().beginTransaction(), R.id.fragment_container, new LoginFragment());
    }

    public void goToSignUp(View view) {
        Util.navigate(getSupportFragmentManager().beginTransaction(), R.id.fragment_container, new SignUpFragment());
    }

    public void loginFacebook(View view) {
        lbFacebook = (LoginButton) view.findViewById(R.id.lb_login_facebook);

        if (lbFacebook != null) {
            lbFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    handleFacebookAccessToken(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.nao_possivel), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(FacebookException error) {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.nao_possivel), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        progressDialog = ProgressDialog.show(LoginActivity.this, getResources().getString(R.string.app_name),
                getResources().getString(R.string.realizando_login), false, true);
        progressDialog.setIcon(R.mipmap.ic_launcher_round);
        progressDialog.setCancelable(false);
        lbFacebook.setEnabled(false);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser());
                    CinePreference.setLogin(LoginActivity.this);
                } else {
                    Toast.makeText(LoginActivity.this, getResources().getString(R.string.nao_possivel), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = Util.usernameFromEmail(user.getEmail());
        writeNewUser(user.getUid(), username, user.getEmail());

        progressDialog.dismiss();
        lbFacebook.setEnabled(true);

        iniciar();
    }

    private void writeNewUser(String userId, String name, String email) {
        Usuario usuario = new Usuario(name, email);
        mDatabase.child("usuario").child(userId).setValue(usuario);
    }

    private void iniciar() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
