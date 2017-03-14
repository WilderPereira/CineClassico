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
import com.studiotrek.cineclassico.view.activity.MainActivity;

import static com.studiotrek.cineclassico.util.Util.usernameFromEmail;

/**
 * Created by Admin on 04/03/2017.
 */

public class SignUpFragment extends Fragment {

    private ProgressDialog progressDialog;
    private EditText etSignupEmail;
    private EditText etSignupSenha;
    private Button bnSignupConectar;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public SignUpFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_signup, container, false);

        etSignupEmail = (EditText) view.findViewById(R.id.et_signup_email);
        etSignupSenha = (EditText) view.findViewById(R.id.et_signup_senha);
        bnSignupConectar = (Button) view.findViewById(R.id.bn_signup_conectar);

        bnSignupConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });

        return view;
    }

    private void cadastrar() {
        if (!validateForm()) {
            return;
        }

        progressDialog = ProgressDialog.show(getContext(), getResources().getString(R.string.app_name),
                getResources().getString(R.string.realizando_login), false, true);
        progressDialog.setIcon(R.mipmap.ic_launcher_round);
        progressDialog.setCancelable(false);
        bnSignupConectar.setEnabled(false);

        String email = etSignupEmail.getText().toString();
        String password = etSignupSenha.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
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

    private void iniciar() {
        CinePreference.setLogin(getContext());
        startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().finish();
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());
        writeNewUser(user.getUid(), username, user.getEmail());

        progressDialog.dismiss();
        bnSignupConectar.setEnabled(true);

        iniciar();
    }

    private void writeNewUser(String userId, String username, String email) {
        Usuario usuario = new Usuario(username, email);
        mDatabase.child("usuario").child(userId).setValue(usuario);
    }

    private boolean validateForm() {
        boolean result = true;
        if (TextUtils.isEmpty(etSignupEmail.getText().toString())) {
            etSignupEmail.setError(getResources().getString(R.string.invalido));
            result = false;
        } else {
            etSignupEmail.setError(null);
        }

        if (TextUtils.isEmpty(etSignupSenha.getText().toString())) {
            etSignupSenha.setError(getResources().getString(R.string.invalido));
            result = false;
        } else {
            etSignupSenha.setError(null);
        }

        return result;
    }

}
