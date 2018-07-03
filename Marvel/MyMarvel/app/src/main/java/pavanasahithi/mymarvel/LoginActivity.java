package pavanasahithi.mymarvel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.id_gmail_login)
    EditText gmail;
    @BindView(R.id.id_password_login)
    EditText password;
    @BindView(R.id.id_login)
    Button login;
    @BindView(R.id.id_cancel_login)
    Button cancel;
    SharedPreferences sharedPreferences;
    FirebaseAuth auth;
    SharedPreferences.Editor editor;
    @BindView(R.id.id_register_here)
    TextView register;
    final static String MYPREF="MyPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gmail.setText("");
                password.setText("");
            }
        });
        sharedPreferences = getApplicationContext().getSharedPreferences(MYPREF, 0);
        editor = sharedPreferences.edit();
        editor.apply();
        if (sharedPreferences.contains(getResources().getString(R.string.isLogged))) {
            if (sharedPreferences.getBoolean(getResources().getString(R.string.isLogged), false)) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(intent);
            }
        }
        auth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gmail.getText().length() == 0) {
                    gmail.setError(getResources().getString(R.string.requiresusername));
                } else {
                    if (password.getText().length() == 0) {
                        password.setError(getResources().getString(R.string.requiresPassword));
                    } else {
                        if (checkNetwork())
                            retrievefromDatabase();
                        else
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.noInternet), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gmail.setText("");
                password.setText("");
            }
        });
    }

    private void retrievefromDatabase() {
        auth.signInWithEmailAndPassword(gmail.getText().toString(), password.getText().toString())
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            editor.putBoolean(getResources().getString(R.string.isLogged), false);
                            editor.commit();
                            Toast.makeText(LoginActivity.this, R.string.authenticationFailed, Toast.LENGTH_LONG).show();
                        } else {
                            editor.putBoolean(getResources().getString(R.string.isLogged), true);
                            editor.commit();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            finish();
                            startActivity(intent);
                        }
                    }
                });
    }

    public boolean checkNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null)
            return true;
        else
            return false;
    }
}
