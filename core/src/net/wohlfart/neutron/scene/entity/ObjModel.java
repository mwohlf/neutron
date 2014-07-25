package net.wohlfart.neutron.scene.entity;

import java.util.HashSet;

import net.wohlfart.neutron.scene.IGraph.INode;
import net.wohlfart.neutron.scene.node.ObjModelNode;

public class ObjModel extends AbstractEntity {

	@Override
	public void initNodes() {
		ObjModelNode node = new ObjModelNode(
				"object", "default", "model/ship1.obj", position);
		
		node.getModel2World().setTranslation(position.getX(), position.getY(), position.getZ());
		nodes = new HashSet<INode>();
		nodes.add(node);
	}

}
