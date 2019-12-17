package mobildev.iosm.com.priot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SittingActivity extends AppCompatActivity {
    public ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitting2);

        imageView = findViewById(R.id.image_setting);
    }
}
