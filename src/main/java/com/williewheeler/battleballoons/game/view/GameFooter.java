package com.williewheeler.battleballoons.game.view;

import com.williewheeler.battleballoons.common.view.BBViewContext;
import com.williewheeler.battleballoons.common.view.resource.FontFactory;
import com.williewheeler.battleballoons.game.world.GameScene;
import io.halfling.world.entity.model.Player;
import io.halfling.core.Assert;

import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import static com.williewheeler.battleballoons.game.view.GameConfig.*;

/**
 * Created by willie on 6/4/17.
 */
public class GameFooter extends JComponent {
	private FontFactory fontFactory;
	private GameScene scene;

	public GameFooter(BBViewContext context, GameScene scene) {
		Assert.notNull(context, "context can't be null");
		Assert.notNull(scene, "scene can't be null");

		this.fontFactory = context.getFontFactory();
		this.scene = scene;
	}

	@Override
	public Dimension getPreferredSize() {
		return ARENA_FOOTER_SIZE_PX;
	}

	@Override
	public void paint(Graphics g) {
		Player player = scene.getPlayer();
		g.setFont(fontFactory.getSmallFont());
		FontMetrics fm = g.getFontMetrics();
		g.setColor(Color.RED);
		g.drawString("LEVEL", ARENA_FOOTER_LEVEL_LABEL_OFFSET_PX, fm.getHeight());
		g.setColor(Color.CYAN);
		g.drawString(String.valueOf(player.getLevel()), ARENA_FOOTER_LEVEL_VALUE_OFFSET_PX, fm.getHeight());
	}
}
