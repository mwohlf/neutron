package net.wohlfart.neutron.scene.entity;

import java.util.Set;

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

	protected IGraph graph;

	protected Set<INode> nodes;

	protected Vector3 tmpMov = new Vector3();
	protected Quaternion tmpRot = new Quaternion();

	public IEntity withPosition(double x, double y, double z) {
		position.set(x, y, z);
		return this;
	}

	@Override
	public void update(Quaternion rot, Vector3 mov) {
		// updating position and rotation
		rotation.mulLeft(rot);
		tmpRot.set(rotation);
		position.add(tmpRot.conjugate().transform(tmpMov.set(mov)));
		// updating the nodes with the new pos/rot
		for (INode node : nodes) {
			final Matrix4 matrix = node.getModel2World();
			// setting the rot/mov to the parnets:
			// ----------------------------  resetting to the parents rot/mov:
			//  matrix.idt();
			//  matrix.rotate(rotation);
			//  matrix.translate(position.getX(), position.getY(), position.getZ());
			// ----------------------------
			
			// ---------------------------- update and modify rot/mov
			tmpRot = matrix.getRotation(tmpRot, true);			
			tmpRot.mulLeft(rot);			
			//
			tmpMov = matrix.getTranslation(tmpMov);
			tmpMov.add(mov);
			tmpMov = tmpRot.transform(tmpMov);
			//tmpMov.add(new Quaternion(tmpRot).transform(new Vector3(mov)));
			// ----------------------------

			// setting specific columns only:
			matrix.set(tmpRot);	
			matrix.setTranslation(tmpMov);
		}
	}
	
	@Override
	public void register(IGraph graph) {
		this.graph = graph;
		graph.add(this);
		initNodes();
		for (INode node : nodes) {
			graph.add(node);			
		}
	}

	@Override
	public void unregister() {
		for (INode node : nodes) {
			graph.remove(node);
		}
		graph.remove(this);
		graph = null;
	}

	public Quaternion getRotation() {
		return rotation;
	}

	public Vector3d getPosition() {
		return position;
	}

	abstract void initNodes();

}
