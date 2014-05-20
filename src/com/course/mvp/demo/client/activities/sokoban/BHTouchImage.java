package com.course.mvp.demo.client.activities.sokoban;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.googlecode.mgwt.dom.client.event.mouse.HandlerRegistrationCollection;
import com.googlecode.mgwt.dom.client.event.tap.HasTapHandlers;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.dom.client.event.tap.TapHandler;
import com.googlecode.mgwt.dom.client.event.touch.HasTouchHandlers;
import com.googlecode.mgwt.dom.client.event.touch.TouchCancelHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchEndHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchMoveHandler;
import com.googlecode.mgwt.dom.client.event.touch.TouchStartHandler;
import com.googlecode.mgwt.dom.client.recognizer.longtap.HasLongTapHandlers;
import com.googlecode.mgwt.dom.client.recognizer.longtap.LongTapEvent;
import com.googlecode.mgwt.dom.client.recognizer.longtap.LongTapHandler;
import com.googlecode.mgwt.dom.client.recognizer.pinch.HasPinchHandlers;
import com.googlecode.mgwt.dom.client.recognizer.pinch.PinchEvent;
import com.googlecode.mgwt.dom.client.recognizer.pinch.PinchHandler;
import com.googlecode.mgwt.dom.client.recognizer.swipe.HasSwipeHandlers;
import com.googlecode.mgwt.dom.client.recognizer.swipe.SwipeEndEvent;
import com.googlecode.mgwt.dom.client.recognizer.swipe.SwipeEndHandler;
import com.googlecode.mgwt.dom.client.recognizer.swipe.SwipeMoveEvent;
import com.googlecode.mgwt.dom.client.recognizer.swipe.SwipeMoveHandler;
import com.googlecode.mgwt.dom.client.recognizer.swipe.SwipeStartEvent;
import com.googlecode.mgwt.dom.client.recognizer.swipe.SwipeStartHandler;
import com.googlecode.mgwt.ui.client.widget.touch.GestureUtility;
import com.googlecode.mgwt.ui.client.widget.touch.TouchWidgetImpl;

public class BHTouchImage extends Image implements HasTouchHandlers,
		HasTapHandlers, HasPinchHandlers, HasSwipeHandlers, HasLongTapHandlers {

	private HandlerRegistration clickHandler = null;
	private HandlerRegistration touchHandler = null;
	private static final TouchWidgetImpl impl = GWT
			.create(TouchWidgetImpl.class);
	protected final GestureUtility gestureUtility;

	private ImageResource resource;
	private ImageResource activeResource;
	private boolean isActive = false;

	public BHTouchImage(ImageResource resource) {
		super(resource);
		this.resource = resource;
		gestureUtility = new GestureUtility(this);
	}

	public BHTouchImage(String url) {
		super(url);
		gestureUtility = new GestureUtility(this);
	}

	public BHTouchImage(ImageResource resource, ImageResource activeResource) {
		super(resource);
		this.resource = resource;
		this.activeResource = activeResource;
		gestureUtility = new GestureUtility(this);
	}

	public void setActive(boolean active) {
		this.isActive = active;
		if (active && activeResource != null)
			this.setResource(activeResource);
		else
			this.setResource(resource);
	}

	public boolean isActive() {
		return this.isActive;
	}

	public HandlerRegistration getClickHandler() {
		return this.clickHandler;
	}

	public HandlerRegistration getTouchHandler() {
		return this.touchHandler;
	}

	public void setTouchHanlder(HandlerRegistration handler) {
		this.touchHandler = handler;
	}

	public void clicked() {
		this.addStyleName("matchingChosen");
	}

	public void removeTouchHandler() {
		if (touchHandler != null)
			touchHandler.removeHandler();
	}

	public void reset() {
		this.removeStyleName("matchingChosen");
		this.removeStyleName("matchingIncorrect");
	}

	@Override
	public HandlerRegistration addTouchStartHandler(TouchStartHandler handler) {
		return impl.addTouchStartHandler(this, handler);

	}

	@Override
	public HandlerRegistration addTouchMoveHandler(TouchMoveHandler handler) {
		return impl.addTouchMoveHandler(this, handler);

	}

	@Override
	public HandlerRegistration addTouchCancelHandler(TouchCancelHandler handler) {
		return impl.addTouchCancelHandler(this, handler);

	}

	@Override
	public HandlerRegistration addTouchEndHandler(TouchEndHandler handler) {
		return impl.addTouchEndHandler(this, handler);

	}

	@Override
	public HandlerRegistration addTouchHandler(TouchHandler handler) {
		HandlerRegistrationCollection handlerRegistrationCollection = new HandlerRegistrationCollection();

		handlerRegistrationCollection
				.addHandlerRegistration(addTouchCancelHandler(handler));
		handlerRegistrationCollection
				.addHandlerRegistration(addTouchStartHandler(handler));
		handlerRegistrationCollection
				.addHandlerRegistration(addTouchEndHandler(handler));
		handlerRegistrationCollection
				.addHandlerRegistration(addTouchMoveHandler(handler));
		return handlerRegistrationCollection;
	}

	@Override
	public HandlerRegistration addTapHandler(TapHandler handler) {
		gestureUtility.ensureTapRecognizer();
		return addHandler(handler, TapEvent.getType());
	}

	public HandlerRegistration addSwipeStartHandler(SwipeStartHandler handler) {
		gestureUtility.ensureSwipeRecognizer();
		return addHandler(handler, SwipeStartEvent.getType());
	}

	public HandlerRegistration addSwipeMoveHandler(SwipeMoveHandler handler) {
		gestureUtility.ensureSwipeRecognizer();
		return addHandler(handler, SwipeMoveEvent.getType());
	}

	public HandlerRegistration addSwipeEndHandler(SwipeEndHandler handler) {
		gestureUtility.ensureSwipeRecognizer();
		return addHandler(handler, SwipeEndEvent.getType());
	}

	@Override
	public HandlerRegistration addPinchHandler(PinchHandler handler) {
		gestureUtility.ensurePinchRecognizer(this);
		return addHandler(handler, PinchEvent.getType());
	}

	@Override
	public HandlerRegistration addLongTapHandler(LongTapHandler handler) {
		gestureUtility.ensureLongTapHandler();
		return addHandler(handler, LongTapEvent.getType());
	}
}