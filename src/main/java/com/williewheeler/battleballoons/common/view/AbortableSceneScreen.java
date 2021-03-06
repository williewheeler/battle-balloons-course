package com.williewheeler.battleballoons.common.view;

import com.williewheeler.battleballoons.common.BBConfig;
import com.williewheeler.battleballoons.common.world.BBScene;
import io.halfling.view.event.ScreenEvent;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by willie on 7/3/17.
 */
public class AbortableSceneScreen extends SceneScreen {

	public static AbortableSceneScreen create(
			String name,
			BBConfig config,
			BBViewContext context,
			BBScene scene) {

		AbortableSceneScreen screen = new AbortableSceneScreen(name, config, context, scene);
		screen.postConstruct();
		return screen;
	}

	private AbortableSceneScreen(String name, BBConfig config, BBViewContext context, BBScene scene) {
		super(name, config, context, scene);
	}

	@Override
	public KeyListener buildKeyHandler() {
		return new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				switch (e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						fireScreenEvent(ScreenEvent.Type.PREVIOUS_SCREEN);
						break;
					case KeyEvent.VK_RIGHT:
						fireScreenEvent(ScreenEvent.Type.NEXT_SCREEN);
						break;
					default:
						fireScreenEvent(ScreenEvent.Type.SCREEN_ABORTED);
						break;
				}
			}
		};
	}
}
