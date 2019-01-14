package com.myoung.android.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class StepItem implements Parcelable {
    // Constants
    private static final String JSON_ID = "id";
    private static final String JSON_SHORT_DESCRIPTION = "shortDescription";
    private static final String JSON_DESCRIPTION = "description";
    private static final String JSON_VIDEO_URL = "videoURL";
    private static final String JSON_THUMBNAIL_URL = "thumbnailURL";


    // Variables
    @SerializedName(JSON_ID)
    private String id;
    @SerializedName(JSON_SHORT_DESCRIPTION)
    private String shortDescription;
    @SerializedName(JSON_DESCRIPTION)
    private String description;
    @SerializedName(JSON_VIDEO_URL)
    private String videoURL;
    @SerializedName(JSON_THUMBNAIL_URL)
    private String thumbnailURL;


    protected StepItem(Parcel in) {
        id = in.readString();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<StepItem> CREATOR = new Creator<StepItem>() {
        @Override
        public StepItem createFromParcel(Parcel in) {
            return new StepItem(in);
        }

        @Override
        public StepItem[] newArray(int size) {
            return new StepItem[size];
        }
    };

    // Getter and Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }
}
