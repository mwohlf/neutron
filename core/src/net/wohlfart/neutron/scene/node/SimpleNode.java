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
	private String id;
	private ISortToken token;

	private Mesh mesh;
	private int primFormat; // GL20.GL_TRIANGLE_FAN

	private Texture texture;
	private ShaderProgram shader;

	private Matrix4 matrix = new Matrix4();

	public SimpleNode(String id, ISortToken token) {
		this.id = id;
		this.token = token;
	}

	public SimpleNode withMesh(Mesh mesh, int primFormat) {
		this.mesh = mesh;
		this.primFormat = primFormat;
		return this;
	}

	public SimpleNode withTexture(Texture texture) {
		this.texture = texture;
		return this;
	}

	public SimpleNode withShader(ShaderProgram shader) {
		this.shader = shader;
		return this;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public ISortToken getSortToken() {
		return token;
	}

	@Override
	public void render(IRenderContext ctx, Iterable<ITree<INode>> children) {
		ctx.begin(shader);
		Gdx.gl20.glDisable(GL20.GL_CULL_FACE);
		shader.setUniformMatrix("u_worldToClip", ctx.getViewMatrix());
		shader.setUniformMatrix("u_modelToWorld", matrix);
		if (texture != null) {
			Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
			texture.bind(0);
			shader.setUniformi("u_texture", 0);
		}
		ctx.render(mesh, primFormat);
		ctx.end();
	}

	@Override
	public Matrix4 getModel2World() {
		return matrix;
	}

}
