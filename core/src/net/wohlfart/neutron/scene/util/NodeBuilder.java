package net.wohlfart.neutron.scene.util;


import java.util.ArrayList;
import java.util.List;

import net.wohlfart.neutron.scene.graph.NodeSortStrategy;
import net.wohlfart.neutron.scene.node.SimpleNode;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class NodeBuilder {

	private Texture texture;
	private List<VertexAttribute> attributes;
	private float size;

	private int vertexIndex = -1;
	private int vertexSize = 0;
	private CurrentVertex currentVertex = new CurrentVertex();
	private ArrayList<Float> vbo;
	private ShaderProgram shader;

	VertexAttribute position3 = new VertexAttribute(Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE);
	VertexAttribute texture2 = new VertexAttribute(Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE);
	VertexAttribute normal3 = new VertexAttribute(Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE);


	public NodeBuilder useAttributes(VertexAttributes attributes) {
		this.attributes = new ArrayList<VertexAttribute>();
		this.vertexSize = 0;
		this.vertexIndex = -1;
		for (VertexAttribute attribute : attributes) {
			vertexSize += attribute.numComponents;
			this.attributes.add(attribute);
		}
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

	public NodeBuilder useShader(ShaderProgram shader) {
		this.shader = shader;
		return this;
	}

	public SimpleNode createQuad(String id) {
		float l = size/2f;
		vbo = new ArrayList<Float>();
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

		Mesh mesh = new Mesh(true, // static
		  		   4, // maxVertices
		  		   0, // maxIndices
		  		   attributes.toArray(new VertexAttribute[attributes.size()]));
		float[] array = new float[vbo.size()];
		for (int i = 0; i < vbo.size(); i++) {
			array[i] = vbo.get(i);
		}
		mesh.setVertices(array);
		return new SimpleNode(id, NodeSortStrategy.ZERO_SORT_TOKEN)
			.withMesh(mesh, GL20.GL_TRIANGLE_FAN)
		    .withShader(shader)
		    .withTexture(texture);
	}



	private CurrentVertex addVertex() {
		return currentVertex.next();
	}

	class CurrentVertex {
		boolean doNextCall = true;

		CurrentVertex next() {
			vertexIndex++;
			for (int i = 0; i < vertexSize; i++) {
				vbo.add((Float)0f);
			}
			return this;
		}

		public CurrentVertex withPosition(float x, float y, float z) {
			vbo.set(vertexIndex * vertexSize + 0 + 0, x);
			vbo.set(vertexIndex * vertexSize + 0 + 1, y);
			vbo.set(vertexIndex * vertexSize + 0 + 2, z);
			return this;
		}

		public CurrentVertex withTexture(float s, float t) {
			vbo.set(vertexIndex * vertexSize + 3 + 0, s);
			vbo.set(vertexIndex * vertexSize + 3 + 1, t);
			return this;
		}

		public CurrentVertex withNormal(float x, float y, float z) {
			vbo.set(vertexIndex * vertexSize + 5 + 0, x);
			vbo.set(vertexIndex * vertexSize + 5 + 1, y);
			vbo.set(vertexIndex * vertexSize + 5 + 2, z);
			return this;
		}

		CurrentVertex has(VertexAttribute attribute) {
			doNextCall = attributes.contains(attribute);
			return this;
		}


	}
}
