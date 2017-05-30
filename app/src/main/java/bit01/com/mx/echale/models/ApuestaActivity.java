package bit01.com.mx.echale.models;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.squareup.picasso.Picasso;

import bit01.com.mx.echale.R;
import bit01.com.mx.echale.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static bit01.com.mx.echale.R.color.colorAccent;
import static bit01.com.mx.echale.R.color.colorPrimaryText;

public class ApuestaActivity extends AppCompatActivity {

    @BindView(R.id.btnApuesta)
    Button btnApuesta;

    @BindView(R.id.montoApuesta)
    EditText montoApuesta;

    @BindView(R.id.localTeamNameApuesta)
    TextView localName;

    @BindView(R.id.awayTeamNameApuesta)
    TextView awayName;

    @BindView(R.id.localTeamImage)
    CircleImageView logoLocal;

    @BindView(R.id.awayTeamImage)
    CircleImageView logoVisita;

    @BindView(R.id.tvEmpate)
    TextView tvEmpate;

    SweetAlertDialog pDialog;

    Boolean yaSelecciono = false;
    String evento = "";
    String equipo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apuesta);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Échale!");

        Bundle extras = getIntent().getExtras();
        if(extras != null){

            localName.setText(extras.getString(Constants.TAG_LOCAL));
            awayName.setText(extras.getString(Constants.TAG_AWAY));

            if(!extras.getString(Constants.TAG_AWAY_IMAGE).isEmpty()) {
                Picasso.with(ApuestaActivity.this)
                        .load(extras.getString(Constants.TAG_AWAY_IMAGE))
                        .resize(100,100)
                        .into(logoVisita);
            }

            if(!extras.getString(Constants.TAG_LOCAL_IMAGE).isEmpty()) {
                Picasso.with(ApuestaActivity.this)
                        .load(extras.getString(Constants.TAG_LOCAL_IMAGE))
                        .resize(100,100)
                        .into(logoLocal);
            }

        }

    }

    public String cadenaConfirmacionApuesta(){

        if(evento.equalsIgnoreCase("empate")){

            return "el empate";

        }else{

            return "la victoria de "+equipo;

        }

    }

    @OnClick(R.id.localTeamImage)
    public void clickLocal(){

        equipo = localName.getText().toString();
        evento = "local";
        logoLocal.setBorderWidth(25);
        logoLocal.setBorderColor(getResources().getColor(R.color.colorAccent));
        logoVisita.setBorderWidth(0);
        logoVisita.setBorderColor(getResources().getColor(R.color.colorPrimaryText));
        tvEmpate.setBackgroundColor(getResources().getColor(R.color.colorTransparente));
        yaSelecciono = true;
    }

    @OnClick(R.id.awayTeamImage)
    public void clickAway(){

        equipo = awayName.getText().toString();
        evento = "visita";
        logoLocal.setBorderWidth(0);
        logoLocal.setBorderColor(getResources().getColor(R.color.colorPrimaryText));
        logoVisita.setBorderWidth(25);
        logoVisita.setBorderColor(getResources().getColor(R.color.colorAccent));
        tvEmpate.setBackgroundColor(getResources().getColor(R.color.colorTransparente));
        yaSelecciono = true;

    }

    @OnClick(R.id.tvEmpate)
    public void clickEmpate(){

        equipo = "";
        evento = "empate";
        logoLocal.setBorderWidth(0);
        logoLocal.setBorderColor(getResources().getColor(R.color.colorPrimaryText));
        logoVisita.setBorderWidth(0);
        logoVisita.setBorderColor(getResources().getColor(R.color.colorPrimaryText));
        tvEmpate.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        yaSelecciono = true;

    }

    @OnClick(R.id.btnApuesta)
    public void apostar(){

        if(!montoApuesta.getText().toString().isEmpty() && yaSelecciono){

            String monto = montoApuesta.getText().toString();

            pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Estás seguro?")
                    .setContentText("Estás apostando $"+monto+" por "+cadenaConfirmacionApuesta()+"!")
                    .setCancelText("No, cancelar!")
                    .setConfirmText("Sí, ¡échale!")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            Toast.makeText(ApuestaActivity.this, "Cancelaste", Toast.LENGTH_SHORT).show();
                            sDialog.cancel();
                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Toast.makeText(ApuestaActivity.this, "Lo aceptaste cawn!", Toast.LENGTH_SHORT).show();
                            sweetAlertDialog.dismiss();
                        }
                    });

            pDialog.show();

        }else{

            if(montoApuesta.getText().toString().isEmpty()){

                Toast.makeText(this, "Ingresa un monto a apostar", Toast.LENGTH_SHORT).show();

            }else{

                Toast.makeText(this, "Selecciona un evento (Presiona sobre un equipo)", Toast.LENGTH_SHORT).show();

            }

        }


    }
}
