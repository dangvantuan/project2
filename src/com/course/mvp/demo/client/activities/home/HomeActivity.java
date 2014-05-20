package com.course.mvp.demo.client.activities.home;

import java.util.ArrayList;
import java.util.List;

import com.course.mvp.demo.client.activities.ClientFactoryImpl;
import com.course.mvp.demo.client.activities.sokoban.SokobanPlace;
import com.course.mvp.demo.client.activities.sokoban.dataresources.DataResources;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;
import com.googlecode.mgwt.ui.client.dialog.Dialogs.ButtonType;
import com.googlecode.mgwt.ui.client.dialog.Dialogs.OptionCallback;
import com.googlecode.mgwt.ui.client.dialog.Dialogs.OptionsDialogEntry;

public class HomeActivity extends MGWTAbstractActivity {

	private final ClientFactoryImpl clientFactory;
	private Place place;
	private HomeViewGwtImpl view;

	final DataResources res = DataResources.IMPL;

	public HomeActivity(ClientFactoryImpl clientFactory, Place place) {
		this.clientFactory = clientFactory;
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = new HomeViewGwtImpl();
		panel.setWidget(view);

		Window.addResizeHandler(new ResizeHandler() {

			@Override
			public void onResize(ResizeEvent event) {
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						view.getMario().setSize(Window.getClientWidth() + "px",
								Window.getClientHeight() - 49 + "px");
					}
				});

			}
		});

		view.getMenuButton().addTapHandler(new TapHandler() {
			@Override
			public void onTap(TapEvent event) {
				// clientFactory.getPlaceController().goTo(new HomePlace());
				List<OptionsDialogEntry> list = new ArrayList<OptionsDialogEntry>();
				list.add(new OptionsDialogEntry("New Game", ButtonType.CONFIRM));
				list.add(new OptionsDialogEntry("Intructions",
						ButtonType.NORMAL));
				list.add(new OptionsDialogEntry("About", ButtonType.NORMAL));
				list.add(new OptionsDialogEntry("Exit", ButtonType.IMPORTANT));

				view.showSomeOptions(list, new OptionCallback() {

					@Override
					public void onOptionSelected(int index) {
						if (index == 1) {
							clientFactory.getPlaceController().goTo(
									new SokobanPlace());
						} else if (index == 2) {
							Dialogs.alert(
									"Instructions",
									"There are 90 level that You need to move boxs to appropriate points on the maps !",
									null);
						} else if (index == 3) {
							Dialogs.alert(
									"About",
									"Contact dangtuan7193@gmail.com for more infomation !",
									null);
						} else if (index == 4) {
						}
					}
				});
			}
		});
	}
}