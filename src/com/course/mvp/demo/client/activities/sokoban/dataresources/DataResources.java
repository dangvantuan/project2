package com.course.mvp.demo.client.activities.sokoban.dataresources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;

public interface DataResources extends ClientBundle {

	DataResources IMPL = (DataResources) GWT.create(DataResources.class);

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/image/area.png")
	ImageResource area();

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/audio/ding.wav")
	DataResource ding();

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/audio/Footstep.wav")
	DataResource footstep();

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/audio/tada.wav")
	DataResource tada();

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/image/arrow_down.png")
	ImageResource arrow_down();

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/image/arrow_left.png")
	ImageResource arrow_left();

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/image/arrow_right.png")
	ImageResource arrow_right();

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/image/arrow_up.png")
	ImageResource arrow_up();

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/image/box.png")
	ImageResource box();

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/image/boxongoal.png")
	ImageResource boxongoal();

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/image/floor.png")
	ImageResource floor();

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/image/middle_32px.png")
	ImageResource middle_32px();

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/image/reset.png")
	ImageResource reset();

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/image/sokoban.png")
	ImageResource sokoban();

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/image/undo.png")
	ImageResource undo();

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/image/wall.png")
	ImageResource wall();

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/image/pressed.png")
	ImageResource pressed();

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/image/menu.png")
	ImageResource menu();

	@Source("com/course/mvp/demo/client/activities/sokoban/dataresources/image/mario.jpg")
	ImageResource mario();

}
