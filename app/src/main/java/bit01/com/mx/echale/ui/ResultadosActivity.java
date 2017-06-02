package bit01.com.mx.echale.ui;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

import bit01.com.mx.echale.R;
import bit01.com.mx.echale.models.Apostador;
import bit01.com.mx.echale.models.ApuestaActivity;
import bit01.com.mx.echale.models.Historial;
import bit01.com.mx.echale.models.Partido;
import bit01.com.mx.echale.models.User;
import bit01.com.mx.echale.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class ResultadosActivity extends AppCompatActivity {

    @BindView(R.id.aireNombreResultados)
    TextView aireResultado;

    @BindView(R.id.localTeamNameResultados)
    TextView localName;

    @BindView(R.id.awayTeamNameResultados)
    TextView awayName;

    @BindView(R.id.tvEmpateResultados)
    TextView tvEmpate;

    @BindView(R.id.localTeamImageResultados)
    CircleImageView logoLocal;

    @BindView(R.id.awayTeamImageResultados)
    CircleImageView logoVisita;

    SweetAlertDialog pDialog;

    String partidoID;
    int intPartidoId;
    String urlLocal;
    String urlVisita;
    String mFechaPartido;
    FirebaseAuth mAuth;
    String userUid;
    String resultado;

    boolean terminoMetodoConfirmacionApoostado = false;
    boolean yaApostoLocal = false;
    boolean yaApostoEmpate = false;
    boolean yaApostoVisita = false;
    String cadenaReferenciaApuesta;

    private long mBolsaTotal, mBolsaLocal, mBolsaEmpate,mBolsaVisita, mMontoApostado;
    private float mFloatBolsaTotal, mFloatBolsaLocal, mFloatBolsaEmpate, mFloatBolsaVisita;

    User usuarioActual;
    long monedasActuales;

    String eventoApuesta;
    Apostador apostador;
    Partido partidoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        userUid = mAuth.getCurrentUser().getUid();

        Bundle extras = getIntent().getExtras();
        if(extras != null){

            resultado = extras.getString(Constants.TAG_RESULTADOS);
            localName.setText(extras.getString(Constants.TAG_LOCAL));
            awayName.setText(extras.getString(Constants.TAG_AWAY));

            partidoID = extras.getString(Constants.TAG_PARTIDO_ID);
            intPartidoId = calcularIDPartido(partidoID);


            if(!extras.getString(Constants.TAG_AWAY_IMAGE).isEmpty()) {
                Picasso.with(ResultadosActivity.this)
                        .load(extras.getString(Constants.TAG_AWAY_IMAGE))
                        .resize(100,100)
                        .into(logoVisita);

                urlVisita = extras.getString(Constants.TAG_AWAY_IMAGE);
            }

            if(!extras.getString(Constants.TAG_LOCAL_IMAGE).isEmpty()) {
                Picasso.with(ResultadosActivity.this)
                        .load(extras.getString(Constants.TAG_LOCAL_IMAGE))
                        .resize(100,100)
                        .into(logoLocal);

                urlLocal = extras.getString(Constants.TAG_LOCAL_IMAGE);
            }
            mFechaPartido = extras.getString(Constants.TAG_DATE);

            updateUi(resultado);

        }

        yaAposto();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Resultado Partido #"+intPartidoId);
        }

        traerPartidoActual();
        traerDatosUsuario();

    }

    public void traerDatosUsuario(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/users/"+userUid);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                usuarioActual = dataSnapshot.getValue(User.class);
                monedasActuales = usuarioActual.getMonedas();

                //Toast.makeText(ApuestaActivity.this, "Usuario Actualizado", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void updateUi(String resultado) {

        switch(resultado){

            case "local":
                aireResultado.setVisibility(View.GONE);
                tvEmpate.setVisibility(View.GONE);
                logoVisita.setVisibility(View.GONE);
                awayName.setVisibility(View.GONE);
                break;
            case "empate":
                break;
            case "visita":
                aireResultado.setVisibility(View.GONE);
                tvEmpate.setVisibility(View.GONE);
                logoLocal.setVisibility(View.GONE);
                localName.setVisibility(View.GONE);
                break;

        }

    }

    public int calcularIDPartido(String string){

        return Integer.parseInt(string.substring(1));

    }

    public void traerApostador(String cadena){

        if(cadena.length()>=35){

            cadena = cadena.substring(35);
            Log.e("LogVergas",cadena);
            //Toast.makeText(this, cadenaReferenciaApuesta, Toast.LENGTH_SHORT).show();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference(cadena+"/"+userUid);
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //apostador = null;
                    apostador = dataSnapshot.getValue(Apostador.class);

                    if(apostador != null){

                        mMontoApostado = apostador.getMonto();


                    }

                    //TODO AQUI ME QUEDE
                    //Toast.makeText(ResultadosActivity.this,apostador.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }

    public void traerPartidoActual(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("/partidosActuales/partido" + intPartidoId + "/");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                partidoActual = null;
                partidoActual = dataSnapshot.getValue(Partido.class);

                Map<String, Object> apuestasPartidoActual = partidoActual.getApuestas();
                mBolsaTotal = (long) apuestasPartidoActual.get("bolsaTotalPartido");

                Map<String, Object> apuestasLocal = (Map<String, Object>) apuestasPartidoActual.get("local");
                mBolsaLocal = (long) apuestasLocal.get("bolsaLocal");

                Map<String, Object> apuestasEmpate = (Map<String, Object>) apuestasPartidoActual.get("empate");
                mBolsaEmpate = (long) apuestasEmpate.get("bolsaEmpate");

                Map<String, Object> apuestasVista = (Map<String, Object>) apuestasPartidoActual.get("visita");
                mBolsaVisita = (long) apuestasVista.get("bolsaVisita");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public boolean yaAposto(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef1 = database.getReference("/partidosActuales/partido"+intPartidoId+"/apuestas/empate/apostadoresEmpate");

        if(!yaApostoLocal && !yaApostoVisita && !yaApostoEmpate){

            myRef1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.hasChild(userUid)){
                        yaApostoEmpate= true;
                        Toast.makeText(ResultadosActivity.this, "soy bien pinshi lento empate", Toast.LENGTH_SHORT).show();
                        Log.e("LogVergas", "empate");
                        cadenaReferenciaApuesta = myRef1.toString();
                        eventoApuesta = "empate";
                        traerApostador(myRef1.toString());

                    }else{

                        yaApostoEmpate=false;

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        if(!yaApostoLocal && !yaApostoVisita && !yaApostoEmpate){

            final DatabaseReference myRef2 = database.getReference("/partidosActuales/partido"+intPartidoId+"/apuestas/local/apostadoresLocal");
            myRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.hasChild(userUid)){
                        Toast.makeText(ResultadosActivity.this, "soy bien pinshi lento Local", Toast.LENGTH_SHORT).show();
                        Log.e("LogVergas", "local");
                        eventoApuesta="local";
                        yaApostoLocal= true;
                        cadenaReferenciaApuesta = myRef2.toString();
                        traerApostador(myRef2.toString());

                    }else{

                        yaApostoLocal=false;

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        if(!yaApostoLocal && !yaApostoVisita && !yaApostoEmpate){

            final DatabaseReference myRef3 = database.getReference("/partidosActuales/partido"+intPartidoId+"/apuestas/visita/apostadoresVisita");
            myRef3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Toast.makeText(ResultadosActivity.this, "yaaaaa", Toast.LENGTH_SHORT).show();
                    if(dataSnapshot.hasChild(userUid)){
                        yaApostoVisita= true;
                        cadenaReferenciaApuesta = myRef3.toString();
                        eventoApuesta = "visita";
                        Log.e("LogVergas", "visita");
                        traerApostador(myRef3.toString());
                        //Toast.makeText(ApuestaActivity.this, "soy bien pinshi lento visita", Toast.LENGTH_SHORT).show();
                    }else{
                        yaApostoVisita=false;
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }


        terminoMetodoConfirmacionApoostado = true;
        return yaApostoLocal || yaApostoEmpate || yaApostoVisita;
        //TODO Arreglar la lentitud cawn

    }

    @OnClick(R.id.btnReclamarRecompensa)
    public void reclamarRecompensa(){

        if(yaAposto()){

            FirebaseDatabase database = FirebaseDatabase.getInstance();

            if(eventoApuesta.equals(resultado)){

                //ganaste
                DatabaseReference myRef2 = database.getReference("/users/"+userUid+"/monedas/");
                myRef2.setValue((long)calcularMonedasUsuario((int)monedasActuales, (int)mMontoApostado));

                myRef2=database.getReference("/users/"+userUid+"/historial/p"+intPartidoId);
                myRef2.setValue(new Historial(eventoApuesta,mMontoApostado, localName.getText().toString(), urlLocal, awayName.getText().toString(), urlVisita, mFechaPartido, calcularCadenaResultadoParaHistorial()));

                myRef2=database.getReference("/users/"+userUid+"/apuestasPendientes/");
                myRef2.child("p"+intPartidoId).removeValue();

                myRef2=database.getReference(cadenaReferenciaApuesta.substring(35));
                myRef2.child(userUid).removeValue();

                Toast.makeText(this, "Parece que todo salió bien cawn", Toast.LENGTH_SHORT).show();

                SweetAlertDialog pDialog2 = new SweetAlertDialog(ResultadosActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Felicidades!")
                        .setContentText("Ganaste $"+(int)(calcularMonedasUsuario((int)monedasActuales, (int)mMontoApostado)-monedasActuales))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                startActivity(new Intent(ResultadosActivity.this,PartidosRecyclerViewActvity.class));

                            }
                        });
                pDialog2.show();

            }else{

                DatabaseReference myRef2 = database.getReference("/users/"+userUid+"/monedas/");

                //perdiste, wey
                myRef2=database.getReference("/users/"+userUid+"/historial/p"+intPartidoId);
                myRef2.setValue(new Historial(eventoApuesta,mMontoApostado, localName.getText().toString(), urlLocal, awayName.getText().toString(), urlVisita, mFechaPartido, calcularCadenaResultadoParaHistorial()));

                myRef2=database.getReference("/users/"+userUid+"/apuestasPendientes/");
                myRef2.child("p"+intPartidoId).removeValue();

                myRef2=database.getReference(cadenaReferenciaApuesta.substring(35));
                myRef2.child(userUid).removeValue();

                SweetAlertDialog pDialog2 = new SweetAlertDialog(ResultadosActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Lo siento...")
                        .setContentText("Perdiste, pero relájate al máximo y échale más!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                startActivity(new Intent(ResultadosActivity.this,PartidosRecyclerViewActvity.class));

                            }
                        });
                pDialog2.show();

            }


        }else{

            pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Error")
                    .setContentText("Parece que no apostaste en este partido, o ya revisaste la recompensa.")
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            pDialog.dismissWithAnimation();
                            startActivity(new Intent(ResultadosActivity.this, PartidosRecyclerViewActvity.class));
                        }
                    });
            pDialog.show();

        }

    }

    public String calcularCadenaResultadoParaHistorial(){

        if(eventoApuesta.equals(partidoActual.getResultado())){

            return "Ganaste!";

        }else{

            return "Échale de nuevo!";

        }

    }

    public float calcularMonedasUsuario(int monActual, int montoApuesta) {

        float gananciaUSuario = 0;

        /*
        mFloatBolsaEmpate = (float) mBolsaEmpate;
        mFloatBolsaLocal = (float)mBolsaLocal;
        mFloatBolsaTotal = (float)mBolsaTotal;
        mFloatBolsaVisita = (float)mBolsaVisita;*/

        switch(partidoActual.getResultado()){

            case "local":
                gananciaUSuario = (montoApuesta/(float)mBolsaLocal)*(float)mBolsaTotal;
                break;
            case "empate":
                gananciaUSuario = (montoApuesta/(float)mBolsaEmpate)*(float)mBolsaTotal;
                break;
            case "visita":
                gananciaUSuario = (montoApuesta/(float)mBolsaVisita)*(float)mBolsaTotal;
                break;


        }

        Log.e("LogVergas", "Este pedo son las monedas actuales: "+monActual+"");

        Log.e("LogVergas", "Este pedo es la ganaancia: "+gananciaUSuario+"");
        return gananciaUSuario+monActual;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(ResultadosActivity.this, PartidosRecyclerViewActvity.class));
                break;
        }
        return true;
    }

}
