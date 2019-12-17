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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import mobildev.iosm.com.priot.Model.Profil;
import mobildev.iosm.com.priot.Model.Users;
import mobildev.iosm.com.priot.PreValent.PreValent;
import mobildev.iosm.com.priot.ViewHolder.ProfilViewHolder;

public class StagairesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference ProfilRef ;
    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    private Query query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stagaires);

        ProfilRef = FirebaseDatabase.getInstance().getReference().child("Personne");
        query = ProfilRef.orderByChild("type").equalTo("1");
        Paper.init(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarpresenceStagaire);
        toolbar.setTitle("Home Stagaire");
        setSupportActionBar(toolbar);




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutStagaire);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewStagaire);
        navigationView.setNavigationItemSelectedListener(this);

        //ici intialiser notre headerView
        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_presenceStagaire_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_presenceStagaire_image);

        //recupperer le nom et le mette dans l'en-tete
        userNameTextView.setText(PreValent.CurrentOnLineUser.getUsername());

        recyclerView = findViewById(R.id.recycler_menuStagaire);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Profil> options = new FirebaseRecyclerOptions.Builder<Profil>().setQuery(query, Profil.class).build();
        FirebaseRecyclerAdapter<Profil, ProfilViewHolder> Adapter = new FirebaseRecyclerAdapter<Profil, ProfilViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProfilViewHolder holder, int position, @NonNull final Profil model){
                holder.txtProfilName.setText(model.getNom());
                holder.txtProfilLastName.setText(model.getPrenom());
                holder.txtProfilRegistration.setText("Registration Date  = "+model.getDateInscription());
                holder.txtProfilIdCarte.setText("ID Carte : "+model.getId_Carte());
                Picasso.get().load(model.getImage()).into(holder.imageView);
                // Picasso.get().load(model.getImage()).memoryPolicy(MemoryPolicy.NO_CACHE )
                //       .networkPolicy(NetworkPolicy.NO_CACHE).error(R.mipmap.ic_launcher).into(holder.imageView);






                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(StagairesActivity.this, StatisticsActivity.class);
                        intent.putExtra("cid", model.getId_Carte());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ProfilViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profils_items_layout, parent, false);
                ProfilViewHolder holder = new ProfilViewHolder(view);
                return holder;
            }
        };

        recyclerView.setAdapter(Adapter);
        Adapter.startListening();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutStagaire);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stagaires, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
        //   return true;
        // }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home)
        {
            startActivity(new Intent(StagairesActivity.this,HomeActivity.class));
        }
        else
        if (id == R.id.nav_orders) {

            startActivity(new Intent(StagairesActivity.this,PresentActivity2.class));
        }
        else if (id == R.id.nav_categories)
        {


        }
        else if (id == R.id.nav_settings)
        {
            startActivity(new Intent(StagairesActivity.this,SittingActivity.class));
        }
        else if (id == R.id.nav_logout)
        {
            Paper.book().destroy();

            Intent intent =new Intent(StagairesActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutStagaire);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
