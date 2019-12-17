package mobildev.iosm.com.priot.ViewHolder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mobildev.iosm.com.priot.R;
import mobildev.iosm.com.priot.interfaces.ItemClickListner;

public class PresenceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProfilName,txtProfilLastName,txtIdCarte,txtEntryTime;
    public ImageView imageView;
    public ItemClickListner listner;
    public CardView c;
    public PresenceViewHolder(View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.presence_image);
        txtProfilName = itemView.findViewById(R.id.presence_name);
        txtProfilLastName = itemView.findViewById(R.id.presenceLastName);
        txtIdCarte = itemView.findViewById(R.id.presence_idCarte);
        c = itemView.findViewById(R.id.card_pres);
        txtEntryTime =itemView.findViewById(R.id.entry_date);

    }
    public void setItemClickListner (ItemClickListner listner){
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(), false);
    }

}
