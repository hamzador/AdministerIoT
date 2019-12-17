package mobildev.iosm.com.priot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;
import mobildev.iosm.com.priot.Model.Users;
import mobildev.iosm.com.priot.PreValent.PreValent;

public class MainActivity extends AppCompatActivity {
    private Button loginBtn;
    ProgressDialog LoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginBtn = findViewById(R.id.main_login_btn);

        LoadingBar =new ProgressDialog(this);

        Paper.init(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent =new Intent(MainActivity.this,loginActivity.class);
                startActivity(myIntent);
            }
        });
        String UserNameKey = Paper.book().read(PreValent.UserNameKey);
        String UserPasswordKey = Paper.book().read(PreValent.UserPasswordKey);

        if(UserNameKey!=""&& UserPasswordKey!=""){
            if(!TextUtils.isEmpty(UserPasswordKey) && !TextUtils.isEmpty(UserNameKey)){


                AlowAccess(UserNameKey,UserPasswordKey);

                LoadingBar.setTitle("Already Logged In");
                LoadingBar.setMessage("Please Wait ....");
                LoadingBar.setCanceledOnTouchOutside(false);
                LoadingBar.show();
            }

        }

    }
    private void AlowAccess(final String username, final String password)
    {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {                                       //unique is the phone
                if(dataSnapshot.child("Users").child(username).exists())
                {
                    Users userData=dataSnapshot.child("Users").child(username).getValue(Users.class);
                    if(userData.getUsername().equals(username)){
                        if(userData.getPassword().equals(password))
                        {
                            Toast.makeText(MainActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            LoadingBar.dismiss();

                            Intent myIntent =new Intent(MainActivity.this,HomeActivity.class);
                            PreValent.CurrentOnLineUser = userData;
                            startActivity(myIntent);
                        }
                        else
                        {
                            LoadingBar.dismiss();
                            Toast.makeText(MainActivity.this, "Password Is Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Account With This Phone Number ( "+username+" ) Not Exists", Toast.LENGTH_SHORT).show();
                    LoadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }


}
