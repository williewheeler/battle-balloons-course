package bb.common.actor.model;

import bb.framework.actor.AbstractActor;
import bb.framework.util.MathUtil;

/**
 * Created by willie on 6/30/17.
 */
public class BigBalloon extends AbstractActor {
	private static final int WIDTH = 12;
	private static final int HEIGHT = 12;
	private static final int BALLOON_NUM_COLORS = 6;
	private static final int ROT_TTL = 5;

	private int dx;
	private int dy;
	private int colorIndex;
	private int rotationIndex;
	private int dRotation;
	private int rotTtl;

	public BigBalloon(int x, int y, int dx, int dy, int rot, int dRot) {
		super(null, x, y, WIDTH, HEIGHT);
		this.dx = dx;
		this.dy = dy;
		this.rotationIndex = rot;
		this.dRotation = dRot;
		this.rotTtl = ROT_TTL;
		this.colorIndex = MathUtil.nextRandomInt(BALLOON_NUM_COLORS);
	}

	public int getColorIndex() {
		return colorIndex;
	}

	public int getRotationIndex() {
		return rotationIndex;
	}

	@Override
	public void updateBody() {
		changeX(dx);
		changeY(dy);

		if (rotTtl == 0) {
			this.rotationIndex += dRotation;

			if (rotationIndex < 0) {
				rotationIndex = 3;
			} else if (rotationIndex > 3) {
				rotationIndex = 0;
			}

			this.rotTtl = ROT_TTL;
		} else {
			rotTtl--;
		}
	}
}
