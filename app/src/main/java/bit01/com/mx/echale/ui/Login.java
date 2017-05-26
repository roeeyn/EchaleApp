package bit01.com.mx.echale.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import bit01.com.mx.echale.R;
import bit01.com.mx.echale.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity {

    @BindView(R.id.email)
    EditText etUser;

    @BindView(R.id.password)
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonLogin)
    public void onFabClick(){

        if(checarDatosVacios()){
            ButterKnife.bind(this);
            Intent intent = new Intent(Login.this, PartidosRecyclerViewActvity.class);
            intent.putExtra(Constants.TAG_USER, etUser.getText().toString());
            startActivity(intent);

        }

    }

    public boolean checarDatosVacios(){

        if(!etUser.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()){

            return true;

        }else{

            Toast.makeText(Login.this, "Ingresa todos los datos", Toast.LENGTH_SHORT).show();
            return false;

        }

    }

}