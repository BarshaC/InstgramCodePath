package com.example.instagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private Context context;
    private List<Post> posts;

    public PostsAdapter(Context context,List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.bind(post);
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvUsername;
        private ImageView ivImage;
        private TextView tvDescription;
        private TextView tvTime;
        private ImageView displayPicture;
        private ImageButton ibLike;
        private ImageButton ibComment;
        private List<String> likedBy;
        private TextView tvlikesCounter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvTime = itemView.findViewById(R.id.tvTime);
            displayPicture = itemView.findViewById(R.id.profilePicture);
            ibLike = itemView.findViewById(R.id.ibLike);
            ibComment = itemView.findViewById(R.id.ibComment);
            tvlikesCounter = itemView.findViewById(R.id.tvlikesCounter);

        }

        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            tvTime.setText(post.getCreatedAt().toString());
            ParseFile profilePicture = post.getUser().getProfileImage();
            if (profilePicture != null) {
                Glide.with(context).load(profilePicture.getUrl())
                        .circleCrop()
                        .into(displayPicture);

            }
            ParseFile image = post.getImage();
            if (image != null) {
                Glide.with(context).load(image.getUrl()).into(ivImage);
            }
            ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context,PostDetailsActivity.class);
                    i.putExtra("details", Parcels.wrap(post));
                    context.startActivity(i);
                }
            });
            ibLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    likedBy = post.getLikedBy();
                    if (likedBy.contains(ParseUser.getCurrentUser().getObjectId())){
                        likedBy.remove(ParseUser.getCurrentUser().getObjectId());
                        post.setKeyLikedBy(likedBy);
                        Drawable newimage = context.getDrawable(R.drawable.ic_heart);
                        ibLike.setImageDrawable(newimage);
                    }
                    else {
                        likedBy.add(ParseUser.getCurrentUser().getObjectId());
                        post.setKeyLikedBy(likedBy);
                        Drawable newimage = context.getDrawable(R.drawable.ic_ufi_heart_active);
                        ibLike.setImageDrawable(newimage);
                    }
                    post.saveInBackground();
                    tvlikesCounter.setText(post.likeCountDisplayText());
                }
            });
            ibComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }


        public void clear() {
            posts.clear();
            notifyDataSetChanged();
        }
        public void addAll(List<Post> list) {
            posts.addAll(list);
            notifyDataSetChanged();
        }
    }
}
