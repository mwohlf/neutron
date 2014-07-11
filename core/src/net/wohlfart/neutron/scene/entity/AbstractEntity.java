package net.wohlfart.neutron.scene.entity;

import net.wohlfart.neutron.scene.IGraph;
import net.wohlfart.neutron.scene.IGraph.IEntity;
import net.wohlfart.neutron.scene.IGraph.INode;
import net.wohlfart.neutron.scene.util.Vector3d;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public abstract class AbstractEntity implements IEntity {

	protected final Quaternion rotation = new Quaternion();
	protected final Vector3d position = new Vector3d();
	
	private IGraph graph;

	private INode node;

	Vector3 tmpMov = new Vector3();

	public IEntity withPosition(double x, double y, double z) {
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
		node = createNode();
		this.graph = graph;
		graph.add(this);
		graph.add(node);
	}

	@Override
	public void unregister() {
		graph.remove(node);
		graph.remove(this);
		graph = null;
	}

	abstract INode createNode();

}
