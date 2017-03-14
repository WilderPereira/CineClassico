package com.studiotrek.cineclassico.view.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.studiotrek.cineclassico.R;
import com.studiotrek.cineclassico.model.Usuario;
import com.studiotrek.cineclassico.util.CinePreference;
import com.studiotrek.cineclassico.util.Constantes;
import com.studiotrek.cineclassico.util.FirebaseUtil;
import com.studiotrek.cineclassico.util.Util;
import com.studiotrek.cineclassico.view.activity.MainActivity;

/**
 * Created by Admin on 04/03/2017.
 */

public class LoginFragment extends Fragment {

    private ProgressDialog progressDialog;
    private EditText etLoginEmail;
    private EditText etLoginSenha;
    private Button bnLoginConectar;
    private LoginButton lbFacebook;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public LoginFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseUtil.getDatabase().getReference();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);

        etLoginEmail = (EditText) view.findViewById(R.id.et_login_email);
        etLoginSenha = (EditText) view.findViewById(R.id.et_login_senha);
        bnLoginConectar = (Button) view.findViewById(R.id.bn_login_conectar);
        lbFacebook = (LoginButton) view.findViewById(R.id.lb_login_facebook);

        bnLoginConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conectar();
            }
        });

        return view;
    }

    private void iniciar() {
        CinePreference.setLogin(getContext());
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish();
    }

    private void conectar() {
        if (!validateForm()) {
            return;
        }

        progressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.app_name),
                getResources().getString(R.string.realizando_login), false, true);
        progressDialog.setIcon(R.mipmap.ic_launcher_round);
        progressDialog.setCancelable(false);
        bnLoginConectar.setEnabled(false);
        lbFacebook.setEnabled(false);

        String email = etLoginEmail.getText().toString();
        String password = etLoginSenha.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    onAuthSuccess(task.getResult().getUser());
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.nao_possivel), Toast.LENGTH_SHORT).show();
                    Log.e(Constantes.TAG, task.getException().toString());
                }
            }
        });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = Util.usernameFromEmail(user.getEmail());
        writeNewUser(user.getUid(), username, user.getEmail());

        progressDialog.dismiss();
        bnLoginConectar.setEnabled(true);
        lbFacebook.setEnabled(true);

        iniciar();
    }

    private void writeNewUser(String userId, String name, String email) {
        Usuario usuario = new Usuario(name, email);
        mDatabase.child("usuario").child(userId).setValue(usuario);
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(etLoginEmail.getText().toString())) {
            etLoginEmail.setError(getResources().getString(R.string.invalido));
            result = false;
        } else {
            etLoginEmail.setError(null);
        }

        if (TextUtils.isEmpty(etLoginEmail.getText().toString())) {
            etLoginEmail.setError(getResources().getString(R.string.invalido));
            result = false;
        } else {
            etLoginEmail.setError(null);
        }

        return result;
    }
}
