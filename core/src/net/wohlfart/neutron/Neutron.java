package net.wohlfart.neutron;

import net.wohlfart.neutron.input.GestureInput;
import net.wohlfart.neutron.input.KeyboardInput;
import net.wohlfart.neutron.scene.ICamera;
import net.wohlfart.neutron.scene.IGraph.IEntity;
import net.wohlfart.neutron.scene.IRenderContext;
import net.wohlfart.neutron.scene.entity.RaySet;
import net.wohlfart.neutron.scene.entity.Skybox;
import net.wohlfart.neutron.scene.graph.Graph;
import net.wohlfart.neutron.scene.node.RenderContext;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Ray;

public class Neutron extends ApplicationAdapter {

	private Graph graph;
	private KeyboardInput keyboardInput;
	private GestureInput gestureInput;
		
	private IRenderContext ctx;
	private RaySet raySet;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		
		this.graph = new Graph();
		this.keyboardInput = new KeyboardInput();
		this.gestureInput = new GestureInput();
		this.ctx = new RenderContext(graph.getCamera());

		Gdx.input.setInputProcessor(new InputMultiplexer(
				keyboardInput, new GestureDetector(gestureInput)));
		
		raySet = new RaySet();
		
		graph.setup(new IEntity[] {
				
				raySet,
			    new Skybox(),
				
				/*
			    new Quad().withPosition(0, 0, -100),
				new Quad().withPosition(1, 0, -200),
				new Quad().withPosition(0, 5, -300),
				new Quad().withPosition(3, 2, -400),
				new Quad().withPosition(2, 4, -500),
				new Quad().withPosition(1, 6, -600), 		
				new Cube().withPosition(5,2,-50),								
				new Pointsprites(),
				*/
		});
	}

	@Override
	public void resize(int width, int height) {
		this.ctx.getCamera().resizeViewport(width, height);
	}

	@Override
	public void render() {
		processInputs();
		// rendering
		graph.render(ctx);
	}
	
	
	private void processInputs() {
		// process pending user input
		keyboardInput.update(graph);
		gestureInput.update(graph);
		
		Vector2 pick = gestureInput.getPickPosition();
		if (!Float.isNaN(pick.x) && !Float.isNaN(pick.y)) {
			ICamera cam = graph.getCamera();
			Ray ray = cam.getPickRay(pick.x, pick.y);
			raySet.addRay(ray);
		}
		pick.x = Float.NaN;
		pick.y = Float.NaN;
	}

}
