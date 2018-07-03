package pavanasahithi.mymarvel;

import android.content.Context;
import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.id_gmail)
    EditText gmail;
    @BindView(R.id.id_password)
    EditText password;
    @BindView(R.id.id_cofirm_password)
    EditText confirm_password;
    @BindView(R.id.id_register)
    Button register;
    @BindView(R.id.id_cancel)
    Button cancel;
    private FirebaseAuth auth;
    String user_string,password_string,confirm_string_password;
    @BindView(R.id.id_login_here)
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        auth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_string = gmail.getText().toString();
                password_string = password.getText().toString();
                confirm_string_password= confirm_password.getText().toString();
                if (!user_string.isEmpty()) {
                    if (!password_string.isEmpty()) {
                        if (password_string.equals(confirm_string_password)) {
                            if (checkNetwork()) {
                                addToFirebase();
                            } else {
                                Toast.makeText(RegisterActivity.this, R.string.noInternet, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            password.setError(getString(R.string.passwordsNoMatch));
                        }
                    } else {
                        password.setError(getString(R.string.requiresPassword));
                    }
                } else {
                    gmail.setError(getString(R.string.requiresusername));
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gmail.setText("");
                password.setText("");
                confirm_password.setText("");
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void addToFirebase() {
        auth.createUserWithEmailAndPassword(user_string, password_string)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, getResources().getString(R.string.authenticationFailed) + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                });
    }

    private boolean checkNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null)
            return true;
        else
            return false;
    }

}

