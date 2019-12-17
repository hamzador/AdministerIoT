package mobildev.iosm.com.priot.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import mobildev.iosm.com.priot.Model.Profil;
import mobildev.iosm.com.priot.R;
import mobildev.iosm.com.priot.interfaces.ItemClickListner;

public class ProfilViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public  TextView txtProfilName,txtProfilLastName,txtProfilRegistration,txtProfilIdCarte;
    public ImageView imageView;
    public ItemClickListner listner;
    public Button active;

    //private List<>

    public ProfilViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.profil_image);
        txtProfilName = itemView.findViewById(R.id.user_name);
        txtProfilLastName = itemView.findViewById(R.id.profil_description);
        txtProfilRegistration = itemView.findViewById(R.id.registration_date);
        txtProfilIdCarte = itemView.findViewById(R.id.home_idCarte);
        active = itemView.findViewById(R.id.ActivePersone);
    }
    public void setItemClickListner (ItemClickListner listner){
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(), false);
    }
}
