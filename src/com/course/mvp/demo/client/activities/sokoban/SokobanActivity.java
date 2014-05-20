package com.course.mvp.demo.client.activities.sokoban;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.course.mvp.demo.client.activities.ClientFactoryImpl;
import com.course.mvp.demo.client.activities.sokoban.dataresources.DataResources;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.user.client.ui.DecoratedPopupPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.dialog.Dialogs;
import com.googlecode.mgwt.ui.client.dialog.Dialogs.ButtonType;
import com.googlecode.mgwt.ui.client.dialog.Dialogs.OptionCallback;
import com.googlecode.mgwt.ui.client.dialog.Dialogs.OptionsDialogEntry;
import com.googlecode.mgwt.ui.client.dialog.PopinDialog;
import com.googlecode.mgwt.ui.client.widget.Button;

public class SokobanActivity extends MGWTAbstractActivity {

	private final ClientFactoryImpl clientFactory;
	private Place place;
	private SokobanViewGwtImpl view;

	private int times = 0; // đếm thời gian
	private Timer t = null;
	private HTML time;

	private Canvas canvas;
	private Context2d ctx;
	private static final int noTiles = 7;
	private int tileSize; // default
							// 25
	private static final int empty = -1;
	private static final int wall = 0;
	private static final int floor = 1;
	private static final int man = 2;
	private static final int box = 3;
	private static final int goal = 4;
	private static final int manOnGoal = 5;
	private static final int boxOnGoal = 6;
	private static final int push = 10;
	private static final int up = 1;
	private static final int down = 2;
	private static final int left = 3;
	private static final int right = 4;
	private Puzzles puzzles;
	private int currentLevel = 0;
	private int width, height;
	private Image tileImage[] = new Image[noTiles];
	private Sound sound;
	private int grid[][];
	private Stack<Integer> moveStack;
	private int rowOffset, columnOffset;
	private int noRows, noColumns;
	private int manRow, manColumn;
	private int noMoves, noPushes;
	final DataResources res = DataResources.IMPL;

	// ******************************
	class MyHandler implements KeyDownHandler {
		@Override
		public void onKeyDown(KeyDownEvent event) {
			int keyCode = event.getNativeKeyCode();
			switch (keyCode) {
			case KeyCodes.KEY_UP:
				moveUp();
				break;
			case KeyCodes.KEY_DOWN:
				moveDown();
				break;
			case KeyCodes.KEY_LEFT:
				moveLeft();
				break;
			case KeyCodes.KEY_RIGHT:
				moveRight();
				break;
			case KeyCodes.KEY_PAGEUP:
				decrementLevel();
				break;
			case KeyCodes.KEY_PAGEDOWN:
				incrementLevel();
				break;
			case KeyCodes.KEY_R:
				resetWorld();
				break;
			case KeyCodes.KEY_U:
				undoMove();
				break;
			}
			paintComponent();
		}
	}

	public void CountTime() {

		t = new Timer() {
			int minutes = 0;
			int seconds = 0;

			@Override
			public void run() {
				if (times < 60) {
					seconds = times;
					minutes = 0;
				} else {
					minutes = times / 60;
					seconds = times % 60;
				}

				if (minutes < 10) {
					if (seconds < 10)
						time.setText("   0" + minutes + " : 0" + seconds);
					else
						time.setText("   0" + minutes + " : " + seconds);
				} else {
					if (seconds < 10)
						time.setText("   " + minutes + " : 0" + seconds);
					else
						time.setText("   " + minutes + " : " + seconds);
				}
				times++;
			}
		};
		t.scheduleRepeating(1000);
	}

	private void createGrid(String level) {
		noRows = 0;
		noColumns = 0;
		int columnCount = 0;
		for (int i = 0; i < level.length(); i++) {
			if (level.charAt(i) == 'M') {
				noRows++;
				if (columnCount > noColumns) {
					noColumns = columnCount;
				}
				columnCount = 0;
			} else {
				columnCount++;
			}
		}

		grid = new int[noColumns][noRows];
		for (int c = 0; c < noColumns; c++) {
			for (int r = 0; r < noRows; r++) {
				grid[c][r] = empty;
			}
		}

		refreshSize();
	}

