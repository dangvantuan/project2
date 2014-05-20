package com.course.mvp.demo.client.activities.sokoban;

import com.course.mvp.demo.client.activities.sokoban.dataresources.DataResources;
import com.google.gwt.media.client.Audio;

public class Sound {
	private Audio ding;
	private Audio tada;
	private Audio footstep;
	final DataResources res = DataResources.IMPL;

	public Sound() {
		ding = Audio.createIfSupported();
		tada = Audio.createIfSupported();
		footstep = Audio.createIfSupported();

		ding.setSrc(res.ding().getUrl());
		tada.setSrc(res.tada().getUrl());
		footstep.setSrc(res.footstep().getUrl());

	}

	public void playDing() {
		ding.play();
	}

	public void playTada() {
		tada.play();
	}

	public void playFootstep() {
		footstep.play();
	}
}