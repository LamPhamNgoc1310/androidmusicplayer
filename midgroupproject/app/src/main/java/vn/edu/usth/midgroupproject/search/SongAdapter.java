package vn.edu.usth.midgroupproject.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.usth.midgroupproject.R;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.ViewHolder> {

    private final List<Map<String, String>> searchResults;
    private final OnResultClickListener onResultClickListener;

    public interface OnResultClickListener {
        void onResultClicked(Map<String, String> result);
    }

    public SongAdapter(List<Map<String, String>> searchResults, OnResultClickListener onResultClickListener) {
        this.searchResults = searchResults;
        this.onResultClickListener = onResultClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.songs_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map<String, String> result = searchResults.get(position);
        holder.songName.setText(result.get("song"));
        holder.artistName.setText(result.get("artist"));

        String imageUrl = result.get("image_url");
        if (imageUrl != null && !imageUrl.isEmpty()) {
            // Use an image loading library like Glide or Picasso to load the image
            // Example with Glide
            Glide.with(holder.songIcon.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.song1)
                    .into(holder.songIcon);
        } else {
            holder.songIcon.setImageResource(R.drawable.song1);
        }

        holder.itemView.setOnClickListener(v -> onResultClickListener.onResultClicked(result));
    }

    @Override
    public int getItemCount() {
        return searchResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView songName;
        public TextView artistName;
        public CircleImageView songIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.song_name);
            artistName = itemView.findViewById(R.id.artist_name);
            songIcon = itemView.findViewById(R.id.Songicon);
        }
    }
}