package br.senai.sp.informatica.exemploautenticacao.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import br.senai.sp.informatica.exemploautenticacao.R;
import br.senai.sp.informatica.exemploautenticacao.lib.DataCalback;
import br.senai.sp.informatica.exemploautenticacao.lib.MessageCallBack;
import br.senai.sp.informatica.exemploautenticacao.model.Usuario;
import br.senai.sp.informatica.exemploautenticacao.model.UsuarioDao;

public class LoginActivity extends BaseActivity {
    private UsuarioDao dao = UsuarioDao.dao;

    private EditText edEmail;
    private EditText edSenha;

    public ProgressDialog progressDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edEmail = findViewById(R.id.edEmail);
        edSenha = findViewById(R.id.edSenha);
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            carregaMensagens(currentUser);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    private void carregaMensagens(FirebaseUser user) {
        hideProgressDialog();

        dao.salvar(new Usuario(user.getUid(), user.getEmail()),
                new MessageCallBack(this, "Fala na atualização de dados do usuário"));

        // redirecionar para MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void novoLoginClick(View view) {
        String email = edEmail.getText().toString();
        String senha = edSenha.getText().toString();

        showProgressDialog();

        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e("novoLogin", "uid: " + task.getResult().getUser().getUid());
                            carregaMensagens(task.getResult().getUser());
                        } else {
                            Toast.makeText(LoginActivity.this, "Falha na Autenticação.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        hideProgressDialog();
                    }
                });
    }

    public void loginClick(View view) {
        String email = edEmail.getText().toString();
        String senha = edSenha.getText().toString();

        showProgressDialog();

        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.e("login", "uid: " + task.getResult().getUser().getUid());
                            dao.jaEstaLogado(task.getResult().getUser().getUid(), new DataCalback(
                                    new DataCalback.OnDataChange() {
                                        @Override
                                        public void dataChange(DataSnapshot dataSnapshot) {
                                            boolean logado = dataSnapshot.getValue(Boolean.class);
                                            if (!logado) {
                                                carregaMensagens(task.getResult().getUser());
                                            } else {
                                                Toast.makeText(LoginActivity.this, "Esta conta já está em uso em outro aparelho",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }));
                        } else {
                            Toast.makeText(LoginActivity.this, "A Autenticação Falhou.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        hideProgressDialog();
                    }
                });
    }
}
