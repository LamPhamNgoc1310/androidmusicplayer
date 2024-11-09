package vn.edu.usth.midgroupproject.models;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

public class SongModel implements Parcelable {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String songTitle;

    @SerializedName("artist")
    private String songArtist;

    @SerializedName("image")
    private String songImage;

    @SerializedName("mp3Url")
    private String mp3Url;

    // Constructor, Getters, and Setters
    // Parcelable implementation
    protected SongModel(Parcel in) {
        id = in.readInt();
        songTitle = in.readString();
        songArtist = in.readString();
        songImage = in.readString();
        mp3Url = in.readString();
    }

    public static final Creator<SongModel> CREATOR = new Creator<SongModel>() {
        @Override
        public SongModel createFromParcel(Parcel in) {
            return new SongModel(in);
        }

        @Override
        public SongModel[] newArray(int size) {
            return new SongModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(songTitle);
        dest.writeString(songArtist);
        dest.writeString(songImage);
        dest.writeString(mp3Url);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongImage() {
        return songImage;
    }

    public void setSongImage(String songImage) {
        this.songImage = songImage;
    }

    public String getMp3Url() {
        return mp3Url;
    }

    public void setMp3Url(String mp3Url) {
        this.mp3Url = mp3Url;
    }
}
