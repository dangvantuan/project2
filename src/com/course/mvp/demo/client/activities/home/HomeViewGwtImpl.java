package com.course.mvp.demo.client.activities.home;

import java.util.List;

import com.course.mvp.demo.client.activities.sokoban.BHTouchImage;
import com.course.mvp.demo.client.activities.sokoban.dataresources.DataResources;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;
import com.googlecode.mgwt.ui.client.dialog.Dialogs.OptionCallback;
import com.googlecode.mgwt.ui.client.dialog.Dialogs.OptionsDialogEntry;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.googlecode.mgwt.ui.client.widget.buttonbar.ButtonBar;
import com.googlecode.mgwt.ui.client.widget.buttonbar.ButtonBarSpacer;
import com.googlecode.mgwt.ui.client.widget.buttonbar.StopButton;

public class HomeViewGwtImpl implements IsWidget {

	LayoutPanel main = new LayoutPanel();
	WidgetList widgetList = new WidgetList();
	HeaderPanel headerPanel = new HeaderPanel();
	HTML sokoban = new HTML("Sokoban");
	ScrollPanel scrollPanel = new ScrollPanel();
	Image mario;
	BHTouchImage menu;
	private ButtonBar footerPanel;

	final DataResources res = DataResources.IMPL;

	public HomeViewGwtImpl() {
		headerPanel.setCenterWidget(sokoban);
		main.add(headerPanel);
		main.add(scrollPanel);

		mario = new Image(res.mario());
		mario.setSize(Window.getClientWidth() + "px", Window.getClientHeight()
				- 2 * 49 + "px");
		scrollPanel.setWidget(mario);
		scrollPanel.setScrollingEnabledX(false);

		footerPanel = new ButtonBar();
		footerPanel.add(new ButtonBarSpacer());
		menu = new BHTouchImage(res.menu());
		menu.addStyleName(new StopButton().getStyleName());
		menu.addStyleName("menucss");
		footerPanel.add(menu);
		footerPanel.add(new ButtonBarSpacer());
		main.add(footerPanel);

	}

	public Image getMario() {
		return mario;
	}

	public BHTouchImage getMenuButton() {
		return this.menu;
	}

	@Override
	public Widget asWidget() {
		return main;
	}

	public void showSomeOptions(List<OptionsDialogEntry> optionText,
			OptionCallback callback) {
		Dialogs.options(optionText, callback, main);

	}

}