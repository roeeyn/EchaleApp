package bit01.com.mx.echale.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import bit01.com.mx.echale.R;
import bit01.com.mx.echale.utils.Constants;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AyudaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Ayuda");
        }

    }

    @OnClick(R.id.btnHabilitarGuias)
    public void onClickHabilitarGuias(){

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.TAG_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.TAG_GUIA_APUESTA,false);
        editor.putBoolean(Constants.TAG_GUIA_PARTIDOS, false);
        editor.commit();

        Toast.makeText(this, "Se han habilitado las guías", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                startActivity(new Intent(AyudaActivity.this, PartidosRecyclerViewActvity.class));
                break;

        }

        return true;

    }

}
