package net.wohlfart.neutron.scene.entity;

import net.wohlfart.neutron.scene.IGraph;
import net.wohlfart.neutron.scene.IGraph.IEntity;
import net.wohlfart.neutron.scene.node.SimpleNode;
import net.wohlfart.neutron.scene.util.NodeBuilder;
import net.wohlfart.neutron.scene.util.Vector3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class SimpleEntity implements IEntity {

	private IGraph graph;

	private SimpleNode node;

	Quaternion rotation = new Quaternion();
	Vector3d position = new Vector3d();
	
	Vector3 tmpMov = new Vector3();

	public SimpleEntity withPosition(double x, double y, double z) {
		position.x = x;
		position.y = y;
		position.z = z;
		return this;
	}
	

	@Override
	public void update(Quaternion rot, Vector3 mov) {
    	rotation.mulLeft(rot);
        position.add(rotation.conjugate().transform(tmpMov.set(mov)));
		Matrix4 matrix = node.getModel2World();
		matrix.idt();
		matrix.rotate(rotation.conjugate());
		matrix.translate((float)position.x, (float)position.y, (float)position.z);
	}

	@Override
	public void register(IGraph graph) {
		createNode();
		this.graph = graph;
		graph.add(this);
		graph.add(node);
	}

	private void createNode() {
		ShaderProgram shader = new ShaderProgram(
				Gdx.files.internal("shader/default.vert").readString("UTF-8"),
				Gdx.files.internal("shader/default.frag").readString("UTF-8"));
		if (!shader.isCompiled()) {
			throw new IllegalStateException("shader not compiled: " + shader.getLog());
		}
		node = new NodeBuilder()
			.useAttributes(new VertexAttributes(
					new VertexAttribute(Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE),
					new VertexAttribute(Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE),
					new VertexAttribute(Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE)))
		    .useShader(shader)
		    .useSize(5f)
		    .useTexture(new Texture(Gdx.files.internal("badlogic.jpg")))
			.createQuad("simple");
	}

	@Override
	public void unregister() {
		graph.remove(node);
		graph.remove(this);
		graph = null;
	}

}
