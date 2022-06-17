package com.example.instagram;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import org.parceler.Parcels;

public class PostDetailsActivity extends AppCompatActivity {
    private static final String TAG = "PostDetailsActivity";
    private TextView postDescription;
    private TextView postUsername;
    private ImageView profileImage;
    private TextView postTimeStamp;
    private ImageView detailPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        Log.i(TAG, "Check for detail activity");

        Post show = Parcels.unwrap(getIntent().getParcelableExtra("details"));

        postDescription = findViewById(R.id.postDescription);
        postUsername = findViewById(R.id.postUsername);
        profileImage = findViewById(R.id.profilePicture);
        postTimeStamp = findViewById(R.id.postTimeStamp);
        detailPhoto = findViewById(R.id.detailPhoto);
        bind(show);
    }


    public void bind(Post post) {
        // Bind the post data to the view elements
        postDescription.setText(post.getDescription());
        postUsername.setText(post.getUser().getUsername());
        postTimeStamp.setText(post.getCreatedAt().toString());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(detailPhoto);
        }
        ParseFile profilePicture = post.getUser().getProfileImage();
        if (profilePicture != null) {
            Glide.with(this).load(profilePicture.getUrl())
                    .circleCrop()
                    //.transform(new RoundedCorners(30))
                    .into(profileImage);
        }
    }
}