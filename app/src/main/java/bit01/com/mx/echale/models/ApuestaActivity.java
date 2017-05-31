package bit01.com.mx.echale.models;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.google.firebase.auth.FirebaseAuth;
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
import butterknife.OnTextChanged;
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

    @BindView(R.id.tvPosibleGanancia)
    TextView tvPosibleGanancia;

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

    Boolean yaSelecciono = false, alertaSeleccionMostrada=false;
    String evento = "";
    String apostadoresType="";
    String bolsaType="";
    String equipo = "";
    long partidoID;
    int intPartidoId;

    float mtotalEvento;
    int intMontoApuesta;
    String mGananciaProbable;

    FirebaseAuth mAuth;
    String userUid;

    private List<Partido> mPartidos = new ArrayList<>();
    private long mBolsaTotal, mBolsaLocal, mBolsaEmpate,mBolsaVisita;
    private float mFloatBolsaTotal, mFloatBolsaLocal, mFloatBolsaEmpate, mFloatBolsaVisita;

    public boolean yaAposto(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/");
        return false;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apuesta);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Échale!");

        mAuth = FirebaseAuth.getInstance();
        userUid = mAuth.getCurrentUser().getUid();

        Bundle extras = getIntent().getExtras();
        if(extras != null){

            localName.setText(extras.getString(Constants.TAG_LOCAL));
            awayName.setText(extras.getString(Constants.TAG_AWAY));

            partidoID = extras.getLong(Constants.TAG_PARTIDO_ID);
            intPartidoId = (int)partidoID;

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


    public void cambioSeleccion(){

        switch(evento){

            case "local":
                mtotalEvento = mFloatBolsaLocal;
                break;
            case "visita":
                mtotalEvento= mFloatBolsaVisita;
                break;
            case "empate":
                mtotalEvento = mFloatBolsaEmpate;
                break;
            default:
                mtotalEvento=0;
                break;

        }

        intMontoApuesta = Integer.parseInt(montoApuesta.getText().toString());
        mGananciaProbable = String.format("%.1f", ((intMontoApuesta/(mtotalEvento+intMontoApuesta))*(mBolsaTotal+intMontoApuesta)));
        tvPosibleGanancia.setText("$"+mGananciaProbable);

    }


    @OnTextChanged(R.id.montoApuesta)
    public void montoCambiado(Editable editable){

        if(!editable.toString().isEmpty()){

            if(!yaSelecciono){

                if(!alertaSeleccionMostrada){

                    alertaSeleccionMostrada = true;
                    Toast.makeText(this, "Selecciona un equipo para ver tu posible ganancia", Toast.LENGTH_LONG).show();

                }

            }else{

                switch(evento){

                    case "local":
                        mtotalEvento = mFloatBolsaLocal;
                        break;
                    case "visita":
                        mtotalEvento= mFloatBolsaVisita;
                        break;
                    case "empate":
                        mtotalEvento = mFloatBolsaEmpate;
                        break;
                    default:
                        mtotalEvento=0;
                        break;

                }

                intMontoApuesta = Integer.parseInt(editable.toString());
                mGananciaProbable = String.format("%.1f", ((intMontoApuesta/(mtotalEvento+intMontoApuesta))*(mBolsaTotal+intMontoApuesta)));
                tvPosibleGanancia.setText("$"+mGananciaProbable);

            }


        }else{

            tvPosibleGanancia.setText("$0");

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

        if(!montoApuesta.getText().toString().isEmpty()){
            cambioSeleccion();
        }else{

            Toast.makeText(this, "Ahora ingresa una apuesta", Toast.LENGTH_SHORT).show();

        }

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

        if(!montoApuesta.getText().toString().isEmpty()){
            cambioSeleccion();
        }else{
            Toast.makeText(this, "Ahora ingresa una apuesta", Toast.LENGTH_SHORT).show();
        }

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

        if(!montoApuesta.getText().toString().isEmpty()){
            cambioSeleccion();
        }else{
            Toast.makeText(this, "Ahora ingresa una apuesta", Toast.LENGTH_SHORT).show();
        }

    }

    public void traerPartidos(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/");

        final List<Partido> partidos = new ArrayList<>();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Toast.makeText(ApuestaActivity.this, "Partidos actualizados", Toast.LENGTH_SHORT).show();

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


        mFloatBolsaEmpate = mBolsaEmpate;
        mFloatBolsaLocal = mBolsaLocal;
        mFloatBolsaTotal = mBolsaTotal;
        mFloatBolsaVisita = mBolsaVisita;

        tvMomioLocal.setText(String.format("%.1f", ((mFloatBolsaLocal/mFloatBolsaTotal)*100.0))+"%");
        tvMomioEmpate.setText(String.format("%.1f", ((mFloatBolsaEmpate/mFloatBolsaTotal)*100.0))+"%");
        tvMomioVisita.setText(String.format("%.1f", ((mFloatBolsaVisita/mFloatBolsaTotal)*100.0))+"%");

        Toast.makeText(this, "YA lo hice bien", Toast.LENGTH_SHORT).show();

    }

    public void postData() {

        switch(evento){

            case "local":
                apostadoresType = "apostadoresLocal";
                bolsaType = "bolsaLocal";
                break;
            case "visita":
                apostadoresType = "apostadoresVisita";
                bolsaType = "bolsaVisita";
                break;
            case "empate":
                apostadoresType = "Empate";
                bolsaType = "bolsaEmpate";
                break;
            default:
                apostadoresType="";
                bolsaType="";
                break;

        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef= database.getReference("/partidosActuales/partido"+intPartidoId+"/apuestas/"+evento+"/"+bolsaType);
        myRef.setValue((long)(mtotalEvento+intMontoApuesta));

        myRef = database.getReference("/partidosActuales/partido"+intPartidoId+"/apuestas/bolsaTotalPartido");
        myRef.setValue(mBolsaTotal+intMontoApuesta);

        myRef = database.getReference("/partidosActuales/partido"+intPartidoId+"/apuestas/"+evento+"/"+apostadoresType+"/"+userUid);
        myRef.setValue(new Apostador(intMontoApuesta));


    }

    @OnClick(R.id.btnApuesta)
    public void apostar(){

        if(!yaAposto()){

            if(!montoApuesta.getText().toString().isEmpty() && yaSelecciono && intMontoApuesta>0){

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

                                postData();

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
}
