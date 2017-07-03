package bb.framework.actor;

import static bb.framework.actor.Direction.*;

/**
 * Created by willie on 6/25/17.
 */
public final class ActorUtil {

	private ActorUtil() {
	}

	public static Direction calculateDirection(int deltaX, int deltaY) {
		Direction direction = null;
		if (deltaY < 0) {
			if (deltaX < 0) {
				direction = UP_LEFT;
			} else if (deltaX > 0) {
				direction = UP_RIGHT;
			} else {
				direction = UP;
			}
		} else if (deltaY > 0) {
			if (deltaX < 0) {
				direction = DOWN_LEFT;
			} else if (deltaX > 0) {
				direction = DOWN_RIGHT;
			} else {
				direction = DOWN;
			}
		} else {
			if (deltaX < 0) {
				direction = LEFT;
			} else if (deltaX > 0) {
				direction = RIGHT;
			} else {
				direction = null;
			}
		}
		return direction;
	}
}