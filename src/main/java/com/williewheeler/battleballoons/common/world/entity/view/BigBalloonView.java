package com.williewheeler.battleballoons.common.world.entity.view;

import com.williewheeler.battleballoons.common.world.entity.model.BigBalloon;
import com.williewheeler.battleballoons.common.BBConfig;
import io.halfling.world.entity.model.Actor;
import io.halfling.core.Assert;
import io.halfling.world.entity.view.ActorView;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Created by willie on 6/30/17.
 */
public class BigBalloonView implements ActorView {
	private BufferedImage[][] balloonSprites;

	public BigBalloonView(BufferedImage[][] balloonSprites) {
		Assert.notNull(balloonSprites, "balloonSprites can't be null");
		this.balloonSprites = balloonSprites;
	}

	@Override
	public void paint(Graphics g, Actor actor) {
		final BigBalloon bigBalloon = (BigBalloon) actor;
		final int halfWidth = BBConfig.SPRITE_WIDTH_PX / 2;
		final int halfHeight = BBConfig.SPRITE_HEIGHT_PX / 2;
		final int xOffset = bigBalloon.getX() - halfWidth;
		final int yOffset = bigBalloon.getY() - halfHeight;
		final int colorIndex = bigBalloon.getColorIndex();
		final int rotIndex = bigBalloon.getRotationIndex();
		final BufferedImage sprite = balloonSprites[colorIndex][rotIndex];

		g.translate(xOffset, yOffset);
		g.drawImage(sprite, 0, 0, BBConfig.SPRITE_WIDTH_PX, BBConfig.SPRITE_HEIGHT_PX, null);
		g.translate(-xOffset, -yOffset);
	}
}
