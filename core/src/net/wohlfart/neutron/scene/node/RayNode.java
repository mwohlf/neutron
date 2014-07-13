package net.wohlfart.neutron.scene.node;

import net.wohlfart.neutron.scene.IGraph.INode;
import net.wohlfart.neutron.scene.IRenderContext;
import net.wohlfart.neutron.scene.ITree;
import net.wohlfart.neutron.scene.graph.ISortToken;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

public class RayNode implements INode {
	private final String id;
	private final ISortToken sortToken;
	private final Matrix4 matrix = new Matrix4();

	private Mesh mesh;
	private int primeFormat;

	private ShaderProgram shader;

	public RayNode(String id, ISortToken sortToken, Mesh mesh,
			int primeFormat, ShaderProgram shader) {
		this.id = id;
		this.sortToken = sortToken;
		this.mesh = mesh;
		this.primeFormat = primeFormat;
		this.shader = shader;
	}
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public ISortToken getSortToken() {
		return sortToken;
	}

	@Override
	public void render(IRenderContext ctx, Iterable<ITree<INode>> children) {
		ctx.begin(shader);
		ctx.setRenderConfig(IRenderConfig.DEFAULT_3D);
		shader.setUniformMatrix("u_worldToClip", ctx.getCamera().getViewMatrix());
		shader.setUniformMatrix("u_modelToWorld", matrix);
		ctx.render(mesh, primeFormat);
		ctx.end();
	}

	@Override
	public Matrix4 getModel2World() {
		return matrix;
	}

}
