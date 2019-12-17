package mobildev.iosm.com.priot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import mobildev.iosm.com.priot.Model.Presence;
import mobildev.iosm.com.priot.PreValent.PreValent;
import mobildev.iosm.com.priot.ViewHolder.PresenceViewHolder;

public class PresentActivity2 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference PresenceRef,durePreceRef;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Query query, queryEntre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_present2);
        PresenceRef = FirebaseDatabase.getInstance().getReference().child("Presence");
        durePreceRef = FirebaseDatabase.getInstance().getReference().child("DurePresence");

        query= PresenceRef.orderByChild("entreSortie").equalTo("1");
       queryEntre=durePreceRef.orderByChild("dateEntre").equalTo("");


        Paper.init(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarpresence);
        toolbar.setTitle("Presence");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutPresence);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewPresence);
        navigationView.setNavigationItemSelectedListener(this);

        //ici intialiser notre headerView
        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_presence_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_presence_image);

        //recupperer le nom et le mette dans l'en-tete
        userNameTextView.setText(PreValent.CurrentOnLineUser.getUsername());
        ///Toast.makeText(PresentActivity2.this,"PresentActivity2 username :"+PreValent.CurrentOnLineUser.getUsername(),Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recycler_menupre);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }


    public void onStart() {
        super.onStart();


                FirebaseRecyclerOptions<Presence> options = new FirebaseRecyclerOptions.Builder<Presence>().setQuery(query, Presence.class).build();
                FirebaseRecyclerAdapter<Presence, PresenceViewHolder> Adapter = new FirebaseRecyclerAdapter<Presence, PresenceViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull PresenceViewHolder holder, int position, @NonNull final Presence model) {


                        holder.txtProfilName.setText(model.getNom());
                        holder.txtProfilLastName.setText(model.getPrenom());
                        holder.txtIdCarte.setText("Id Carte  : " + model.getId_Carte());

                        holder.txtEntryTime.setText("Date d'Entré: " + model.getDateEntre());
                        Picasso.get().load(model.getImage()).into(holder.imageView);

                    }

                    @NonNull
                    @Override
                    public PresenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.presences_items_layout, parent, false);
                        PresenceViewHolder holder = new PresenceViewHolder(view);


                        return holder;
                    }
                };

                recyclerView.setAdapter(Adapter);
                Adapter.startListening();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutPresence);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.present_activity2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home)
        {
            startActivity(new Intent(PresentActivity2.this,HomeActivity.class));
        }
       else if (id == R.id.nav_orders)
        {
            startActivity(new Intent(PresentActivity2.this,PresentActivity2.class));
        }
        else if (id == R.id.nav_categories)
        {

            startActivity(new Intent(PresentActivity2.this,StagairesActivity.class));
        }
        else if (id == R.id.nav_settings)
        {
            startActivity(new Intent(PresentActivity2.this,SittingActivity.class));
        }
        else if (id == R.id.nav_logout)
        {
            Paper.book().destroy();

            Intent intent =new Intent(PresentActivity2.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutPresence);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
