package com.services;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;

import com.ilifesmart.ToolsApplication;

import java.io.FileDescriptor;

public class MediaPlayerMgmt {

	private static MediaPlayerMgmt sInstance;
	private MediaPlayer player;

	public static MediaPlayerMgmt getInstance() {
		if (sInstance == null) {
			synchronized (MediaPlayer.class.getSimpleName()) {
				if (sInstance == null) {
					sInstance = new MediaPlayerMgmt();
				}
			}
		}

		return sInstance;
	}

	private MediaPlayerMgmt() {
		player = new MediaPlayer();
		player.setOnPreparedListener(mp -> {
			player.start();
		});
	}

	public void playAssetSoundFile(String sound, int loops) {
		if (TextUtils.isEmpty(sound))
			return ;

		if (player == null)
			return ;

		try {
			if (player.isPlaying()) {
				player.stop();
			}

			AssetFileDescriptor afd = ToolsApplication.getContext().getAssets().openFd(sound);
			FileDescriptor fd = afd.getFileDescriptor();
			player.reset();
			player.setDataSource(fd, afd.getStartOffset(), afd.getLength());
			player.setAudioStreamType(AudioManager.STREAM_MUSIC);
			player.prepareAsync();
			player.setLooping(loops < 0);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public void stopPlayAudio() {
		if (player == null) {
			return ;
		}
		
		if (player.isPlaying()) {
			player.pause();
		}
	}

	/* MediaPlayer资源需要释放.*/
	public void releaseMedia() {
		if (player == null) {
			return;
		}
		stopPlayAudio();

		player.release();
		player = null;
		sInstance = null;
	}
}
