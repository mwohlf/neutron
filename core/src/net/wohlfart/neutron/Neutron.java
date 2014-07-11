package net.wohlfart.neutron;

import net.wohlfart.neutron.input.GestureInput;
import net.wohlfart.neutron.input.KeyboardInput;
import net.wohlfart.neutron.scene.IGraph.IEntity;
import net.wohlfart.neutron.scene.IRenderContext;
import net.wohlfart.neutron.scene.IUpdateable;
import net.wohlfart.neutron.scene.entity.SimpleEntity;
import net.wohlfart.neutron.scene.entity.Skybox;
import net.wohlfart.neutron.scene.graph.Graph;
import net.wohlfart.neutron.scene.node.RenderContext;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Neutron extends ApplicationAdapter implements IUpdateable {

	private Graph graph;
	private KeyboardInput keyboardInput;
	private GestureInput gestureInput;
	private IRenderContext ctx;


	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		this.graph = new Graph();
		this.keyboardInput = new KeyboardInput();
		this.gestureInput = new GestureInput();
		this.ctx = new RenderContext(graph);

		Gdx.input.setInputProcessor(new InputMultiplexer(
				keyboardInput, new GestureDetector(gestureInput)));
		graph.setup(new IEntity[] {
				new Skybox(),
				new SimpleEntity().withPosition(0, 0, -100),
				new SimpleEntity().withPosition(1, 0, -200),
				new SimpleEntity().withPosition(0, 5, -300),
				new SimpleEntity().withPosition(3, 2, -400),
				new SimpleEntity().withPosition(2, 4, -500),
				new SimpleEntity().withPosition(1, 6, -600),
		});
	}

	@Override
	public void resize(int width, int height) {
		graph.resizeViewport(width, height);
	}

	@Override
	public void render() {
		keyboardInput.pull(graph);
		gestureInput.pull(graph);
		graph.render(ctx);
	}

	@Override
	public void update(Quaternion rot, Vector3 mov) {
		graph.update(rot, mov);
	}
}
