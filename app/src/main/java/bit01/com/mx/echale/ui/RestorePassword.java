package bit01.com.mx.echale.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import bit01.com.mx.echale.R;
import bit01.com.mx.echale.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RestorePassword extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;

    @BindView(R.id.restore_input_email) EditText restorePasswordEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restore_password);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.restore_password_button)
    public void restorePassword(){
        String email = restorePasswordEmail.getText().toString().trim();

        mProgressDialog = new ProgressDialog(RestorePassword.this);
        mProgressDialog.setTitle("Enviando mail");
        mProgressDialog.setMessage("Esta acción puede tomar algunos segundos..");
        mProgressDialog.show();

        /**
         *  Se debe instanciar FirebaseAuth
         *  o se genera NullPointerException
         */
        mAuth = FirebaseAuth.getInstance();

        // Retorna un task
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if( task.isSuccessful() ){
                            Toast.makeText(RestorePassword.this, "Se ha enviado a tu correo la solicitud", Toast.LENGTH_SHORT).show();
                            Log.e(Constants.LOG_TAG, "Successful password restore");
                            mProgressDialog.hide();
                            startActivity(new Intent(RestorePassword.this, Login.class));
                        }else{
                            Toast.makeText(RestorePassword.this, "Parece que tu correo está mal.", Toast.LENGTH_SHORT).show();
                            mProgressDialog.hide();
                            Log.e(Constants.LOG_TAG, "Unsuccessful password restore");
                        }
                    }
                }
        );
    }

}