	public void refreshSize() {
		tileSize = width / noColumns;
		if (tileSize * noRows > height) {
			tileSize = height / noRows;
		}

		columnOffset = (width - noColumns * tileSize) / 2;
		rowOffset = 5;
	}

	public void newLevel(String level) {
		createGrid(level);
		int r = -1;
		int c = -1;
		for (int i = 0; i < level.length(); i++) {
			switch (level.charAt(i)) {
			case '^':
				c++;
				break;
			case 'M':
				r++;
				c = 0;
				break;
			case '#':
				grid[c++][r] = wall;
				break;
			case ' ':
				grid[c++][r] = floor;
				break;
			case '@':
				grid[c][r] = man;
				manRow = r;
				manColumn = c++;
				break;
			case '$':
				grid[c++][r] = box;
				break;
			case '.':
				grid[c++][r] = goal;
				break;
			case '*':
				grid[c++][r] = boxOnGoal;
				break;
			}
		}
		moveStack = new Stack<Integer>();
		view.getCurLevel().setText("Level: " + (getCurrentLevel() + 1));
		noMoves = 0;
		noPushes = 0;
		view.getMoves().setText("Move: " + noMoves);
	}

	public int getNoMoves() {
		return noMoves;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentlevel) {
		this.currentLevel = currentlevel;
	}

	public void newCurrentLevel(int level) {
		currentLevel = level;
		newLevel(puzzles.getLevel(currentLevel));
	}

	public void resetWorld() {
		newLevel(puzzles.getLevel(currentLevel));
		times = 0;
	}

	private void createTileImages() {
		Image wallic = new Image(res.wall());
		Image flooric = new Image(res.floor());
		Image manic = new Image(res.sokoban());
		Image boxic = new Image(res.box());
		Image goalic = new Image(res.area());
		Image manongoalic = manic;
		Image boxongoalic = new Image(res.boxongoal());
		tileImage[0] = wallic;
		tileImage[1] = flooric;
		tileImage[2] = manic;
		tileImage[3] = boxic;
		tileImage[4] = goalic;
		tileImage[5] = manongoalic;
		tileImage[6] = boxongoalic;
	}

	public void paintComponent() {
		canvas.setCoordinateSpaceWidth(width);
		canvas.setCoordinateSpaceHeight(height);
		for (int c = 0; c < noColumns; c++) {
			for (int r = 0; r < noRows; r++) {
				if (grid[c][r] != empty) {
					ctx.drawImage(
							ImageElement.as(tileImage[grid[c][r]].getElement()),
							columnOffset + c * tileSize, rowOffset + r
									* tileSize, tileSize, tileSize);
				}
			}
		}
	}

	public void incrementLevel() {
		currentLevel++;
		if (currentLevel >= puzzles.getNoLevels()) {
			currentLevel = 0;
		}
		resetWorld();
	}

	public void decrementLevel() {
		currentLevel--;
		if (currentLevel < 0) {
			currentLevel = puzzles.getNoLevels() - 1;
		}
		resetWorld();
	}

	public void moveUp() {
		makeMove(manRow - 1, manColumn, manRow - 2, manColumn, up);
	}

	public void moveDown() {
		makeMove(manRow + 1, manColumn, manRow + 2, manColumn, down);
	}

	public void moveLeft() {
		makeMove(manRow, manColumn - 1, manRow, manColumn - 2, left);
	}

	public void moveRight() {
		makeMove(manRow, manColumn + 1, manRow, manColumn + 2, right);
	}

