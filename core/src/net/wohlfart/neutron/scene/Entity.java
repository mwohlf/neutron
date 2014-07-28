package net.wohlfart.neutron.scene;

import net.wohlfart.neutron.util.Vector3d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

public abstract class Entity implements RenderableProvider, Disposable {

	protected final Quaternion rotation = new Quaternion();
	
	protected final Vector3d position = new Vector3d();
	
	protected final Color color = new Color(Color.BLUE);
	
	protected final Matrix4 transform = new Matrix4();	

	protected Matrix4 getTransform() {
		transform.idt();
		transform.rotate(rotation);
		transform.translate(position.getVector3());
		return transform;
	}
	
	public void update(Quaternion rot, Vector3 mov) {
		rotation.mulLeft(rot);
		position.add(mov);
	}
	
	public Entity withPosition(double x, double y, double z) {
		position.set(x, y, z);
		return this;
	}
	
	public Entity move(double x, double y, double z) {
		position.add(x,y,z);
		return this;
	}

	public Entity withRotation(Quaternion rot) {
		rotation.set(rot.x, rot.y, rot.z, rot.w);
		return this;
	}

	public Entity rotate(Quaternion rot) {
		rotation.mulLeft(rot);
		return this;
	}

	public Entity withColor(Color col) {
		color.set(col);
		return this;
	}

}
