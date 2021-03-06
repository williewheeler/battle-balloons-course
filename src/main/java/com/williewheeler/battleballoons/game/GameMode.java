package com.williewheeler.battleballoons.game;

import com.williewheeler.battleballoons.common.BBConfig;
import com.williewheeler.battleballoons.common.view.BBViewContext;
import com.williewheeler.battleballoons.common.world.event.GameEvents;
import com.williewheeler.battleballoons.common.view.resource.AudioFactory;
import com.williewheeler.battleballoons.common.view.TransitionScreen;
import com.williewheeler.battleballoons.game.view.GameScreen;
import io.halfling.world.event.GameEvent;
import io.halfling.world.event.GameListener;
import io.halfling.view.event.ScreenEvent;
import io.halfling.view.event.ScreenListener;
import io.halfling.mode.AbstractMode;
import io.halfling.view.ScreenManager;
import io.halfling.core.Assert;

import static com.williewheeler.battleballoons.game.GameScreenNames.TRANSITION_SCREEN;

/**
 * Created by willie on 7/1/17.
 */
public class GameMode extends AbstractMode {
	private BBConfig config;
	private BBViewContext context;
	
	private Game game;
	private GameHandler gameHandler;
	private ScreenHandler screenHandler;

	public GameMode(BBConfig config, BBViewContext context, ScreenManager screenManager) {
		super(BBConfig.GAME_MODE, screenManager);
		Assert.notNull(config, "config can't be null");
		Assert.notNull(context, "context can't be null");
		this.config = config;
		this.context = context;
		this.gameHandler = new GameHandler();
		this.screenHandler = new ScreenHandler();
	}
	
	@Override
	public void start() {
		this.game = new Game();
		game.incrementPlayerLevel();
		initSceneListeners();
		context.getAudioFactory().playerFirstLevel();
		transitionTo(transitionScreen());
	}
	
	private void initSceneListeners() {
		AudioFactory audioFactory = context.getAudioFactory();
		game.getScene().addGameListener(gameHandler);
		game.getScene().addGameListener(new AudioHandler(audioFactory));
	}
	
	private TransitionScreen transitionScreen() {
		TransitionScreen screen = TransitionScreen.create(TRANSITION_SCREEN, config, context);
		screen.addScreenListener(screenHandler);
		return screen;
	}
	
	private GameScreen arenaScreen() {
		GameScreen screen = GameScreen.create(config, context, game.getScene());
		screen.addScreenListener(screenHandler);
		return screen;
	}
	
	private class GameHandler implements GameListener {

		@Override
		public void handleEvent(GameEvent event) {
			// TODO Can we use enum event types? [WLW]
			if (event == GameEvents.NEXT_LEVEL) {
				game.incrementPlayerLevel();
				initSceneListeners();
				transitionTo(transitionScreen());
			} else if (event == GameEvents.PLAYER_GONE) {
				transitionTo(transitionScreen());
			} else if (event == GameEvents.GAME_OVER) {
				stop();
			}
		}
	}
	
	private class AudioHandler implements GameListener {
		private AudioFactory audioFactory;
		
		public AudioHandler(AudioFactory audioFactory) {
			this.audioFactory = audioFactory;
		}
		
		@Override
		public void handleEvent(GameEvent event) {
			
			// TODO Refactor to avoid if/else? [WLW]
			if (event == GameEvents.PLAYER_WALKED) {
				audioFactory.playerWalks();
			} else if (event == GameEvents.PLAYER_THREW_BALLOON) {
				audioFactory.playerThrowsBalloon();
			} else if (event == GameEvents.PLAYER_DIED) {
				audioFactory.playerDies();
			} else if (event == GameEvents.BEAT_PLAYED) {
				audioFactory.beat();
			} else if (event == GameEvents.TEACHERS) {
				audioFactory.teachers();
			} else if (event == GameEvents.ANIMAL_RESCUED) {
				audioFactory.animalRescued();
			} else if (event == GameEvents.ANIMAL_DIED) {
				audioFactory.animalDies();
			} else if (event == GameEvents.NEXT_LEVEL) {
				audioFactory.playerNextLevel();
			} else if (event == GameEvents.OBSTACLE_DESTROYED) {
				// TODO Yeah, repurposing the sound...
//				audioFactory.startSound();
			} else if (event == GameEvents.JUDO_DIED) {
				audioFactory.judoHit();
			}
		}
	}
	
	private class ScreenHandler implements ScreenListener {
		
		@Override
		public void handleEvent(ScreenEvent event) {
			if (event.getSource() instanceof TransitionScreen) {
				switch (event.getType()) {
					case SCREEN_EXPIRED:
						game.spawnPlayer();
						game.getScene().setActive(true);
						transitionTo(arenaScreen());
						break;
				}
			}
		}
	}
}
