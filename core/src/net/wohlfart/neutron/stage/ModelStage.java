package net.wohlfart.neutron.stage;

import net.wohlfart.neutron.IStage;
import net.wohlfart.neutron.input.ControllerInput;
import net.wohlfart.neutron.input.GestureInput;
import net.wohlfart.neutron.input.InputQueue;
import net.wohlfart.neutron.scene.Cube;
import net.wohlfart.neutron.scene.SceneGraph;
import net.wohlfart.neutron.scene.Skybox;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;

public class ModelStage implements IStage {

	private SceneGraph sceneGraph;

	private InputQueue inputQueue = new InputQueue();

	
	@Override
	public void create() {
		sceneGraph = new SceneGraph();
		sceneGraph.add(
				new Cube("cube1").withPosition(-10,0,-30).withColor(Color.RED),
				new Cube("cube2").withPosition(0,0,-30).withColor(Color.GREEN),
				new Cube("cube3").withPosition(+10,0,-30).withColor(Color.BLUE),
				new Skybox("skybox")
		);

		InputMultiplexer input = new InputMultiplexer(
				new ControllerInput(inputQueue), 
				new GestureDetector(new GestureInput(inputQueue)));
		Gdx.input.setInputProcessor(input);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.3f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT
	    		  | GL20.GL_DEPTH_BUFFER_BIT
	    		  | GL20.GL_STENCIL_BUFFER_BIT);
		sceneGraph.render();
		sceneGraph.update(inputQueue.getRotation(), inputQueue.getTranslation());
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public IStage prepare() {
		return this;
	}

}
