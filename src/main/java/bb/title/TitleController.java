package bb.title;

import bb.core.GameController;
import bb.core.event.GameEvent;
import bb.core.event.GameListener;
import bb.core.view.FontFactory;
import bb.core.view.ImageFactory;
import bb.core.view.SpriteFactory;

import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static bb.BBConfig.FRAME_PERIOD_MS;

/**
 * Created by willie on 6/17/17.
 */
public class TitleController implements GameController {
	private TitleModel titleModel;
	private TitleScreen titleScreen;
	private TickHandler tickHandler;
	private KeyHandler keyHandler;
	private Timer timer;
	private GameListener gameListener;

	public TitleController(
			FontFactory fontFactory,
			ImageFactory imageFactory,
			SpriteFactory spriteFactory,
			GameListener gameListener) {

		this.titleModel = new TitleModel();
		this.titleScreen = new TitleScreen(titleModel, fontFactory, imageFactory, spriteFactory);
		this.tickHandler = new TickHandler();
		this.keyHandler = new KeyHandler();
		this.timer = new Timer(FRAME_PERIOD_MS, tickHandler);
		this.gameListener = gameListener;
	}

	@Override
	public void start() {
		timer.start();
	}

	@Override
	public void stop() {
		timer.stop();
	}

	@Override
	public JComponent getScreen() {
		return titleScreen;
	}

	@Override
	public KeyListener getKeyListener() {
		return keyHandler;
	}

	private void fireGameEvent(String type) {
		gameListener.handleEvent(new GameEvent(this, type));
	}

	private class TickHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (titleModel.isActive()) {
				titleModel.update();
				titleScreen.getTopLevelAncestor().repaint();
			} else {
				fireGameEvent(GameEvent.SCREEN_EXPIRED);
			}
		}
	}

	private class KeyHandler extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			switch (e.getKeyCode()) {
				case KeyEvent.VK_1:
					fireGameEvent(GameEvent.START_1P_GAME);
					break;
				case KeyEvent.VK_2:
					fireGameEvent(GameEvent.START_2P_GAME);
					break;
			}
		}
	}
}
