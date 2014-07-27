package net.wohlfart.neutron;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class Neutron implements ApplicationListener {

	private IStage currentState = IStage.NULL;
	private PlayStage playStage;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		switchState(new ModelStage());
		// createPlayState();
	}

	@Override
	public void resize(int width, int height) {
		currentState.resize(width, height);
	}

	@Override
	public void render() {
		currentState.render();
	}

	@Override
	public void pause() {
		currentState.pause();
	}

	@Override
	public void resume() {
		currentState.resume();
	}

	@Override
	public void dispose() {
		currentState.dispose();
	}
	
	/**
	 * call dispose() on the old stage and create() on the new stage
	 */
	private void switchState(IStage newState) {
		currentState.dispose();
		currentState = newState;
		currentState.create();
	}
	
	private void setPlayStage(PlayStage playStage) {
		this.playStage = playStage;
		switchState(playStage);
	}

	private void createPlayState() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// running NOT in the render thread
				final PlayStage playStage = new PlayStage();
				playStage.prepare();
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						// running in the render thread
						setPlayStage(playStage);
					}
				});
			}
		}).start();
	}
}
