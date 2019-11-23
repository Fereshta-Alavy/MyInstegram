package codepath.com.myinstegram;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import codepath.com.myinstegram.fragments.ComposeFragment;
import codepath.com.myinstegram.fragments.MovieFragment;
import codepath.com.myinstegram.fragments.PostFragment;
import codepath.com.myinstegram.fragments.ProfileFragment;


public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private BottomNavigationView bottomNavigationView;

    final FragmentManager fragmentManager = getSupportFragmentManager();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = new PostFragment();
                        break;

                    case R.id.action_profile:
                        fragment = new ProfileFragment();
                        break;
                    case R.id.action_movie:
                        fragment = new MovieFragment();
                        break;
                    case R.id.action_camera:
                        fragment = new ComposeFragment();
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.action_home);

    }




}
