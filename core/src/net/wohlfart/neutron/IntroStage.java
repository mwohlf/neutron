package net.wohlfart.neutron;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class IntroStage implements IStage {

	@Override
	public void create() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public IStage initialize() {
		// TODO Auto-generated method stub
		return null;
	}

}
