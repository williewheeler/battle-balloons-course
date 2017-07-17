package com.williewheeler.bb.common.actor.view;

import com.williewheeler.bb.common.actor.model.Balloon;
import com.williewheeler.bb.common.actor.model.BigBalloon;
import com.williewheeler.bb.common.actor.model.Bully;
import com.williewheeler.bb.common.actor.model.Cat;
import com.williewheeler.bb.common.actor.model.Dog;
import com.williewheeler.bb.common.actor.model.Judo;
import com.williewheeler.bb.common.actor.model.Lexi;
import com.williewheeler.bb.common.actor.model.Obstacle;
import com.williewheeler.bb.common.actor.model.Parrot;
import com.williewheeler.bb.common.actor.model.Text;
import com.williewheeler.bb.common.resource.FontFactory;
import com.williewheeler.bb.common.resource.SpriteFactory;
import com.williewheeler.retroge.actor.model.Actor;
import com.williewheeler.retroge.actor.view.ActorView;
import com.williewheeler.retroge.util.Assert;

/**
 * Created by willie on 6/24/17.
 */
public class ActorViewFactory {
	private LexiView lexiView;
	private BalloonView balloonView;
	private ObstacleView obstacleView;
	private JudoView judoView;
	private BullyView bullyView;
	private CatView catView;
	private DogView dogView;
	private ParrotView parrotView;
	private BigBalloonView bigBalloonView;
	private TextView textView;

	public ActorViewFactory(FontFactory fontFactory, SpriteFactory spriteFactory) {
		Assert.notNull(fontFactory, "fontFactory can't be null");
		Assert.notNull(spriteFactory, "spriteFactory can't be null");

		this.lexiView = new LexiView(spriteFactory);
		this.balloonView = new BalloonView();
		this.obstacleView = new ObstacleView();
		this.judoView = new JudoView(spriteFactory);
		this.bullyView = new BullyView(spriteFactory);
		this.catView = new CatView(spriteFactory);
		this.dogView = new DogView(spriteFactory);
		this.parrotView = new ParrotView(spriteFactory);
		this.bigBalloonView = new BigBalloonView(spriteFactory.getBigBalloons());
		this.textView = new TextView(fontFactory);
	}

	public ActorView getView(Actor actor) {
		// TODO Refactor to avoid if/else chain. [WLW]
		if (actor instanceof Lexi) {
			return lexiView;
		} else if (actor instanceof Balloon) {
			return balloonView;
		} else if (actor instanceof Obstacle) {
			return obstacleView;
		} else if (actor instanceof Judo) {
			return judoView;
		} else if (actor instanceof Bully) {
			return bullyView;
		} else if (actor instanceof Dog) {
			return dogView;
		} else if (actor instanceof Cat) {
			return catView;
		} else if (actor instanceof Parrot) {
			return parrotView;
		} else if (actor instanceof BigBalloon) {
			return bigBalloonView;
		} else if (actor instanceof Text) {
			return textView;
		} else {
			throw new IllegalArgumentException("Illegal actor: " + actor);
		}
	}
}
