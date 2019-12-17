package mobildev.iosm.com.priot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;
import mobildev.iosm.com.priot.Model.Users;
import mobildev.iosm.com.priot.PreValent.PreValent;

public class loginActivity extends AppCompatActivity {

    private EditText inputUsername,inputPassword;
    Button loginBtn;
    ProgressDialog LoadingBar;
    String parentDbName="Admins";
    private CheckBox checkBoxRemembreMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn =findViewById(R.id.login_btn);
        inputPassword=findViewById(R.id.login_password_input);
        inputUsername=findViewById(R.id.login_username_input);

        checkBoxRemembreMe=findViewById(R.id.remember_me_chk);
        Paper.init(this);


        LoadingBar = new ProgressDialog(loginActivity.this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
         //       Toast.makeText(loginActivity.this,"You Are Clicked Login Button",Toast.LENGTH_SHORT).show();
                loginUsers();
            }
        });
    }

    private void loginUsers()
    {
        String password=inputPassword.getText().toString();
        String username=inputUsername.getText().toString();

        if(TextUtils.isEmpty(username)){
            Toast.makeText(loginActivity.this,"Please write your Phone Numbre",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(loginActivity.this,"Please write your Password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            LoadingBar.setTitle("Login Account");
            LoadingBar.setMessage("Please Wait , While We Are Checking  The Credentials.");
            LoadingBar.setCanceledOnTouchOutside(false);
            LoadingBar.show();

            AlowAccessToAccount(username,password);
        }
    }


    private void AlowAccessToAccount(final String username, final String password)
    {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {                                       //unique is the phone
                if(dataSnapshot.child("Admin").child(username).exists())
                {
                    //Toast.makeText(loginActivity.this, "username existe ", Toast.LENGTH_SHORT).show();

                    Users userData = dataSnapshot.child("Admin").child(username).getValue(Users.class);//recupere les donnéer saisir
                    if(userData.getUsername().equals(username)){
                        if(userData.getPassword().equals(password))
                        {
                                Toast.makeText(loginActivity.this, "welcome Admin You Are, Logged in Successfully", Toast.LENGTH_SHORT).show();
                                LoadingBar.dismiss();

                                Intent myIntent = new Intent(loginActivity.this,HomeActivity.class);
                                PreValent.CurrentOnLineUser = userData;//enregistrer les information du user
                                startActivity(myIntent);

                        }
                        else
                        {
                            LoadingBar.dismiss();
                            Toast.makeText(loginActivity.this, "Password Is Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(loginActivity.this, "Account With This Phone Number ( "+username+" ) Not Exists", Toast.LENGTH_SHORT).show();
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