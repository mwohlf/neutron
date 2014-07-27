package net.wohlfart.neutron;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * just a dark blue background
 */
public class IntroStage implements IStage {

	@Override
	public void create() {}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void dispose() {}

	@Override
	public IStage prepare() {
		return this;
	}

}
