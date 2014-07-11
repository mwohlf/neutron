package net.wohlfart.neutron.scene;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public interface IUpdateable {

	void update(Quaternion rot, Vector3 mov);

}
