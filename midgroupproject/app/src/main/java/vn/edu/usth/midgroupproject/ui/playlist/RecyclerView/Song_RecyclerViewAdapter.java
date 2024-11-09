package vn.edu.usth.midgroupproject.ui.playlist.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import vn.edu.usth.midgroupproject.R;
import vn.edu.usth.midgroupproject.models.SongModel;

public class Song_RecyclerViewAdapter extends RecyclerView.Adapter<Song_RecyclerViewAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<SongModel> songModels;

    public Song_RecyclerViewAdapter(Context context, ArrayList<SongModel> songModels,
                                    RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.songModels = songModels;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout (Giving a look to the rows)
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_song_list_view, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SongModel song = songModels.get(position);

        // Log to check the song details for debugging
        Log.d("Song Info", "Title: " + song.getSongTitle() + ", Artist: " + song.getSongArtist() + ", Image URL: " + song.getSongImage());

        holder.tvTitle.setText(song.getSongTitle());
        holder.tvArtist.setText(song.getSongArtist());

        // Load image using Glide
        Glide.with(context)
                .load("https://androidmusicplayer-be.vercel.app" + songModels.get(position).getSongImage()) // Append the base URL to the image path
                .into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        // Return the size of the song list
        return songModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // Declare view components
        ImageView imageView;
        TextView tvTitle, tvArtist;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            // Initialize the views
            imageView = itemView.findViewById(R.id.song_image);
            tvTitle = itemView.findViewById(R.id.song_title);
            tvArtist = itemView.findViewById(R.id.song_artist);

            // Handling item click
            itemView.setOnClickListener(view -> {
                if (recyclerViewInterface != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(pos);  // Notify the interface
                    }
                }
            });
        }
    }
}
