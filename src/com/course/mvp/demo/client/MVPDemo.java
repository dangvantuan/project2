package com.course.mvp.demo.client;

import com.course.mvp.demo.client.activities.ClientFactoryImpl;
import com.course.mvp.demo.client.activities.PhoneActivityMapper;
import com.course.mvp.demo.client.activities.PhoneAnimationMapper;
import com.course.mvp.demo.client.activities.home.HomePlace;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.mgwt.mvp.client.AnimatableDisplay;
import com.googlecode.mgwt.mvp.client.AnimatingActivityManager;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MVPDemo implements EntryPoint {
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	public static PhoneGap phoneGap = null;
	public static ClientFactoryImpl clientFactory = GWT
			.create(ClientFactoryImpl.class);
	public static PhoneActivityMapper appActivityMapper;

	public void onModuleLoad() {
		// Window.alert("onModuleLoaded: " + getOSName());
		startApp();

	}

	private void startApp() {
		PlaceHistoryMapper historyMapper = GWT.create(PlaceHistoryMapper.class);
		final PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(
				historyMapper);
		historyHandler.register(clientFactory.getPlaceController(),
				clientFactory.getEventBus(), new HomePlace());
		createPhoneDisplay(clientFactory);
		historyHandler.handleCurrentHistory();
	}

	private void createPhoneDisplay(ClientFactoryImpl clientFactory) {
		AnimatableDisplay display = GWT.create(AnimatableDisplay.class);
		appActivityMapper = new PhoneActivityMapper(clientFactory);
		PhoneAnimationMapper appAnimationMapper = new PhoneAnimationMapper();
		AnimatingActivityManager activityManager = new AnimatingActivityManager(
				appActivityMapper, appAnimationMapper,
				clientFactory.getEventBus());
		activityManager.setDisplay(display);
		RootPanel.get().add(display);
	}
}
