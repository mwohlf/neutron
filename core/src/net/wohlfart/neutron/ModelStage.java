package net.wohlfart.neutron;

import net.wohlfart.neutron.scene.SceneGraph;
import net.wohlfart.neutron.scene.Skybox;

public class ModelStage implements IStage {

	private SceneGraph sceneGraph;

	@Override
	public void create() {
		sceneGraph = new SceneGraph();
		sceneGraph.add(new Skybox("skybox"));
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
		sceneGraph.render();
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
