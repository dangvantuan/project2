package com.course.mvp.demo.client.activities;

import com.course.mvp.demo.client.activities.sokoban.SokobanViewGwtImpl;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class ClientFactoryImpl {

	private SimpleEventBus eventBus;
	private PlaceController placeController;
	private SokobanViewGwtImpl sokobanView = null;

	public ClientFactoryImpl() {
		eventBus = new SimpleEventBus();
		placeController = new PlaceController(eventBus);
	}

	public PlaceController getPlaceController() {
		return placeController;
	}

	public SokobanViewGwtImpl getSokobanView() {
		if (sokobanView == null) {
			sokobanView = new SokobanViewGwtImpl();
		}

		return sokobanView;
	}

	public EventBus getEventBus() {
		return eventBus;
	}

}