package net.wohlfart.neutron.scene.util;


import java.util.Iterator;

import net.wohlfart.neutron.scene.graph.ISortToken;
import net.wohlfart.neutron.scene.node.SimpleNode;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.ShortArray;


/**
 * tool for creating vbo data structures
 *
 * @author mwohlf
 */
public class NodeBuilder {

	private static final float EPSILON = 0.001f;

	// offsets are only calculated in the VertexAttributes constructor...
	private static final VertexAttribute POSITION3 = new VertexAttribute(Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE);
	private static final VertexAttribute TEXTURE2 = new VertexAttribute(Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE);
	private static final VertexAttribute NORMAL3 = new VertexAttribute(Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE);
	private static final VertexAttribute COLOR4 = new VertexAttribute(Usage.Color, 4, ShaderProgram.COLOR_ATTRIBUTE);

	// set from outside
	protected Texture texture;
	protected VertexAttributes attributes;
	protected float size;
	protected Color color;
	protected Vector3 start;
	protected Vector3 end;
	
	// calculated / generated
	private int verticesIndex = -1;
	protected int vertexSize = 0;
	private CurrentVertex currentVertex = new CurrentVertex();
	protected ShaderProgram shader;
	protected ISortToken sortToken;

	protected FloatArray vertices = new FloatArray();
	protected ShortArray indices = new ShortArray();


	public NodeBuilder useAttributes(VertexAttributes attributes) {
		this.vertexSize = 0;
		Iterator<VertexAttribute> iter = attributes.iterator();
		while (iter.hasNext()) {
			vertexSize += iter.next().numComponents;
		}
		this.verticesIndex = -1;
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
	
	public NodeBuilder useStart(Vector3 start) {
		this.start = start;
		return this;
	}

	public NodeBuilder useEnd(Vector3 end) {
		this.end = end;
		return this;
	}

	public NodeBuilder useColor(Color color) {
		this.color = color;
		return this;
	}

	private Mesh createMesh() {
		float[] farray = new float[vertices.size];
		assert (farray.length % vertexSize == 0);
		for (int i = 0; i < vertices.size; i++) {
			farray[i] = vertices.get(i);
		}
		Mesh mesh = new Mesh(true, // static
				(int) (farray.length/vertexSize), // maxVertices
				indices.size, // maxIndices
				attributes);
		mesh.setVertices(farray);
		if (indices.size > 0) {
			short[] sarray = new short[indices.size];
			for (int i = 0; i < indices.size; i++) {
				sarray[i] = indices.get(i);
			}
			mesh.setIndices(sarray);
		}
		return mesh;
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
		verticesIndex++;
		vertices.ensureCapacity(vertexSize);
		vertices.addAll(new float[vertexSize], 0, vertexSize);
		return currentVertex;
	}
	
	private void addLine(int a, int b) {
		indices.ensureCapacity(2);
		indices.add(a);
		indices.add(b);
	}
	
	// FIXME: need to consider offsets...
	private void foreachPosition(Transformer transformer) {
		int count = (int) (vertices.size/vertexSize);
		for (int i = 0; i < count; i++) {
			float[] param = {
					vertices.get(i * vertexSize  + 0),
					vertices.get(i * vertexSize  + 1),
					vertices.get(i * vertexSize  + 2),					
			};
			transformer.transform(param);
			vertices.set(i * vertexSize + 0, param[0]);
			vertices.set(i * vertexSize + 1, param[1]);
			vertices.set(i * vertexSize + 2, param[2]);						
		}
	}

	private interface Transformer {
		void transform(float[] vertexValues);
	}

	private class CurrentVertex {

		private CurrentVertex withPosition3(Vector3 v) {
			return withPosition3(v.x, v.y, v.z);
		}

		private CurrentVertex withPosition3(float x, float y, float z) {
			final int i;
			if ((i = offset(POSITION3)) > -1) {
				vertices.set(verticesIndex * vertexSize + i + 0, x);
				vertices.set(verticesIndex * vertexSize + i + 1, y);
				vertices.set(verticesIndex * vertexSize + i + 2, z);
			}
			return this;
		}

		private CurrentVertex withTexture2(float s, float t) {
			final int i;
			if ((i = offset(TEXTURE2)) > -1) {
				vertices.set(verticesIndex * vertexSize + i + 0, s);
				vertices.set(verticesIndex * vertexSize + i + 1, t);
			}
			return this;
		}

		private CurrentVertex withNormal3(Vector3 v) {
			return withNormal3(v.x, v.y, v.z);
		}

		private CurrentVertex withNormal3(float x, float y, float z) {
			final int i;
			if ((i = offset(NORMAL3)) > -1) {
				vertices.set(verticesIndex * vertexSize + i + 0, x);
				vertices.set(verticesIndex * vertexSize + i + 1, y);
				vertices.set(verticesIndex * vertexSize + i + 2, z);
			}
			return this;
		}

		private CurrentVertex withColor4(Color color) {
			final int i;
			if ((i = offset(COLOR4)) > -1) {
				vertices.set(verticesIndex * vertexSize + i + 0, color.r);
				vertices.set(verticesIndex * vertexSize + i + 1, color.g);
				vertices.set(verticesIndex * vertexSize + i + 2, color.b);
				vertices.set(verticesIndex * vertexSize + i + 3, color.a);
			}
			return this;
		}

		
		private int offset(VertexAttribute attribute) {
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



	public SimpleNode createQuad(String id) {
		vertices.clear();
		float l = size/2f;
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

		return new SimpleNode(
				id, sortToken,
				createMesh(), GL20.GL_TRIANGLE_FAN,
				shader, texture);
	}

	public SimpleNode createCube(String id) {
		vertices.clear();
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


		return new SimpleNode(
				id, sortToken,
				createMesh(), GL20.GL_TRIANGLES,
				shader, texture);
	}
	


	public SimpleNode createRay(String id) {
		vertices.clear();
		indices.clear();
		
		addVertex()
			.withPosition3(+0.00f, +0.00f, +1.00f)
			.withColor4(color)
			;
		addVertex()
			.withPosition3(+0.00f, +0.00f, +0.00f)
			.withColor4(color)
			;
		addVertex()
			.withPosition3(+0.02f, +0.02f, +0.90f)
			.withColor4(color)
			;
		addVertex()
			.withPosition3(-0.02f, +0.02f, +0.90f)
			.withColor4(color)
			;
		addVertex()
			.withPosition3(-0.02f, -0.02f, +0.90f)
			.withColor4(color)
			;
		addVertex()
			.withPosition3(+0.02f, -0.02f, +0.90f)
			.withColor4(color)
			;
		
		final float len = new Vector3(end).sub(start).len();
		final Matrix4 matrix = new Matrix4().scl(len);
		if (new Vector3(end).nor().crs(Vector3.Z).len2() < EPSILON) {
			matrix.rotate(Vector3.X, 180);
		} else {
			matrix.rotate(Vector3.Z, new Vector3(end).nor());
		}
		foreachPosition(
		    new Transformer() {
		    	@Override
		    	public void transform(float[] vertexValues) {
		    		Vector3 vec = new Vector3(vertexValues[0], vertexValues[1], vertexValues[2]);
		    		vec.mul(matrix);
		    		vertexValues[0] = vec.x;
		    		vertexValues[1] = vec.y;
		    		vertexValues[2] = vec.z;
		    	}
			}
		); 

		addLine(1, 0);
		addLine(2, 0);
		addLine(3, 0);
		addLine(4, 0);
		addLine(5, 0);    
		
		return new SimpleNode(
				id, sortToken,
				createMesh(), GL20.GL_LINES,
				shader, null);
	}


}
