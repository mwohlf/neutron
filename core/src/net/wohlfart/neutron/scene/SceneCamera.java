package net.wohlfart.neutron.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class SceneCamera extends Camera {
	public float fieldOfView = 67;

	final Vector3 tmp = new Vector3();

	public SceneCamera() {		
		this.fieldOfView = 67;
		this.viewportWidth = Gdx.graphics.getWidth();
		this.viewportHeight = Gdx.graphics.getHeight();
		
		position.set(0f, 0f, 0f);
		super.lookAt( 0, 0, -1);
		near = 10f;
		far = 1000;
		
		update();
	}

	@Override
	public void update () {
		update(true);
	}

	@Override
	public void update (boolean updateFrustum) {
		float aspect = viewportWidth / viewportHeight;
		projection.setToProjection(Math.abs(near), Math.abs(far), fieldOfView, aspect);
		view.setToLookAt(position, tmp.set(position).add(direction), up);
		combined.set(projection);
		Matrix4.mul(combined.val, view.val);

		if (updateFrustum) {
			invProjectionView.set(combined);
			Matrix4.inv(invProjectionView.val);
			frustum.update(invProjectionView);
		}
	}
	
	@Override
	public void lookAt(float x, float y, float z) {
		throw new NoSuchMethodError("not supported for this cam");
	}
	
	
	
	
	
	
}
