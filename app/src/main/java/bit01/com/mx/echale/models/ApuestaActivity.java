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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import bit01.com.mx.echale.R;
import bit01.com.mx.echale.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;


public class ApuestaActivity extends AppCompatActivity {

    @BindView(R.id.momioLocal)
    TextView tvMomioLocal;

    @BindView(R.id.momioEmpate)
    TextView tvMomioEmpate;

    @BindView(R.id.momioVisita)
    TextView tvMomioVisita;

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
    long partidoID;

    private List<Partido> mPartidos = new ArrayList<>();
    private long mBolsaTotal, mBolsaLocal, mBolsaEmpate,mBolsaVisita;

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

            partidoID = extras.getLong(Constants.TAG_PARTIDO_ID);

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

        traerPartidos();

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

    public void traerPartidos(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/");

        final List<Partido> partidos = new ArrayList<>();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Toast.makeText(ApuestaActivity.this, "Que pedoooo "+partidoID, Toast.LENGTH_SHORT).show();

                partidos.clear();

                Iterable<DataSnapshot> children = dataSnapshot.child("partidosActuales").getChildren();
                for(DataSnapshot child : children){

                    partidos.add(child.getValue(Partido.class));

                }

                mPartidos = partidos;

                updateApuestas(mPartidos);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void updateApuestas(List<Partido> partidos) {

        Partido partidoActual = partidos.get((int)partidoID-1);

        Map<String, Object> apuestasPartidoActual = partidoActual.getApuestas();

        mBolsaTotal = (long) apuestasPartidoActual.get("bolsaTotalPartido");

        Map<String, Object> apuestasLocal = (Map<String, Object>) apuestasPartidoActual.get("local");
        mBolsaLocal = (long) apuestasLocal.get("bolsaLocal");

        Map<String, Object> apuestasEmpate = (Map<String, Object>) apuestasPartidoActual.get("empate");
        mBolsaEmpate = (long) apuestasEmpate.get("bolsaEmpate");

        Map<String, Object> apuestasVista = (Map<String, Object>) apuestasPartidoActual.get("visita");
        mBolsaVisita = (long) apuestasVista.get("bolsaVisita");

        tvMomioLocal.setText(mBolsaLocal+"");
        tvMomioEmpate.setText(mBolsaEmpate+"");
        tvMomioVisita.setText(mBolsaVisita+"");

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
