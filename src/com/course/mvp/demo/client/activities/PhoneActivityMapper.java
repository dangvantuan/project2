package com.course.mvp.demo.client.activities;

import com.course.mvp.demo.client.activities.home.HomeActivity;
import com.course.mvp.demo.client.activities.home.HomePlace;
import com.course.mvp.demo.client.activities.sokoban.SokobanActivity;
import com.course.mvp.demo.client.activities.sokoban.SokobanPlace;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

public class PhoneActivityMapper implements ActivityMapper {

	private ClientFactoryImpl clientFactory;

	public PhoneActivityMapper(ClientFactoryImpl clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof HomePlace)
			return new HomeActivity(clientFactory, place);
		else if (place instanceof SokobanPlace)
			return new SokobanActivity(clientFactory, place);

		return null;
	}
}
