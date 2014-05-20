package com.course.mvp.demo.client.activities.home;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class HomePlace extends Place {
	String token = "";

	public HomePlace() {
		super();
		token = "home";
	}

	public String getToken() {
		return this.token;
	}

	public static class Tokenizer implements PlaceTokenizer<HomePlace> {
		@Override
		public String getToken(HomePlace place) {
			return place.getToken();
		}

		@Override
		public HomePlace getPlace(String token) {
			return new HomePlace();
		}
	}
}
