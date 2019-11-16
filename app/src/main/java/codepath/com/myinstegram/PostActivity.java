package codepath.com.myinstegram;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class PostActivity extends AppCompatActivity {

    private TextView tvDescription;
    private ImageView ivPostedPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        tvDescription = findViewById(R.id.tvDescription);
        ivPostedPicture = findViewById(R.id.ivPostedPicture);
        queryPosts();

    }


    private void queryPosts() {
        String objectId = getIntent().getStringExtra("id");
        ParseQuery<post> postQuery = new ParseQuery<post>(post.class);
        postQuery.include(post.KEY_USER);
        postQuery.findInBackground(new FindCallback<post>() {
            @Override
            public void done(List<post> posts, ParseException e) {

                if(e != null){
                    Log.e("post activity", "error with Query");
                    e.printStackTrace();
                    return;
                }
                for (int i = 0 ; i < posts.size() ; i++)
                    Log.d("value is" , posts.get(i).toString());
                post lastPost = posts.get(posts.size()-1);
                Log.i("information", lastPost.getDescription());
                tvDescription.setText(lastPost.getDescription());
                lastPost.getImage().getDataInBackground(new GetDataCallback() {
                            public void done(byte[] data,
                                             ParseException e) {
                                if (e == null) {
                                    // Decode the Byte[] into
                                    // Bitmap
                                    Bitmap bmp = BitmapFactory
                                            .decodeByteArray(
                                                    data, 0,
                                                    data.length);
                                    // ImageView
                                    ivPostedPicture.setImageBitmap(bmp);

                                } else {
                                    Log.d("test",
                                            "Problem load image the data.");
                                }
                            }
                        });
            }
        });
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}


