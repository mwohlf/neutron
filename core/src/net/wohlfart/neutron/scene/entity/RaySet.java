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
		
		
		// TODO: rotate and move sart and end point
		
		// TODO: fix the matrix rest in AbstractEntity
		
		
		
		// s.rot(matrix)
		/*
		// updating position and rotation
		rotation.mulLeft(rot);
		tmRot.set(rotation);
		position.add(tmRot.conjugate().transform(tmpMov.set(mov)));		 
		 */
		
		System.out.println("ray in world space:  from " + start + " to " + end);

		SimpleNode newNode = new NodeBuilder()
			.useAttributes(new VertexAttributes(
						new VertexAttribute(Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE),							
						new VertexAttribute(Usage.Color, 4, ShaderProgram.COLOR_ATTRIBUTE)))
			.useShader(SHADER_NAME)
			.useStart(s)
			.useEnd(e)
			.useColor(Color.RED)
			.createRay("ray");
		
		// ...best option woudl be to modify the matrix of this node
		// however the entity resets the matricis of all nodes atm
		// we need to fix this first...
		// final Matrix4 matrix = newNode.getModel2World();

		graph.add(newNode);
		nodes.add(newNode);
	}



}
