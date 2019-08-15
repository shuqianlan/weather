package com.media;

import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.HttpDataSource;

import java.io.IOException;

public class PlayStateListener implements Player.EventListener {
    public static final String TAG = "PlayStateListener";

    @Override
    public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    /*
    * playbackState:
    * Player.STATE_IDLE 初始化态(STOPPED or FAILED)
    * Player.STATE_BUFFERING:正在缓冲
    * Player.STATE_READY:可以从当前位置播放
    * Player.STATE_ENDED: 播放完毕
    * */
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        Log.d(TAG, "onPlayerStateChanged: playWhenReady " + playWhenReady);
        Log.d(TAG, "onPlayerStateChanged: playbackState " + playbackState);
        
        if (playbackState == Player.STATE_IDLE) {
            Log.d(TAG, "onPlayerStateChanged: 初始化");
        } else if (playbackState == Player.STATE_BUFFERING) {
            Log.d(TAG, "onPlayerStateChanged: 缓冲");
        } else if (playWhenReady && playbackState == Player.STATE_READY) {
            Log.d(TAG, "onPlayerStateChanged: 可播放");
        } else if (playbackState == Player.STATE_ENDED) {
            Log.d(TAG, "onPlayerStateChanged: 完毕");
        }
    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

    }

    /*
    * 错误放生抛出在IDLE之前
    * error.type:
    * ExoPlaybackException.TYPE_SOURCE
    * */
    @Override
    public void onPlayerError(ExoPlaybackException error) {
        Log.d(TAG, "onPlayerError: " + error.getMessage());
        
        if (error.type == ExoPlaybackException.TYPE_SOURCE) {
            IOException cause = error.getSourceException();
            if (cause instanceof HttpDataSource.HttpDataSourceException) {
                // An HTTP error occurred.
                HttpDataSource.HttpDataSourceException httpError = (HttpDataSource.HttpDataSourceException) cause;
                // This is the request for which the error occurred.
                DataSpec requestDataSpec = httpError.dataSpec;
                // It's possible to find out more about the error both by casting and by
                // querying the cause.
                if (httpError instanceof HttpDataSource.InvalidResponseCodeException) {
                    // Cast to InvalidResponseCodeException and retrieve the response code,
                    // message and headers.
                } else {
                    // Try calling httpError.getCause() to retrieve the underlying cause,
                    // although note that it may be null.
                }
            }
        }    }

    @Override
    public void onPositionDiscontinuity(int reason) {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSeekProcessed() {

    }
}
