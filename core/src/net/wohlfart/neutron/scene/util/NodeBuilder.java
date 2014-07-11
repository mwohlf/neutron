package net.wohlfart.neutron.scene.util;


import java.util.Iterator;

import net.wohlfart.neutron.scene.graph.NodeSortStrategy;
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

public class NodeBuilder {

	private Texture texture;
	private VertexAttributes attributes;
	private float size;

	private int vertexIndex = -1;
	private int vertexSize = 0;
	private CurrentVertex currentVertex = new CurrentVertex();
	private FloatArray vbo;
	private ShaderProgram shader;

	VertexAttribute position3 = new VertexAttribute(Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE);
	VertexAttribute texture2 = new VertexAttribute(Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE);
	VertexAttribute normal3 = new VertexAttribute(Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE);


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

	public SimpleNode createQuad(String id) {
		float l = size/2f;
		vbo = new FloatArray(0);
		addVertex()
		.has(position3).withPosition(-l,-l, 0)  // bottom left
		.has(texture2).withTexture(0,1)
		.has(normal3).withNormal(0,0,1);
		addVertex()
		.has(position3).withPosition(+l,-l, 0)  // bottom right
		.has(texture2).withTexture(1,1)
		.has(normal3).withNormal(0,0,1);
		addVertex()
		.has(position3).withPosition(+l,+l, 0)  // top right
		.has(texture2).withTexture(1,0)
		.has(normal3).withNormal(0,0,1);
		addVertex()
		.has(position3).withPosition(-l,+l, 0)  // top left
		.has(texture2).withTexture(0,0)
		.has(normal3).withNormal(0,0,1);

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
				id, NodeSortStrategy.ZERO_SORT_TOKEN,
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
				id, NodeSortStrategy.ZERO_SORT_TOKEN,
				mesh, GL20.GL_TRIANGLES,
				shader, texture);
	}


	private void addQuad(Vector3 v0, Vector3 v1, Vector3 v2, Vector3 v3, Vector3 n) {

		addVertex()
		.has(position3).withPosition(v0)  // left top 
		.has(texture2).withTexture(1,1)
		.has(normal3).withNormal(n);
		
		addVertex()
		.has(position3).withPosition(v1)  // left bottom 
		.has(texture2).withTexture(1,0)
		.has(normal3).withNormal(n);

		addVertex()
		.has(position3).withPosition(v2)  // right bottom 
		.has(texture2).withTexture(0,0)
		.has(normal3).withNormal(n);

		addVertex()
		.has(position3).withPosition(v3)  // right top 
		.has(texture2).withTexture(0,1)
		.has(normal3).withNormal(n);

		addVertex()
		.has(position3).withPosition(v0)  // left top 
		.has(texture2).withTexture(1,1)
		.has(normal3).withNormal(n);

		addVertex()
		.has(position3).withPosition(v2)  // right bottom 
		.has(texture2).withTexture(0,0)
		.has(normal3).withNormal(n);

		
	}

	private CurrentVertex addVertex() {
		return currentVertex.next();
	}

	class CurrentVertex {
		boolean doNextCall = true;
		int offset = 0;

		private CurrentVertex next() {
			vertexIndex++;
			this.offset = 0;
			vbo.ensureCapacity(vertexSize);
			vbo.addAll(new float[vertexSize], 0, vertexSize);
			return this;
		}

		CurrentVertex withPosition(Vector3 v) {
			withPosition(v.x, v.y, v.z);
			return this;
		}

		CurrentVertex withPosition(float x, float y, float z) {
			vbo.set(vertexIndex * vertexSize + (offset++), x);
			vbo.set(vertexIndex * vertexSize + (offset++), y);
			vbo.set(vertexIndex * vertexSize + (offset++), z);
			return this;
		}

		CurrentVertex withTexture(float s, float t) {
			vbo.set(vertexIndex * vertexSize + (offset++), s);
			vbo.set(vertexIndex * vertexSize + (offset++), t);
			return this;
		}

		CurrentVertex withNormal(Vector3 v) {
			return withNormal(v.x, v.y, v.z);
		}

		CurrentVertex withNormal(float x, float y, float z) {
			vbo.set(vertexIndex * vertexSize + (offset++), x);
			vbo.set(vertexIndex * vertexSize + (offset++), y);
			vbo.set(vertexIndex * vertexSize + (offset++), z);
			return this;
		}

		CurrentVertex has(VertexAttribute attribute) {
			Iterator<VertexAttribute> iter = attributes.iterator();
			while (iter.hasNext()) {
				if (iter.next().equals(attribute)) {
					doNextCall = true;
					return this;
				}
			}
			doNextCall = false;
			return this;
		}


	}
}
