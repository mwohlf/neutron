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

	private Vector3 tmpMov = new Vector3();
	private Quaternion tmRot = new Quaternion();

	public IEntity withPosition(double x, double y, double z) {
		position.set(x, y, z);
		return this;
	}
	
	@Override
	public void update(Quaternion rot, Vector3 mov) {
    	rotation.mulLeft(rot);
    	tmRot.set(rotation);
        position.add(tmRot.conjugate().transform(tmpMov.set(mov)));
		Matrix4 matrix = node.getModel2World();
		matrix.idt();
		matrix.rotate(rotation);
		matrix.translate(position.getX(), position.getY(), position.getZ());
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
