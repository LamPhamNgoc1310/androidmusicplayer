package vn.edu.usth.midgroupproject.ui.song_player;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.io.IOException;
import java.util.ArrayList;
import vn.edu.usth.midgroupproject.R;
import vn.edu.usth.midgroupproject.models.SongModel;

public class SongActivity extends AppCompatActivity {

    private ArrayList<SongModel> songList;
    private int currentPosition;
    private MediaPlayer mediaPlayer;
    private ImageView playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        songList = getIntent().getParcelableArrayListExtra("SONG_LIST");
        currentPosition = getIntent().getIntExtra("CURRENT_POSITION", 0);

        playButton = findViewById(R.id.play_button);
        setupUI();

        findViewById(R.id.previous_button).setOnClickListener(v -> playPreviousSong());
        findViewById(R.id.next_button).setOnClickListener(v -> playNextSong());

        playButton.setOnClickListener(v -> togglePlayPause());
    }

    private void setupUI() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        SongModel currentSong = songList.get(currentPosition);

        TextView tvTitle = findViewById(R.id.song_title_fragment);
        TextView tvArtist = findViewById(R.id.song_artist_fragment);
        ImageView imageView = findViewById(R.id.song_image_fragment);

        tvTitle.setText(currentSong.getSongTitle());
        tvArtist.setText(currentSong.getSongArtist());

        Glide.with(this)
                .load("https://androidmusicplayer-be.vercel.app" + currentSong.getSongImage())
                .into(imageView);

        setupMediaPlayer("https://androidmusicplayer-be.vercel.app" + currentSong.getMp3Url());
    }

    private void setupMediaPlayer(String mp3Url) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build());

        try {
            mediaPlayer.setDataSource(mp3Url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> mediaPlayer.start());
            mediaPlayer.setOnCompletionListener(mp -> {
                playButton.setImageResource(R.drawable.playbutton);
                mediaPlayer.seekTo(0);
            });
        } catch (IOException e) {
            Toast.makeText(this, "Error loading song", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void togglePlayPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playButton.setImageResource(R.drawable.playbutton);
        } else {
            mediaPlayer.start();
            playButton.setImageResource(R.drawable.pausebutton);
        }
    }

    private void playNextSong() {
        if (currentPosition < songList.size() - 1) {
            currentPosition++;
            setupUI();
        } else {
            Toast.makeText(this, "End of playlist", Toast.LENGTH_SHORT).show();
        }
    }

    private void playPreviousSong() {
        if (currentPosition > 0) {
            currentPosition--;
            setupUI();
        } else {
            Toast.makeText(this, "Start of playlist", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
