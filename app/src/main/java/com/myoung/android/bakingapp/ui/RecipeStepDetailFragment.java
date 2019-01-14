package com.myoung.android.bakingapp.ui;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.myoung.android.bakingapp.R;
import com.myoung.android.bakingapp.data.StepItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailFragment extends Fragment {

    // TAG for logging
    private static final String TAG = RecipeDetailFragment.class.getSimpleName();

    // Constants
    private static final String KEY_VIDEO_PLAY_POSITION = "video_play_position";

    // View
    @BindView(R.id.pv_video) PlayerView playerViewVideo;
    @BindView(R.id.tv_step_description) TextView textViewDescription;
    @BindView(R.id.layout_step_description) LinearLayout layoutDescription;

    // Variables
    private StepItem stepItem;
    private SimpleExoPlayer player;
    private DefaultTrackSelector trackSelector;
    private long videoPlayPosition;
    private boolean playWhenReady;

    // Constructor
    public RecipeStepDetailFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        initFragment(rootView, savedInstanceState);
        return rootView;
    }

    private void initFragment(View rootView, Bundle savedInstanceState) {
        ButterKnife.bind(this, rootView);

        if(savedInstanceState != null) {
            videoPlayPosition = savedInstanceState.getLong(KEY_VIDEO_PLAY_POSITION);
        }

        if(stepItem != null) {
            setupVideo(stepItem);
            setData(stepItem);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_VIDEO_PLAY_POSITION, videoPlayPosition);
    }

    private void setData(StepItem stepItem) {
        String description = stepItem.getDescription();

        textViewDescription.setText(description);
    }

    private void hideUI() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if(activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().hide();

            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_IMMERSIVE
            );
        }
    }

    private void setupVideo(StepItem stepItem) {
        String videoURL = stepItem.getVideoURL();

        if(videoURL==null || videoURL.equals("")) {
            playerViewVideo.setVisibility(View.GONE);
            return;
        }

        boolean isTablet = getResources().getBoolean(R.bool.is_tablet);
        if(!isTablet && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            playerViewVideo.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            playerViewVideo.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            hideUI();
        }


        // 1. Create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create a default LoadControl
        LoadControl loadControl = new DefaultLoadControl();

        // 3. Create the player
        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        playerViewVideo.setPlayer(player);
//        playerViewVideo.setKeepScreenOn(true);

        // 4. Produce DataSource
        Uri videoUri = Uri.parse(videoURL);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "Baking Time"));

        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(videoUri);

        player.prepare(videoSource);
        player.setPlayWhenReady(true);
    }

    public void setStepItem(StepItem stepItem) {
        this.stepItem = stepItem;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(player != null) {
            videoPlayPosition = player.getCurrentPosition();
            playWhenReady = player.getPlayWhenReady();
            player.stop();
            player.release();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(player != null) {
            player.seekTo(videoPlayPosition);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(player != null) {
            player.release();
        }
    }

}
