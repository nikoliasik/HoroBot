package com.winter.horobot.util.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import sx.blah.discord.handle.audio.AudioEncodingType;
import sx.blah.discord.handle.audio.IAudioProvider;

public class AudioProvider implements IAudioProvider {

	private final AudioPlayer audioPlayer;
	private AudioFrame lastFrame;

	public AudioProvider(AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
	}

	public boolean isReady() {
		if (lastFrame == null) {
			lastFrame = audioPlayer.provide();
		}
		return lastFrame != null;
	}

	public byte[] provide() {
		if(lastFrame == null) {
			lastFrame = audioPlayer.provide();
		}

		byte[] data = lastFrame != null ? lastFrame.data : null;
		lastFrame = null;

		return data;
	}

	public int getChannels() {
		return 2;
	}

	public AudioEncodingType getAudioEncodingType() {
		return AudioEncodingType.OPUS;
	}
}