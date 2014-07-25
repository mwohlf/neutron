package net.wohlfart.neutron.scene.entity;

import java.util.Random;

import net.wohlfart.neutron.scene.IGraph;
import net.wohlfart.neutron.scene.IGraph.IEntity;
import net.wohlfart.neutron.scene.IGraph.INode;
import net.wohlfart.neutron.scene.IRenderContext;
import net.wohlfart.neutron.scene.ITree;
import net.wohlfart.neutron.scene.graph.ISortToken;
import net.wohlfart.neutron.scene.graph.NodeSortStrategy;
import net.wohlfart.neutron.scene.node.IRenderConfig;
import net.wohlfart.neutron.shader.ShaderLoader;
import net.wohlfart.neutron.util.Vector3d;

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

public class Pointsprites implements IEntity {

	private static final String POINT_SPRITES_TEXTURE_FILE = "cloud.jpg";
	//private static final String POINT_SPRITES_TEXTURE_FILE = "badlogic.jpg";

	private static final String SHADER_NAME = "pointsprites";

	private final String id = "poinsprites";

	private final Quaternion rotation = new Quaternion();

	private final Vector3d position = new Vector3d();

	private final Matrix4 matrix = new Matrix4().idt();

	private final int spriteCount = 500;

	private final Random random = new Random();

	private IGraph graph;

	private ShaderProgram shader;

	private Sprites sprites;

	private Texture texture;

	private Vector3 tmpMov = new Vector3();
	private Quaternion tmRot = new Quaternion();

	@Override
	public void update(Quaternion rot, Vector3 mov) {
		rotation.mulLeft(rot);
		tmRot.set(rotation);
		position.add(tmRot.conjugate().transform(tmpMov.set(mov)));
		matrix.idt();
		matrix.rotate(rotation);
		matrix.translate(position.getX(), position.getY(), position.getZ());
	}

	@Override
	public void register(IGraph graph) {
		this.graph = graph;
		texture = new Texture(Gdx.files.internal(POINT_SPRITES_TEXTURE_FILE));
		shader = ShaderLoader.load(SHADER_NAME);
		sprites = new Sprites();
		graph.add(sprites);
		graph.add(this);
	}

	@Override
	public void unregister() {
		graph.remove(sprites);
		graph.remove(this);
		graph = null;
	}

	private class Sprites extends Mesh implements INode {

		public Sprites() {
			super(true, // static
					spriteCount, // maxVertices
					0, // maxIndices
					new VertexAttribute(Usage.Position, 3, "a_position")
				//	new VertexAttribute(Usage.Color, 4, "a_color"),
			  	//  new VertexAttribute(Usage.TextureCoordinates, 2, "a_texCoord")
					);

			int floatCount = getVertexAttributes().vertexSize/4;
			
			float[] vertices = new float[spriteCount * floatCount];
			for (int i = 0; i < vertices.length; i += floatCount) {
				int j = i;
				vertices[j++] = random.nextFloat() * 200 -100;
				vertices[j++] = random.nextFloat() * 200 -100;
				vertices[j++] = random.nextFloat() * 200 -100;
				if (j > floatCount) {
					continue;
				}
/*
				if (j > floatCount) {
					continue;
				}
				vertices[i + 3] = 1;
				vertices[i + 4] = 1;
				vertices[i + 5] = 1;
				vertices[i + 6] = 1;
*/
				if (j > floatCount) {
					continue;
				}
				vertices[j++] = 1f;
				vertices[j++] = 1f;
				
			}
			this.setVertices(vertices);
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public ISortToken getSortToken() {
			return NodeSortStrategy.ZERO_TOKEN;
		}

		@Override
		public Matrix4 getModel2World() {
			return matrix;
		}

		@Override
		public void render(IRenderContext ctx, Iterable<ITree<INode>> children) {
			ctx.begin(shader);
			ctx.setRenderConfig(IRenderConfig.POINT_SPRITES);
			shader.setUniformMatrix("u_worldToClip", ctx.getCamera().getViewMatrix());
			shader.setUniformMatrix("u_modelToWorld", matrix);
			shader.setUniformf("u_thickness", 64f);
			if (texture != null) {
				Gdx.gl20.glEnable(GL20.GL_TEXTURE_2D);
				Gdx.gl20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_CLAMP_TO_EDGE );
				Gdx.gl20.glTexParameteri(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_CLAMP_TO_EDGE );
				texture.bind(0);
				shader.setUniformi("u_texture", 0);
			}
			ctx.render(this, GL20.GL_POINTS);
			ctx.end();
		}

	}


}
