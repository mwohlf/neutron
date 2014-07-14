package net.wohlfart.neutron.scene.entity;

import java.util.HashSet;

import net.wohlfart.neutron.scene.IGraph.INode;
import net.wohlfart.neutron.scene.util.NodeBuilder;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector3;

public class RaySet extends AbstractEntity {
		
	private static final String SHADER_NAME = "line";

	@Override
	public void initNodes() {
		nodes = new HashSet<INode>();
		addRay(new Vector3(0,0,0), new Vector3(0,0,30).scl(-1));
		addRay(new Vector3(0,0,0), new Vector3(0,30,0));
		addRay(new Vector3(0,0,0), new Vector3(30,0,0));
	}

	private void addRay(Vector3 start, Vector3 end) {
		nodes.add(new NodeBuilder()
			.useAttributes(new VertexAttributes(
					new VertexAttribute(Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE)))
		    .useShader(SHADER_NAME)
			.useStart(start)
			.useEnd(end)
			.useColor(Color.MAROON)
			.createRay("ray"));
	}

}
