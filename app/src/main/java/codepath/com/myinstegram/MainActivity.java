package codepath.com.myinstegram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private EditText etDescription;
    private ImageView ivPostImage;
    private ImageView ivHome;
    private ImageView ivCaptureImage;
    private ImageView ivPost;
    private ImageView ivLogout;

    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    private File photoFile;
    public post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDescription = findViewById(R.id.description);
        ivPostImage = findViewById(R.id.ivPostImage);
        ivHome = findViewById(R.id.ivHome);
        ivCaptureImage = findViewById(R.id.ivCaptureImage);
        ivPost = findViewById(R.id.ivPost);
        ivLogout = findViewById(R.id.ivLogout);


        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goPostActivity();
            }
        });

        ivCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });
        


        ivPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = etDescription.getText().toString();
                ParseUser user = ParseUser.getCurrentUser();
                if(photoFile == null || ivPostImage.getDrawable() == null){
                    Log.i(TAG , "No image to submit");
                    Toast.makeText(MainActivity.this, "There is no photo", Toast.LENGTH_SHORT).show();
                    return;
                }
                savePost(description, user, photoFile);

            }
        });


        ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                Toast.makeText(MainActivity.this, "You Logged out", Toast.LENGTH_SHORT).show();
                goBackToLoginActivity();
            }
        });
    }

    private void goPostActivity(){
        Intent i = new Intent(this, PostActivity.class);
        startActivity(i);
        queryPosts();
    }

    private void goBackToLoginActivity() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }


    private void launchCamera() {

        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(MainActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivPostImage.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    private void savePost(String description, ParseUser user, File photoFile) {
        post = new post();
        post.setDescription(description);
        post.setImage(new ParseFile(photoFile));
        post.setUser(user);

        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                String id = post.getObjectId();
                if(e != null){
                    Log.e(TAG, "there is error saving the item");
                    e.printStackTrace();
                    return;
                }

                Log.d(TAG, "success");
                etDescription.setText("");
                ivPostImage.setImageResource(0);

            }
        });
    }

    private void queryPosts() {
        ParseQuery<post> postQuery = new ParseQuery<post>(post.class);
        postQuery.include(post.KEY_USER);
        postQuery.findInBackground(new FindCallback<post>() {
            @Override
            public void done(List<post> posts, ParseException e) {

                if(e != null){
                    Log.e(TAG, "error with Query");
                    e.printStackTrace();
                    return;
                }

                String ob = post.getObjectId();
                String des = post.getDescription();





            }
        });
    }
}
