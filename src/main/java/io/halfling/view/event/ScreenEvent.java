package io.halfling.view.event;

import io.halfling.view.Screen;
import io.halfling.core.Assert;

/**
 * Created by willie on 6/18/17.
 */
public class ScreenEvent {
	
	// FIXME These are more like attract mode screen types.
	// Some apply more generally (like SCREEN_EXPIRED for game mode transition screens).
	public enum Type {
		START_1P_GAME,
		START_2P_GAME,
		PREVIOUS_SCREEN,
		NEXT_SCREEN,
		SCREEN_ABORTED,
		SCREEN_EXPIRED
	}

	private Screen source;
	private Type type;

	public ScreenEvent(Screen source, Type type) {
		Assert.notNull(source, "source can't be null");
		this.source = source;
		this.type = type;
	}

	public Screen getSource() {
		return source;
	}

	public Type getType() {
		return type;
	}
}
