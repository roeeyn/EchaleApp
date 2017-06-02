package bit01.com.mx.echale.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import bit01.com.mx.echale.R;
import bit01.com.mx.echale.models.ApuestaPendiente;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by roeeyn on 1/06/17.
 */

public class ApuestasAdapter extends RecyclerView.Adapter<ApuestasAdapter.ApuestasViewHolder>{

    private List<ApuestaPendiente> apuestas;
    private Context context;

    public ApuestasAdapter(List<ApuestaPendiente> apuestas) {
        this.apuestas = apuestas;
    }

    @Override
    public ApuestasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_apuestas, parent, false);

        ApuestasViewHolder viewHolder = new ApuestasViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ApuestasViewHolder holder, int position) {
        ApuestaPendiente promocion = apuestas.get(position);
        holder.bindHistorial(promocion, position+1);
    }

    @Override
    public int getItemCount() {
        return apuestas.size();
    }

    public class ApuestasViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.apuestas_local_team_image)
        CircleImageView localTeamImage;

        @BindView(R.id.apuestas_away_team_image)
        CircleImageView awayTeamImage;

        @BindView(R.id.apuestas_local_team_name)
        TextView localTeamName;

        @BindView(R.id.apuestas_away_team_name)
        TextView awayTeamName;

        @BindView(R.id.apuestas_match_date)
        TextView matchDate;

        @BindView(R.id.apuestas_evento)
        TextView evento;

        @BindView(R.id.apuestas_apuesta)
        TextView apuestaNumero;

        @BindView(R.id.apuestas_monto)
        TextView monto;

        View rootView;

        public ApuestasViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindHistorial(ApuestaPendiente apuestaPendiente, int numeroApuesta){
            localTeamName.setText(apuestaPendiente.getNombreLocal());
            awayTeamName.setText(apuestaPendiente.getNombreVisita());
            matchDate.setText(apuestaPendiente.getFecha());
            evento.setText("Apostaste: " + apuestaPendiente.getEvento());
            monto.setText("Monto: " + apuestaPendiente.getMonto());
            apuestaNumero.setText("Apuesta #" + numeroApuesta);

            if (!apuestaPendiente.getUriLocal().isEmpty()) {

                Picasso.with(context)
                        .load(apuestaPendiente.getUriLocal())
                        .resize(80,80)
                        .into(localTeamImage);


            }

            if (!apuestaPendiente.getUriVisita().isEmpty()) {

                Picasso.with(context)
                        .load(apuestaPendiente.getUriVisita())
                        .resize(80,80)
                        .into(awayTeamImage);
            }
        }
    }

}
