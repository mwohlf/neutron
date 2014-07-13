package net.wohlfart.neutron.scene;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.collision.Ray;

public interface ICamera {

	Matrix4 getViewMatrix();

	void resizeViewport(int width, int height);
	
	Ray getPickRay(float screenX, float screenY);

}
