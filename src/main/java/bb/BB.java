package bb;

import bb.arena.ArenaController;
import bb.arena.model.ArenaModel;
import bb.arena.view.ArenaScreen;
import bb.attract.AttractScreenFactory;
import bb.attract.backstory.BackstoryController;
import bb.attract.roster.RosterController;
import bb.attract.title.TitleController;
import bb.attract.title.TitleScreen;
import bb.common.view.factory.AudioFactory;
import bb.common.view.factory.FontFactory;
import bb.common.view.factory.SpriteFactory;
import bb.common.view.actor.ActorViewFactory;
import bb.framework.GameController;
import bb.framework.event.GameEvent;
import bb.framework.event.GameListener;
import bb.framework.view.AttractScreen;
import bb.framework.view.loader.FontLoader;
import bb.framework.view.loader.ImageLoader;
import bb.framework.view.Resizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// TODO Move attact mode logic into a dedicated controller [WLW]

public class BB extends JFrame {
	private static final Logger log = LoggerFactory.getLogger(BB.class);

	private FontLoader fontLoader;
	private ImageLoader imageLoader;

	private FontFactory fontFactory;
	private SpriteFactory spriteFactory;
	private AudioFactory audioFactory;
	private ActorViewFactory actorViewFactory;
	private AttractScreenFactory attractScreenFactory;

	private GameHandler gameHandler;
	private GameController currentController;

	public BB() {
		super("Battle Balloons");

		this.fontLoader = new FontLoader();
		this.imageLoader = new ImageLoader();

		this.fontFactory = new FontFactory(fontLoader);
		this.spriteFactory = new SpriteFactory(imageLoader);
		this.audioFactory = new AudioFactory();
		this.actorViewFactory = new ActorViewFactory(spriteFactory, fontFactory);
		this.attractScreenFactory = new AttractScreenFactory(fontFactory, imageLoader, spriteFactory, actorViewFactory);

		this.gameHandler = new GameHandler();
	}

	public void launch() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Forces correct size
		setContentPane(new Resizer());

		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		startTitle();
//		startRoster();
	}

	private void startTitle() {
		TitleScreen screen = attractScreenFactory.createTitleScreen();
		setCurrentController(new TitleController(screen, gameHandler));
	}

	private void startBackstory() {
		AttractScreen screen = attractScreenFactory.createBackstoryScreen();
		setCurrentController(new BackstoryController(screen, gameHandler));
	}

	private void startRoster() {
		AttractScreen screen = attractScreenFactory.createRosterScreen();
		setCurrentController(new RosterController(screen, gameHandler));
	}

	private void startGame(int numPlayers) {
		ArenaModel model = new ArenaModel();
		ArenaScreen screen = new ArenaScreen(model, fontFactory, spriteFactory);
		setCurrentController(new ArenaController(model, screen, audioFactory, gameHandler));
	}

	private void setCurrentController(GameController controller) {
		if (currentController != null) {
			currentController.stop();
			removeKeyListener(currentController.getKeyListener());
		}

		this.currentController = controller;
		setContentPane(new Resizer(controller.getGameScreen()));
		validate();
		addKeyListener(currentController.getKeyListener());
		currentController.start();
	}

	private class GameHandler implements GameListener {

		@Override
		public void handleEvent(GameEvent event) {
			Object source = event.getSource();
			String type = event.getType();

			if (type == GameEvent.START_1P_GAME) {
				startGame(1);
			} else if (type == GameEvent.START_2P_GAME) {
				startGame(2);
			} else if (type == GameEvent.SCREEN_ABORTED) {
				startTitle();
			} else if (type == GameEvent.SCREEN_EXPIRED) {
				if (source instanceof TitleController) {
					startBackstory();
				} else if (source instanceof BackstoryController) {
					startRoster();
				} else if (source instanceof RosterController) {
					startTitle();
				} else {
					throw new IllegalStateException("Unknown source: " + source);
				}
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new BB().launch();
			}
		});
	}
}