	private void makeMove(int r1, int c1, int r2, int c2, int direction) {
		if ((grid[c1][r1] == floor) || (grid[c1][r1] == goal)) {
			if (grid[c1][r1] == floor) {
				grid[c1][r1] = man;
			} else {
				grid[c1][r1] = manOnGoal;
			}
			moveStack.push(direction);
			sound.playFootstep();
			view.getMoves().setText("Move: " + ++noMoves);
		} else if (((grid[c1][r1] == box) || (grid[c1][r1] == boxOnGoal))
				&& ((grid[c2][r2] == floor) || (grid[c2][r2] == goal))) {
			if (grid[c2][r2] == floor) {
				grid[c2][r2] = box;
			} else {
				grid[c2][r2] = boxOnGoal;
			}
			if (grid[c1][r1] == box) {
				grid[c1][r1] = man;
			} else {
				grid[c1][r1] = manOnGoal;
			}
			moveStack.push(direction + push);
			sound.playFootstep();

			view.getMoves().setText("Move: " + ++noMoves + "");
		} else {
			sound.playDing();
			return;
		}
		if (grid[manColumn][manRow] == man) {
			grid[manColumn][manRow] = floor;
		} else {
			grid[manColumn][manRow] = goal;
		}
		manRow = r1;
		manColumn = c1;
		goalReached();
	}

	private void goalReached() {
		for (int c = 0; c < noColumns; c++) {
			for (int r = 0; r < noRows; r++) {
				if (grid[c][r] == box) {
					return;
				}
			}
		}
		sound.playTada();
		Dialogs.alert("Congratulation !", "Next Level !", null);

		if (currentLevel == 89) {
			Dialogs.alert("Congratulation !",
					"You have been finished 90 level of the game !", null);
		}

		incrementLevel();
	}

	public void undoMove() {
		if (moveStack.empty()) {
			return;
		}
		int direction = moveStack.peek();
		if (direction > push) {
			direction -= push;
		}
		switch (direction) {
		case up:
			moveBack(manRow + 1, manColumn, manRow - 1, manColumn);
			break;
		case down:
			moveBack(manRow - 1, manColumn, manRow + 1, manColumn);
			break;
		case left:
			moveBack(manRow, manColumn + 1, manRow, manColumn - 1);
			break;
		case right:
			moveBack(manRow, manColumn - 1, manRow, manColumn + 1);
			break;
		}
		goalReached();
	}

	private void moveBack(int r1, int c1, int r2, int c2) {
		if (grid[c1][r1] == floor) {
			grid[c1][r1] = man;
		} else {
			grid[c1][r1] = manOnGoal;
		}
		view.getMoves().setText("Move: " + --noMoves + "");
		if (moveStack.pop() > push) {
			if (grid[manColumn][manRow] == man) {
				grid[manColumn][manRow] = box;
			} else {
				grid[manColumn][manRow] = boxOnGoal;
			}
			if (grid[c2][r2] == box) {
				grid[c2][r2] = floor;
			} else {
				grid[c2][r2] = goal;
			}
		} else {
			if (grid[manColumn][manRow] == man) {
				grid[manColumn][manRow] = floor;
			} else {
				grid[manColumn][manRow] = goal;
			}
		}
		manRow = r1;
		manColumn = c1;
	}

