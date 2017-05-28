package bit01.com.mx.echale.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import bit01.com.mx.echale.R;
import bit01.com.mx.echale.models.Historial;
import bit01.com.mx.echale.models.User;
import bit01.com.mx.echale.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Registro extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.input_name) EditText inputName;
    @BindView(R.id.input_last_name) EditText inputLastName;
    @BindView(R.id.input_date) EditText inputBirthDate;
    @BindView(R.id.input_email) EditText inputEmail;
    @BindView(R.id.input_password) EditText inputPassword;
    @BindView(R.id.input_confirm_password) EditText inputConfirmPassword;

    private DatePickerDialog dpd;
    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ButterKnife.bind(this);

        mFirebaseAuth = FirebaseAuth.getInstance();

        //Inicia Configuración Date Picker Dialog
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                Registro.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setYearRange(now.get(Calendar.YEAR)-75, now.get(Calendar.YEAR)-18);
        //Termina Configuración Date Picker Dialog
        mProgressDialog = new ProgressDialog(Registro.this);

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        inputBirthDate.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
    }

    @OnClick({R.id.textInputLayout_date, R.id.input_date})
    public void showDatePicker(){
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }


    @OnClick(R.id.btn_signup)
    public void createAccount(){
        registerUser();
    }

    private void registerUser() {
        // .trim() removes whitespaces from both sides of the string.
        final String email = inputEmail.getText().toString().trim();
        final String name = inputName.getText().toString().trim();
        final String lastName = inputLastName.getText().toString().trim();
        String birthDate = inputBirthDate.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String confirmPassword = inputConfirmPassword.getText().toString().trim();

        if( email.isEmpty() || name.isEmpty() || lastName.isEmpty() ||
            birthDate.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ){
            Toast.makeText( Registro.this , "No puede haber campos vacíos", Toast.LENGTH_LONG).show();
        }else{
            if (password.compareTo(confirmPassword) != 0) {
                Toast.makeText( Registro.this , "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                inputPassword.setText("");
                inputConfirmPassword.setText("");
            }else{
                if(password.length()<6){
                    Toast.makeText( Registro.this , "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_LONG).show();
                    inputPassword.setText("");
                    inputConfirmPassword.setText("");
                }else{
                    mProgressDialog.setTitle("Creando cuenta");
                    mProgressDialog.setMessage("Esta acción puede tomar algunos segundos..");
                    mProgressDialog.show();

                    mFirebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Registro.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if( task.isSuccessful()  ) {
                                        saveDataOnFirebase(name, lastName, email);
                                        Toast.makeText(Registro.this, "Registrado exitosamente", Toast.LENGTH_SHORT).show();
                                        mProgressDialog.hide();
                                        startActivity(new Intent(Registro.this, PartidosRecyclerViewActvity.class));
                                    }else {
                                        Log.e(Constants.LOG_TAG, task.getResult() + "");
                                        Toast.makeText(Registro.this, "No se pudo registrar, verifica los campos", Toast.LENGTH_SHORT).show();
                                        mProgressDialog.hide();
                                    }
                                }
                            });
                }
            }
        }

    }

    public void saveDataOnFirebase(String name, String lastName, String email){

        User user = new User(
                name + " " + lastName, email, 300, null
        );

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
    }


}
