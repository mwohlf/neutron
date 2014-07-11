package net.wohlfart.neutron.scene.entity;

import net.wohlfart.neutron.scene.IGraph;
import net.wohlfart.neutron.scene.IGraph.IEntity;
import net.wohlfart.neutron.scene.IGraph.INode;
import net.wohlfart.neutron.scene.IRenderContext;
import net.wohlfart.neutron.scene.ITree;
import net.wohlfart.neutron.scene.graph.NodeSortStrategy;
import net.wohlfart.neutron.scene.graph.NodeSortStrategy.ISortToken;
import net.wohlfart.neutron.scene.util.ShaderLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Skybox implements IEntity {

	private IGraph graph;

	private ShaderProgram shader;

	private Side[] sides;

	private String path = "skybox/new";

	private Matrix4 matrix = new Matrix4();

	@Override
	public void update(Quaternion rot, Vector3 mov) {
		matrix.idt().rotate(rot);
		for (Side side : sides) {
			side.getModel2World().mulLeft(matrix);
		}
	}

	/** should only be called by the graph */
	@Override
	public void register(IGraph graph) {
		this.graph = graph;
		createSides();
		shader = ShaderLoader.load("skybox");
		graph.add(this);
		for (Side side : sides) {
			graph.add(side);
		}
	}

	private void createSides() {
		sides = new Side[] {
				new Side("back", new Texture(Gdx.files.internal(path + "/" + "back6.png")), new Vector3( 0f, 0f,+1f).nor()),
				new Side("front", new Texture(Gdx.files.internal(path + "/" + "front5.png")), new Vector3( 0f, 0f,-1f).nor()),
				new Side("top", new Texture(Gdx.files.internal(path + "/" + "top3.png")), new Vector3( 0f,+1f, 0f).nor()),
				new Side("bottom", new Texture(Gdx.files.internal(path + "/" + "bottom4.png")), new Vector3( 0f,-1f, 0f).nor()),
				new Side("right", new Texture(Gdx.files.internal(path + "/" + "right1.png")), new Vector3(+1f, 0f, 0f).nor()),
				new Side("left", new Texture(Gdx.files.internal(path + "/" + "left2.png")), new Vector3(-1f, 0f, 0f).nor()),
		};
	}

	@Override
	public void unregister() {
		for (Side side : sides) {
			graph.remove(side);
		}
		graph.remove(this);
		graph = null;
	}


	private class Side extends Mesh implements INode {

		private final String id;
		private final Texture texture;
		private final Matrix4 matrix = new Matrix4();

		private Side(String id, Texture texture, Vector3 normal) {
			super(true, // static
				  4, // maxVertices
				  0, // maxIndices
				  new VertexAttribute(Usage.Position, 3, "a_position"),
				  new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoord"));
			float l = normal.len();
			setVertices(new float[] {
					-1, -1, - l, 1, 0, // bottom left
					 1, -1, - l, 0, 0, // bottom right
					 1,  1, - l, 0, 1, // top right
					-1,  1, - l, 1, 1, // top left
			});
			this.id = id;
			this.texture = texture;
			if (Vector3.Z.equals(normal)) {
				getModel2World().setToRotation(Vector3.X, 180f).rotate(Vector3.Z, 180f);
			} else {
				getModel2World().setToRotation(Vector3.Z, normal);
			}
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public ISortToken getSortToken() {
			return NodeSortStrategy.POS_INF_TOKEN;
		}

		@Override
		public Matrix4 getModel2World() {
			return matrix;
		}

		@Override
		public void render(IRenderContext ctx, Iterable<ITree<INode>> children) {
			shader.begin();
			shader.setUniformMatrix("u_worldToClip", ctx.getCamera().getViewMatrix());
			shader.setUniformMatrix("u_modelToWorld", matrix);
			Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
			texture.bind(0);
			shader.setUniformi("u_texture", 0);
			render(shader, GL20.GL_TRIANGLE_FAN);
			shader.end();
		}

	}

}
