package bit01.com.mx.echale.models;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import bit01.com.mx.echale.R;
import bit01.com.mx.echale.ui.PartidosRecyclerViewActvity;
import bit01.com.mx.echale.utils.Constants;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class ApuestaActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @BindView(R.id.btnApuesta)
    Button btnApuesta;

    @BindView(R.id.localTeamNameApuesta)
    TextView localName;

    @BindView(R.id.awayTeamNameApuesta)
    TextView awayName;

    @BindView(R.id.localTeamImage)
    CircleImageView logoLocal;

    @BindView(R.id.awayTeamImage)
    CircleImageView logoVisita;

    SweetAlertDialog pDialog;

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
                Glide.with(ApuestaActivity.this)
                        .load(extras.getString(Constants.TAG_AWAY_IMAGE))
                        .into(logoVisita);
            }

            if(!extras.getString(Constants.TAG_LOCAL_IMAGE).isEmpty()) {
                Glide.with(ApuestaActivity.this)
                        .load(extras.getString(Constants.TAG_LOCAL_IMAGE))
                        .into(logoLocal);
            }

        }

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean pruebaMostrada = sharedPreferences.getBoolean(Constants.TAG_PRUEBA_YA_HECHA, false);
        if(!pruebaMostrada){

            final Drawable droid = ContextCompat.getDrawable(this, R.drawable.ic_cards_white_24dp);

            final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 25, droid.getIntrinsicHeight() * 25);


            final TapTargetSequence sequence = new TapTargetSequence(this)
                    .targets(

                            TapTarget.forBounds(droidTarget, "Échale!", "En esta sección podrás elegir el partido de tu preferencia y apostar.")
                                    .cancelable(false)
                                    .icon(droid)
                                    .id(3)



                    ).listener(new TapTargetSequence.Listener() {
                        @Override
                        public void onSequenceFinish() {

                            Toast.makeText(ApuestaActivity.this, "Ahora haz tu primera apuesta", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                        }

                        @Override
                        public void onSequenceCanceled(TapTarget lastTarget) {

                            Toast.makeText(ApuestaActivity.this, "Algo salió mal :(", Toast.LENGTH_SHORT).show();

                        }
                    });

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Constants.TAG_PRUEBA_YA_HECHA, true);
            editor.commit();
            sequence.start();

        }

    }

    @OnClick(R.id.btnApuesta)
    public void apostar(){

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Estás seguro?")
                .setContentText("Estás apostando ${cantidad} por {Equipo}!")
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

    }

    public Activity getActivity() {
        return this;
    }
}
