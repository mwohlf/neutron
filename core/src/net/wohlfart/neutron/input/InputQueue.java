package net.wohlfart.neutron.input;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class InputQueue {
	
	private final Quaternion rotation = new Quaternion();
	
	private final Vector3 translation = new Vector3();

	public void add(Quaternion rot) {
		rotation.mulLeft(rot);
	}

	public void add(Vector3 mov) {
		translation.add(mov);
	}

	public Quaternion getRotation() {
		return rotation;
	}
	
	public Vector3 getTranslation() {
		return translation;
	}
	
}
