package bit01.com.mx.echale.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import bit01.com.mx.echale.models.ApuestaActivity;
import bit01.com.mx.echale.models.Partido;
import bit01.com.mx.echale.R;
import bit01.com.mx.echale.utils.Constants;
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

        String mLocalUrl;
        String mAwayUrl;
        String partidoID;

        private View rootView;

        public PartidoViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void bindPartido(Partido partido){
            localTeamName.setText(partido.getNombreLocal());
            awayTeamName.setText(partido.getNombreVisita());
            matchDate.setText(partido.getFecha());
            matchTime.setText(partido.getHora());
            mLocalUrl = partido.getUrlLocal();
            mAwayUrl = partido.getUrlVisita();
            partidoID = partido.getIdPartido();
            /*
            if(!partido.getLocalTeamImageUrl().isEmpty()) {
                Glide.with(context)
                        .load(mLocalUrl)
                        .into(localTeamImage);
            }

            if(!partido.getAwayTeamImageUrl().isEmpty()) {
                Glide.with(context)
                        .load(mAwayUrl)
                        .into(awayTeamImage);
            }*/

            if(!partido.getLocalTeamImageUrl().isEmpty()) {
                Picasso.with(context)
                        .load(mLocalUrl)
                        .resize(80,80)
                        .into(localTeamImage);
            }

            if(!partido.getAwayTeamImageUrl().isEmpty()) {
                Picasso.with(context)
                        .load(mAwayUrl)
                        .resize(80,80)
                        .into(awayTeamImage);
            }

            rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, ApuestaActivity.class);
                    intent.putExtra(Constants.TAG_LOCAL, localTeamName.getText().toString());
                    intent.putExtra(Constants.TAG_AWAY, awayTeamName.getText().toString());
                    intent.putExtra(Constants.TAG_LOCAL_IMAGE, mLocalUrl);
                    intent.putExtra(Constants.TAG_AWAY_IMAGE, mAwayUrl);
                    intent.putExtra(Constants.TAG_PARTIDO_ID, partidoID);
                    intent.putExtra(Constants.TAG_DATE, matchDate.getText().toString());
                    context.startActivity(intent);

                }
            });

        }
    }
}
