package net.wohlfart.neutron.scene;

import com.badlogic.gdx.math.Matrix4;

public interface ICamera {

	Matrix4 getViewMatrix();

	void resizeViewport(int width, int height);

}
