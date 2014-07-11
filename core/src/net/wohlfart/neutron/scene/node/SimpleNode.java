package net.wohlfart.neutron.scene.node;

import net.wohlfart.neutron.scene.IGraph.INode;
import net.wohlfart.neutron.scene.IRenderContext;
import net.wohlfart.neutron.scene.ITree;
import net.wohlfart.neutron.scene.graph.NodeSortStrategy.ISortToken;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

public class SimpleNode implements INode {
	private final String id;
	private final ISortToken sortToken;
	private final Matrix4 matrix = new Matrix4();

	private Mesh mesh;
	private int primeFormat; // GL20.GL_TRIANGLE_FAN

	private Texture texture;
	private ShaderProgram shader;

	public SimpleNode(String id, ISortToken sortToken, Mesh mesh,
			int primeFormat, ShaderProgram shader, Texture texture) {
		this.id = id;
		this.sortToken = sortToken;
		this.mesh = mesh;
		this.primeFormat = primeFormat;
		this.shader = shader;
		this.texture = texture;
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
	public Matrix4 getModel2World() {
		return matrix;
	}

	@Override
	public void render(IRenderContext ctx, Iterable<ITree<INode>> children) {
		ctx.begin(shader);
		Gdx.gl20.glDisable(GL20.GL_CULL_FACE);
		shader.setUniformMatrix("u_worldToClip", ctx.getCamera().getViewMatrix());
		shader.setUniformMatrix("u_modelToWorld", matrix);
		if (texture != null) {
			Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
			texture.bind(0);
			shader.setUniformi("u_texture", 0);
		}
		ctx.render(mesh, primeFormat);
		ctx.end();
	}

}
