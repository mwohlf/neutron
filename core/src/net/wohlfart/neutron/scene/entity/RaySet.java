package net.wohlfart.neutron.scene.entity;

import java.util.HashSet;

import net.wohlfart.neutron.scene.IGraph.INode;
import net.wohlfart.neutron.scene.node.SimpleNode;
import net.wohlfart.neutron.scene.util.NodeBuilder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

public class RaySet extends AbstractEntity {

	private static final String SHADER_NAME = "line";

	@Override
	public void initNodes() {
		nodes = new HashSet<INode>();
		addRay(new Vector3(0,0,-10), new Vector3(0,0,30).scl(-1));
		addRay(new Vector3(0,0,-10), new Vector3(0,30,0));
		addRay(new Vector3(0,0,-10), new Vector3(30,0,0));
	}

	public void addRay(Ray ray) {
		addRay(ray.origin, ray.getEndPoint(new Vector3(), 10f));
	}

	/*
	 * start, end are in cam coords and have to be rotated/moved to fir into the current rot/pos of this Ray Set...
	 * 
	 */
	private void addRay(Vector3 start, Vector3 end) {
		Vector3 s = new Vector3(start);
		Vector3 e = new Vector3(end);
		
		s = s.add(position.getVector3().mul(rotation.cpy()));
		e = e.add(position.getVector3().mul(rotation.cpy()));
		
		s = s.mul(rotation.cpy().conjugate());
		e = e.mul(rotation.cpy().conjugate());

		System.out.println("adding ray from " + start + " to " + end);
		SimpleNode newNode = new NodeBuilder()
			.useAttributes(new VertexAttributes(
						new VertexAttribute(Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE),							
						new VertexAttribute(Usage.Color, 4, ShaderProgram.COLOR_ATTRIBUTE)))
			.useShader(SHADER_NAME)
			.useStart(s)
			.useEnd(e)
			.useColor(Color.RED)
			.createRay("ray");

		nodes.add(newNode);
		graph.add(newNode);
	}



}
