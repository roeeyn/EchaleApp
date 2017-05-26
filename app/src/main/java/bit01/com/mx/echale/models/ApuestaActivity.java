package bit01.com.mx.echale.models;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import bit01.com.mx.echale.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ApuestaActivity extends AppCompatActivity {

    @BindView(R.id.btnApuesta)
    Button btnApuesta;

    SweetAlertDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apuesta);
        ButterKnife.bind(this);


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
}
