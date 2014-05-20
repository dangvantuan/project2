package com.course.mvp.demo.client.activities.sokoban;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SokobanPlace extends Place {
	String token = "";

	public SokobanPlace() {
		super();
		token = "Sokoban";
	}

	public String getToken() {
		return this.token;
	}

	public static class Tokenizer implements PlaceTokenizer<SokobanPlace> {
		@Override
		public String getToken(SokobanPlace place) {
			return place.getToken();
		}

		@Override
		public SokobanPlace getPlace(String token) {
			return new SokobanPlace();
		}
	}
}
