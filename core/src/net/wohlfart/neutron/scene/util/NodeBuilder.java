package net.wohlfart.neutron.scene.util;


import java.util.Iterator;

import net.wohlfart.neutron.scene.graph.ISortToken;
import net.wohlfart.neutron.scene.node.SimpleNode;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.FloatArray;


/**
 * tool for creating vbo data structures
 *
 * @author mwohlf
 */
public class NodeBuilder {

	// offsets are only calculated in the VertexAttributes constructor...
	private static final VertexAttribute POSITION3 = new VertexAttribute(Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE);
	private static final VertexAttribute TEXTURE2 = new VertexAttribute(Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE);
	private static final VertexAttribute NORMAL3 = new VertexAttribute(Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE);

	private Texture texture;
	private VertexAttributes attributes;
	private float size;

	private int vertexIndex = -1;
	private int vertexSize = 0;
	private CurrentVertex currentVertex = new CurrentVertex();
	private FloatArray vbo;
	private ShaderProgram shader;
	private ISortToken sortToken;



	public NodeBuilder useAttributes(VertexAttributes attributes) {
		this.vertexSize = 0;
		Iterator<VertexAttribute> iter = attributes.iterator();
		while (iter.hasNext()) {
			vertexSize += iter.next().numComponents;
		}
		this.vertexIndex = -1;
		this.attributes = attributes;
		return this;
	}

	public NodeBuilder useSize(float size) {
		this.size = size;
		return this;
	}

	public NodeBuilder useTexture(Texture texture) {
		this.texture = texture;
		return this;
	}

	public NodeBuilder useShader(String shaderName) {
		this.shader = ShaderLoader.load(shaderName);
		return this;
	}

	public NodeBuilder useSortToken(ISortToken sortToken) {
		this.sortToken = sortToken;
		return this;
	}

	public SimpleNode createQuad(String id) {
		float l = size/2f;
		vbo = new FloatArray(0);
		addVertex()
		.withPosition3(-l,-l, 0)  // bottom left
		.withTexture2(0,1)
		.withNormal3(0,0,1)
		;
		addVertex()
		.withPosition3(+l,-l, 0)  // bottom right
		.withTexture2(1,1)
		.withNormal3(0,0,1)
		;
		addVertex()
		.withPosition3(+l,+l, 0)  // top right
		.withTexture2(1,0)
		.withNormal3(0,0,1)
		;
		addVertex()
		.withPosition3(-l,+l, 0)  // top left
		.withTexture2(0,0)
		.withNormal3(0,0,1)
		;

		float[] array = new float[vbo.size];
		for (int i = 0; i < vbo.size; i++) {
			array[i] = vbo.get(i);
		}
		Mesh mesh = new Mesh(true, // static
				(int) (array.length/vertexSize), // maxVertices
				0, // maxIndices
				attributes);
		mesh.setVertices(array);
		return new SimpleNode(
				id, sortToken,
				mesh, GL20.GL_TRIANGLE_FAN,
				shader, texture);
	}

	public SimpleNode createCube(String id) {
		vbo = new FloatArray(0);
		float l = size/2f;

		Vector3 v000 = new Vector3(-l,-l,-l);  //      010 ------- 110
		Vector3 v001 = new Vector3(-l,-l,+l);  //      /|          /|
		Vector3 v010 = new Vector3(-l,+l,-l);  //     / |         / |
		Vector3 v011 = new Vector3(-l,+l,+l);  //   011 ------- 111 |
		Vector3 v100 = new Vector3(+l,-l,-l);  //    | 000 ------|-100
		Vector3 v101 = new Vector3(+l,-l,+l);  //    | /         | /  
		Vector3 v110 = new Vector3(+l,+l,-l);  //    |/          |/
		Vector3 v111 = new Vector3(+l,+l,+l);  //   001 ------- 101

		addQuad(v101, v111, v011, v001, new Vector3(+0,+0,+1));
		addQuad(v100, v110, v111, v101, new Vector3(+1,+0,+0));
		addQuad(v000, v010, v110, v100, new Vector3(+0,+0,-1));
		addQuad(v001, v011, v010, v000, new Vector3(-1,+0,+0));		
		addQuad(v110, v010, v011, v111, new Vector3(+0,+1,+0));
		addQuad(v101, v001, v000, v100, new Vector3(+0,-1,+0)); 	

		float[] array = new float[vbo.size];
		for (int i = 0; i < vbo.size; i++) {
			array[i] = vbo.get(i);
		}
		assert (array.length % vertexSize == 0);
		Mesh mesh = new Mesh(true, // static
				(int) (array.length/vertexSize), // maxVertices
				0, // maxIndices
				attributes);
		mesh.setVertices(array);
		return new SimpleNode(
				id, sortToken,
				mesh, GL20.GL_TRIANGLES,
				shader, texture);
	}


	private void addQuad(Vector3 v0, Vector3 v1, Vector3 v2, Vector3 v3, Vector3 n) {

		addVertex()
		.withPosition3(v0)  // left top 
		.withTexture2(1,1)
		.withNormal3(n)
		;
		addVertex()
		.withPosition3(v1)  // left bottom 
		.withTexture2(1,0)
		.withNormal3(n)
		;
		addVertex()
		.withPosition3(v2)  // right bottom 
		.withTexture2(0,0)
		.withNormal3(n)
		;
		addVertex()
		.withPosition3(v3)  // right top 
		.withTexture2(0,1)
		.withNormal3(n)
		;
		addVertex()
		.withPosition3(v0)  // left top 
		.withTexture2(1,1)
		.withNormal3(n)
		;
		addVertex()
		.withPosition3(v2)  // right bottom 
		.withTexture2(0,0)
		.withNormal3(n);
	}

	private CurrentVertex addVertex() {
		return currentVertex.next();
	}

	class CurrentVertex {

		private CurrentVertex next() {
			vertexIndex++;
			vbo.ensureCapacity(vertexSize);
			vbo.addAll(new float[vertexSize], 0, vertexSize);
			return this;
		}

		CurrentVertex withPosition3(Vector3 v) {
			return withPosition3(v.x, v.y, v.z);
		}

		CurrentVertex withPosition3(float x, float y, float z) {
			final int i;
			if ((i = offset(POSITION3)) > -1) {
				vbo.set(vertexIndex * vertexSize + i + 0, x);
				vbo.set(vertexIndex * vertexSize + i + 1, y);
				vbo.set(vertexIndex * vertexSize + i + 2, z);
			}
			return this;
		}

		CurrentVertex withTexture2(float s, float t) {
			final int i;
			if ((i = offset(TEXTURE2)) > -1) {
				vbo.set(vertexIndex * vertexSize + i + 0, s);
				vbo.set(vertexIndex * vertexSize + i + 1, t);
			}
			return this;
		}

		CurrentVertex withNormal3(Vector3 v) {
			return withNormal3(v.x, v.y, v.z);
		}

		CurrentVertex withNormal3(float x, float y, float z) {
			final int i;
			if ((i = offset(NORMAL3)) > -1) {
				vbo.set(vertexIndex * vertexSize + i + 0, x);
				vbo.set(vertexIndex * vertexSize + i + 1, y);
				vbo.set(vertexIndex * vertexSize + i + 2, z);
			}
			return this;
		}

		int offset(VertexAttribute attribute) {
			Iterator<VertexAttribute> iter = attributes.iterator();
			while (iter.hasNext()) {
				VertexAttribute attr = iter.next();
				if (attr.equals(attribute)) {
					return attr.offset/4;
				}
			}
			return -1;
		}

	}

}
