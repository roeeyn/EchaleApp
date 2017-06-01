package bit01.com.mx.echale.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

import bit01.com.mx.echale.R;
import bit01.com.mx.echale.models.User;
import bit01.com.mx.echale.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity {

    // Google sign in variables
    private SignInButton mGoogleBtn;
    private GoogleApiClient mGoogleApiClient;

    // Firebase variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog mProgressDialog;

    @BindView(R.id.login_input_email)
    EditText inputEmailText;

    @BindView(R.id.login_input_password)
    EditText inputPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inicialización de ButterKnife
        ButterKnife.bind(this);

        // Inicialización del ProgressDialog
        mProgressDialog = new ProgressDialog(Login.this);

        // Button bind to the view
        mGoogleBtn = (SignInButton) findViewById(R.id.sign_in_button);

        // Tomamos la instancia de Firebase
        mAuth = FirebaseAuth.getInstance();

        // Configuración de Google Sign In
        // Se obtiene el id del cliente para crear el objeto de opciones
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Builder para configurar la GoogleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Toast.makeText(Login.this, "Hay un error!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        /**
         * Cuando cerramos la apliación se inicializa este método
         * verificamos si ya se ha iniciado sesión antes, de ser así
         * mostramos la lista de partidos.
         */
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("myLog", "onAuthStateChanged:signed_in:" + user.getUid());
                    // Show the recycler view of the matches.

                    startActivity(new Intent(Login.this, PartidosRecyclerViewActvity.class));
                } else {
                    // User is signed out
                    // Don't do nothing, just show the Login activity.
                    Log.d("myLog", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        // Al presionar el botón de Google Sign In
        mGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    @OnClick(R.id.email_login_button)
    public void emailSignIn(){
        userLogin();
    }

    private void userLogin() {
        // Tomamos el correo
        final String email = inputEmailText.getText().toString().trim();
        // Tomamos el password
        final String password = inputPasswordText.getText().toString().trim();
        if( email.isEmpty() ){
            Toast.makeText( Login.this , "El campo correo no puede estar vacío", Toast.LENGTH_LONG).show();
        }else if( password.isEmpty() ){
            Toast.makeText( Login.this , "El campo password no puede estar vacío", Toast.LENGTH_LONG).show();
        }else {

            // Se asignan los valores iniciales del ProgressDialog
            mProgressDialog.setTitle("Iniciando sesión");
            mProgressDialog.setMessage("Esta acción puede tomar algunos segundos..");
            mProgressDialog.show();

            // Se realiza el inicio de sesión con correo y contraseña
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if ( task.isSuccessful() ){
                                // Para el intent es this y a cual activity va con terminación.class
                                mProgressDialog.hide();
                                Intent intent = new Intent(Login.this, PartidosRecyclerViewActvity.class);
                                intent.putExtra("UserEmail", email);
                                startActivity(intent);
                            }else{
                                mProgressDialog.hide();

                                // Validaciones de correo y contraseña, para mostrar errores al usuario
                                if (!Constants.validateEmail(email))
                                    Toast.makeText(Login.this, "Ingresa un correo valido", Toast.LENGTH_SHORT).show();
                                else{
                                    mAuth.fetchProvidersForEmail(email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                                            if (task.isSuccessful()){
                                                if (task.getResult().getProviders().size() > 0)
                                                    Toast.makeText(Login.this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                                                else
                                                    Toast.makeText(Login.this, "Este correo no está registado", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Se agrega el listener a la instancia de FirebaseAuth
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        FirebaseAuth.getInstance().signOut();
    }

    // Método para inicio de sesión con Google
    private void signIn() {
        // Inicio de sesión con google
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }


    @Override
    // Método para recibir el resultado de la acción de SignIn()
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        Log.d(Constants.LOG_TAG, requestCode + "");

        // RequestCode tiene que ser 9001
        if (requestCode == Constants.RC_SIGN_IN) {
            // Objeto con la información del intent -- De hecho el intent de SignIn()
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
                Log.d(Constants.LOG_TAG, result.getStatus().getStatusCode() + "");
                Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Inicio de sesión con cuenta google
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(Constants.LOG_TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(Constants.LOG_TAG, "signInWithCredential", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else{

                            saveDataOnFirebase(
                                    mAuth.getCurrentUser().getDisplayName(),
                                    mAuth.getCurrentUser().getEmail(),
                                    mAuth.getCurrentUser().getPhotoUrl().toString()
                            );
                            startActivity(new Intent(Login.this, PartidosRecyclerViewActvity.class));
                        }
                        // ...
                    }
                });
    }


    public void saveDataOnFirebase(String name, String email, String photoUrl){

        User user = new User(
                name, email, 300, "", null, photoUrl
        );

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users");
        myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
    }

    // Manda a la actividad para registro
    @OnClick(R.id.link_signup)
    public void signUpLink(){
        startActivity(new Intent(Login.this, Registro.class));
    }

    // Manda a la actividad para recuperar contraseña
    @OnClick(R.id.link_restore_password)
    public void restorePasswordLink(){
        startActivity(new Intent(Login.this, RestorePassword.class));
    }
}
