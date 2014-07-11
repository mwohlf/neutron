package net.wohlfart.neutron.scene.graph;

import net.wohlfart.neutron.scene.ICamera;

import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

/**
 * a camera that always stays at (0,0,0) and looks at (0,0,-1)
 * used in the scene graph
 *
 * @author mwohlf
 */
class Camera implements ICamera {

	public final Vector3 position = new Vector3(0, 0, 0);

	public final Vector3 direction = new Vector3(0, 0, -1);

	public final Vector3 up = new Vector3(0, 1, 0);

	public float fieldOfView = 67;

	/** the projection matrix **/
	public final Matrix4 projection = new Matrix4();

	/** the view matrix **/
	public final Matrix4 view = new Matrix4();
	/** the combined projection and view matrix **/
	public final Matrix4 combined = new Matrix4();
	/** the inverse combined projection and view matrix **/
	public final Matrix4 invProjectionView = new Matrix4();

	public float near = 1;

	public float far = 100;

	/** the viewport width **/
	public float viewportWidth = 0;
	/** the viewport height **/
	public float viewportHeight = 0;

	/** the frustum **/
	public final Frustum frustum = new Frustum();


	public void update() {
		float aspect = viewportWidth / viewportHeight;
		projection.setToProjection(Math.abs(near), Math.abs(far), fieldOfView, aspect);
		view.setToLookAt(position, direction, up);
		combined.set(projection);
		Matrix4.mul(combined.val, view.val);
		invProjectionView.set(combined);
		Matrix4.inv(invProjectionView.val);
		frustum.update(invProjectionView);
	}

	public Matrix4 getProjection() {
		return projection;
	}

	public Matrix4 getViewMatrix() {
		return combined;
	}

	public Matrix4 getInvProjectionView() {
		return invProjectionView;
	}

	public float getViewportWidth() {
		return viewportWidth;
	}

	public float getViewportHeight() {
		return viewportHeight;
	}

	@Override
	public void resizeViewport(int width, int height) {
		viewportWidth = width;		
		viewportHeight = height;
		update();
	}
	
}
