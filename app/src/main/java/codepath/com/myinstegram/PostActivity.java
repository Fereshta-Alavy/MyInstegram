package codepath.com.myinstegram;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PostActivity extends AppCompatActivity {

    private TextView tvDescription;
    private ImageView ivPostedPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        tvDescription = findViewById(R.id.tvDescription);
        ivPostedPicture = findViewById(R.id.ivPostedPicture);


    }



    }


