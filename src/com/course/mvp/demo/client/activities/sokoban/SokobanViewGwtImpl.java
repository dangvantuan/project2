package com.course.mvp.demo.client.activities.sokoban;

import java.util.List;

import com.course.mvp.demo.client.activities.sokoban.dataresources.DataResources;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.ui.client.MGWTStyle;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;
import com.googlecode.mgwt.ui.client.dialog.Dialogs.OptionCallback;
import com.googlecode.mgwt.ui.client.dialog.Dialogs.OptionsDialogEntry;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;
import com.googlecode.mgwt.ui.client.widget.HeaderPanel;
import com.googlecode.mgwt.ui.client.widget.LayoutPanel;
import com.googlecode.mgwt.ui.client.widget.ScrollPanel;
import com.googlecode.mgwt.ui.client.widget.WidgetList;
import com.googlecode.mgwt.ui.client.widget.buttonbar.ButtonBar;
import com.googlecode.mgwt.ui.client.widget.buttonbar.ButtonBarSpacer;
import com.googlecode.mgwt.ui.client.widget.buttonbar.RefreshButton;
import com.googlecode.mgwt.ui.client.widget.buttonbar.ReplyButton;
import com.googlecode.mgwt.ui.client.widget.buttonbar.StopButton;

public class SokobanViewGwtImpl implements IsWidget {

	LayoutPanel main = new LayoutPanel();
	WidgetList widgetList = new WidgetList();
	HeaderPanel headerPanel = new HeaderPanel();
	ScrollPanel scrollPanel = new ScrollPanel();
	HeaderButton menuButton = new HeaderButton();
	HTML moves = new HTML("Moves: 0");
	HTML time = new HTML("00:00");
	HTML curLevel = new HTML("Level: 1");
	VerticalPanel centerPanel;
	VerticalPanel bottomPanel;
	BHTouchImage up, down, left, right, menu;
	private ButtonBar footerPanel;
	private RefreshButton btnReset = new RefreshButton();
	private ReplyButton btnUndo = new ReplyButton();

	final DataResources res = DataResources.IMPL;

	public SokobanViewGwtImpl() {
		headerPanel.setLeftWidget(moves);
		headerPanel.setCenterWidget(time);
		headerPanel.setRightWidget(curLevel);
		moves.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle()
				.getListCss().listHeader());
		time.addStyleDependentName(MGWTStyle.getTheme().getMGWTClientBundle()
				.getListCss().listHeader());
		curLevel.addStyleName(MGWTStyle.getTheme().getMGWTClientBundle()
				.getListCss().listHeader());
		main.add(headerPanel);
		main.add(scrollPanel);

		FlowPanel container = new FlowPanel();
		scrollPanel.setWidget(container);
		scrollPanel.setScrollingEnabledX(false);

		centerPanel = new VerticalPanel();
		centerPanel.setHeight(Window.getClientHeight() - 4 * 49 - 10 + "px");
		container.add(centerPanel);

		bottomPanel = new VerticalPanel();
		bottomPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		bottomPanel.setStyleName("vPanelBottom");
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);

		HorizontalPanel hPanelUp = new HorizontalPanel();
		up = new BHTouchImage(res.arrow_up());
		up.setStyleName("arrow");

		hPanelUp.add(up);
		vPanel.add(hPanelUp);

		HorizontalPanel hPanel = new HorizontalPanel();
		left = new BHTouchImage(res.arrow_left());
		left.setStyleName("arrow");
		Image middle = new Image(res.middle_32px());
		right = new BHTouchImage(res.arrow_right());
		right.setStyleName("arrow");
		hPanel.add(left);
		hPanel.add(middle);
		hPanel.add(right);
		vPanel.add(hPanel);

		down = new BHTouchImage(res.arrow_down());
		down.setStyleName("arrow");
		vPanel.add(down);
		bottomPanel.add(vPanel);
		container.add(bottomPanel);

		footerPanel = new ButtonBar();
		footerPanel.add(btnReset);
		footerPanel.add(new ButtonBarSpacer());
		menu = new BHTouchImage(res.menu());
		menu.addStyleName(new StopButton().getStyleName());
		menu.addStyleName("menucss");
		footerPanel.add(menu);
		footerPanel.add(new ButtonBarSpacer());
		footerPanel.add(btnUndo);
		main.add(footerPanel);

	}

	public HTML getMoves() {
		return moves;
	}

	public void setMoves(HTML moves) {
		this.moves = moves;
	}

	public HTML getCurLevel() {
		return curLevel;
	}

	public void setCurLevel(HTML curLevel) {
		this.curLevel = curLevel;
	}

	public HTML getTime() {
		return time;
	}

	public void setTime(HTML time) {
		this.time = time;
	}

	public RefreshButton getReset() {
		return btnReset;
	}

	public ReplyButton getUndo() {
		return btnUndo;
	}

	public BHTouchImage getMenuButton() {
		return this.menu;
	}

	public VerticalPanel getCenterLayout() {
		return centerPanel;
	}

	public BHTouchImage getUp() {
		return up;
	}

	public BHTouchImage getDown() {
		return down;
	}

	public BHTouchImage getLeft() {
		return left;
	}

	public BHTouchImage getRight() {
		return right;
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