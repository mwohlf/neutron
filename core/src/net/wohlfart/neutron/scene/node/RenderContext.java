package net.wohlfart.neutron.scene.node;

import net.wohlfart.neutron.scene.IGraph;
import net.wohlfart.neutron.scene.IRenderContext;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

public class RenderContext implements IRenderContext {
	ShaderProgram currentShader;
	IGraph graph;

	public RenderContext(IGraph graph) {
		this.graph = graph;
	}

	@Override
	public void begin(ShaderProgram shader) {
		this.currentShader = shader;
		shader.begin();
	}

	@Override
	public void end() {
		currentShader.end();
	}

	@Override
	public void render(Mesh mesh, int primFormat) {
		mesh.render(currentShader, primFormat);
	}

	@Override
	public Matrix4 getViewMatrix() {
		return graph.getViewMatrix();
	}

}
