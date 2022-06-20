package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class PostDetailsActivity extends AppCompatActivity {
    private static final String TAG = "PostDetailsActivity";
    private TextView tvDescription;
    private TextView tvUsername;
    private ImageView ivImage;
    private TextView postTimeStamp;
    private ImageView displayPicture;
    private ImageButton ibLike;
    private List<String> likedBy;
    private TextView tvlikesCounter;
    private Post post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        Log.i(TAG, "Check for detail activity");

        post = Parcels.unwrap(getIntent().getParcelableExtra("details"));

        tvDescription = findViewById(R.id.tvDescription);
        tvUsername = findViewById(R.id.tvUsername);
        displayPicture = findViewById(R.id.profilePicture);
        postTimeStamp = findViewById(R.id.tvTimeStamp);
        ivImage = findViewById(R.id.ivImage);
        ibLike = findViewById(R.id.ibLike);
        tvlikesCounter = findViewById(R.id.tvlikesCounter);
        tvDescription.setText(post.getDescription());
        tvUsername.setText(post.getUser().getUsername());
        postTimeStamp.setText(post.getCreatedAt().toString());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this).load(image.getUrl()).into(ivImage);
        }
        ParseFile profilePicture = post.getUser().getProfileImage();
        if (profilePicture != null) {
            Glide.with(this).load(profilePicture.getUrl())
                    .circleCrop()
                    .into(displayPicture);
        }
        ibLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likedBy = post.getLikedBy();
                if (likedBy.contains(ParseUser.getCurrentUser().getObjectId())) {
                    likedBy.remove(ParseUser.getCurrentUser().getObjectId());
                    post.setKeyLikedBy(likedBy);
                    Log.d(TAG, "liked");
                    Drawable newimage = PostDetailsActivity.this.getDrawable(R.drawable.ic_heart);
                    ibLike.setImageDrawable(newimage);
                } else {
                    likedBy.add(ParseUser.getCurrentUser().getObjectId());
                    post.setKeyLikedBy(likedBy);
                    Log.d(TAG, "liked");
                    Drawable newimage = PostDetailsActivity.this.getDrawable(R.drawable.ic_ufi_heart_active);
                    ibLike.setImageDrawable(newimage);
                }
                post.saveInBackground();
                tvlikesCounter.setText(post.likeCountDisplayText());
            }
        });
    }

}
