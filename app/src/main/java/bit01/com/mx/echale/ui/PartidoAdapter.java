package bit01.com.mx.echale.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import bit01.com.mx.echale.models.Partido;
import bit01.com.mx.echale.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ericklara on 24/05/17.
 */

public class PartidoAdapter extends RecyclerView.Adapter<PartidoAdapter.PartidoViewHolder>{

    private List<Partido> matchList;
    private Context context;

    public PartidoAdapter(List<Partido> matchList) {
        this.matchList = matchList;
    }

    @Override
    public PartidoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_partido, parent, false);

        PartidoViewHolder viewHolder = new PartidoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PartidoViewHolder holder, int position) {
        Partido partido = matchList.get(position);
        holder.bindPartido(partido);
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    class PartidoViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.local_team_image)
        CircleImageView localTeamImage;

        @BindView(R.id.away_team_image)
        CircleImageView awayTeamImage;

        @BindView(R.id.local_team_name)
        TextView localTeamName;

        @BindView(R.id.away_team_name)
        TextView awayTeamName;

        @BindView(R.id.match_date)
        TextView matchDate;

        @BindView(R.id.match_time)
        TextView matchTime;

        public PartidoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindPartido(Partido partido){
            localTeamName.setText(partido.getLocalTeam());
            awayTeamName.setText(partido.getAwayTeam());
            matchDate.setText(partido.getDate());
            matchTime.setText(partido.getTime());
            /*
            if(!partido.getLocalTeamImageUrl().isEmpty()) {
                Glide.with(context)
                        .load(partido.getLocalTeamImageUrl())
                        .into(localTeamImage);
            }

            if(!partido.getAwayTeamImageUrl().isEmpty()) {
                Glide.with(context)
                        .load(partido.getAwayTeamImageUrl())
                        .into(awayTeamImage);
            }*/

            localTeamImage.setImageResource(R.drawable.coyotes);
            awayTeamImage.setImageResource(R.drawable.coyotes2);

        }
    }
}