	public Canvas getCanvas() {
		return this.canvas;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public SokobanActivity(ClientFactoryImpl clientFactory, Place place) {
		this.clientFactory = clientFactory;
		this.place = place;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = new SokobanViewGwtImpl();
		panel.setWidget(view);

		canvas = Canvas.createIfSupported();
		ctx = canvas.getContext2d();
		canvas.setCoordinateSpaceWidth(width);
		canvas.setCoordinateSpaceHeight(height);
		canvas.addKeyDownHandler(new MyHandler());

		this.width = Window.getClientWidth();
		// 49 is height of HeaderPanel
		this.height = Window.getClientHeight() - 4 * 49 - 10;

		puzzles = new Puzzles();
		sound = new Sound();
		createTileImages();

		resetWorld();
		paintComponent();

		view.getCenterLayout().add(getCanvas());
		time = view.getTime();
		CountTime();

		Window.addResizeHandler(new ResizeHandler() {

			@Override
			public void onResize(ResizeEvent event) {
				Scheduler.get().scheduleDeferred(new ScheduledCommand() {

					@Override
					public void execute() {
						width = Window.getClientWidth();
						// 49 is height of HeaderPanel
						height = Window.getClientHeight() - 4 * 49 - 10;
						refreshSize();
						paintComponent();
					}
				});

			}
		});

		view.getUp().addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent event) {
				moveUp();
				paintComponent();
			}
		});

		view.getDown().addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent event) {
				moveDown();
				paintComponent();
			}
		});

		view.getLeft().addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent event) {
				moveLeft();
				paintComponent();
			}
		});

		view.getRight().addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent event) {
				moveRight();
				paintComponent();
			}
		});

		view.getReset().addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent event) {
				resetWorld();
				paintComponent();
				times = 0;
			}
		});

		view.getUndo().addTapHandler(new TapHandler() {

			@Override
			public void onTap(TapEvent event) {
				undoMove();
				paintComponent();
			}
		});

		view.getMenuButton().addTapHandler(new TapHandler() {
			@Override
			public void onTap(TapEvent event) {
				List<OptionsDialogEntry> list = new ArrayList<OptionsDialogEntry>();
				list.add(new OptionsDialogEntry("New Game", ButtonType.CONFIRM));
				list.add(new OptionsDialogEntry("Select Level",
						ButtonType.NORMAL));
				list.add(new OptionsDialogEntry("Next Level", ButtonType.NORMAL));
				list.add(new OptionsDialogEntry("Previous Level",
						ButtonType.NORMAL));
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
							times = 0;
						} else if (index == 2) {
							final PopinDialog popinDialog = new PopinDialog();

							HorizontalPanel hInputPanel = new HorizontalPanel();
							Label lblInput = new Label("Level (1->90)");
							final TextBox tbxInput = new TextBox();
							hInputPanel.add(lblInput);
							hInputPanel.add(tbxInput);

							VerticalPanel vInputPanel = new VerticalPanel();
							vInputPanel.setStyleName("vInputPanel");
							vInputPanel.add(hInputPanel);

							Button btnOk = new Button("  Ok  ");
							btnOk.setWidth(70 + "px");
							btnOk.addTapHandler(new TapHandler() {
								@Override
								public void onTap(TapEvent event) {
									String strLevel = tbxInput.getText();
									try {
										int level = Integer.parseInt(strLevel);
										if (level >= 1 && level <= 90) {
											setCurrentLevel(level - 1);
											resetWorld();
											paintComponent();
											popinDialog.hide();
										} else {
											Dialogs.alert(
													"Error",
													"There are 90 level (1->90) . Please select other level !",
													null);
										}
									} catch (Exception e) {
										Dialogs.alert(
												"Error",
												"Please select other level (1->90)",
												null);
										return;
									}

								}
							});
							Button btnCancel = new Button("Cancel");
							btnCancel.setWidth(70 + "px");
							btnCancel.addTapHandler(new TapHandler() {

								@Override
								public void onTap(TapEvent event) {
									// TODO Auto-generated method stub
									popinDialog.hide();
								}
							});
							popinDialog.setCenterContent(true);

							HorizontalPanel hAddTwoButton = new HorizontalPanel();
							hAddTwoButton.add(btnOk);
							hAddTwoButton.add(btnCancel);
							hAddTwoButton.addStyleName("hAddTwoButton");

							vInputPanel.add(hAddTwoButton);

							DecoratedPopupPanel decoratedSelectLevelPanel = new DecoratedPopupPanel();
							decoratedSelectLevelPanel.add(vInputPanel);
							decoratedSelectLevelPanel.center();
							decoratedSelectLevelPanel
									.setStyleName("decoratedSelectLevelPanel");

							popinDialog.add(decoratedSelectLevelPanel);
							popinDialog.show();

						} else if (index == 3) {
							incrementLevel();
							resetWorld();
							paintComponent();
						} else if (index == 4) {
							decrementLevel();
							resetWorld();
							paintComponent();
						} else if (index == 5) {
							Dialogs.alert(
									"Instructions",
									"There are 90 level that You need to move boxs to appropriate points on the maps !",
									null);
						} else if (index == 6) {
							Dialogs.alert(
									"About",
									"Contact dangtuan7193@gmail.com for more infomation !",
									null);
						} else if (index == 7) {
						}
					}
				});
			}
		});
	}
}