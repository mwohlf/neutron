package net.wohlfart.neutron.input;

import net.wohlfart.neutron.scene.IUpdateable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GestureInput implements GestureListener {
	private final Quaternion IDT_QUATERNION = new Quaternion().idt();

	private final float MOV_SPEED = 0.7f;
	private final float ZOOM_SPEED = 0.004f;

	private final float PAN_SPEED = -0.1f;
	private final float FLING_SPEED = -0.001f;

	private final float EPSILON = 0.0001f;

	private final Vector2 pick = new Vector2(Float.NaN, Float.NaN);
	private final Vector3 mov = new Vector3(0, 0, 0);
	private final Quaternion rot = new Quaternion().idt();

	// tmp  
	private Quaternion rotx = new Quaternion().idt();
	private Quaternion roty = new Quaternion().idt();


	// TODO: implement state pattern to handle input actions differently depending on the current state
	// e.g. when rotating a tap causes rotation to stop, when not rotating a tap causes a move...

	public void update(IUpdateable updateable) {
		updateable.update(rot, mov);
		slowdown();
	}
	
	public Vector2 getPickPosition() {
		return pick;
	}

	private void slowdown() {
		if (rot.isIdentity(EPSILON)) {
			rot.idt();
		} else {
			rot.slerp(IDT_QUATERNION, 0.01f);
		}
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		
		Gdx.app.debug("input", "touchDown called: (" + x + "/" + y + ") pointer: " + pointer + " button: " + button);
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		Gdx.app.debug("input", "tap called: (" + x + "/" + y + ") count: " + count + " button: " + button);
		if (count == 2) {
			mov.set(0, 0, MOV_SPEED);
			return true;	
		} else {
			// need an event bus here to post stuff...
			pick.set(x, y);
			return false;
		}
	}

	@Override
	public boolean longPress(float x, float y) {
		Gdx.app.debug("input", "longPress called: (" + x + "/" + y + ") ");
		mov.set(0,0,0);
		rot.idt();
		return true;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		Gdx.app.debug("input", "fling called: " + velocityX + ":" + velocityY + " button: " + button);
		rotx.set(Vector3.Y, velocityX * FLING_SPEED);
		roty.set(Vector3.X, velocityY * FLING_SPEED);
		rot.idt().mul(rotx).mul(roty);
		return true;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		Gdx.app.debug("input", "pan called: (" + x + "/" + y + ") deltas: " + deltaX + "/" + deltaY);
		rotx.set(Vector3.Y, deltaX * PAN_SPEED);
		roty.set(Vector3.X, deltaY * PAN_SPEED);
		rot.idt().mul(rotx).mul(roty);
		return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		Gdx.app.debug("input", "panStop called: (" + x + "/" + y + ") pointer " + pointer + " button: " + button);
		return true;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		Gdx.app.debug("input", "zoom called: initialDistance: " + initialDistance + " distance: " + distance);
		mov.set( 0f, 0f, (initialDistance - distance) * ZOOM_SPEED);
		return true;
	}

	@Override
	public boolean pinch(
			Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		Gdx.app.debug("input", "pinch called: \n"
				+ " initialPointer1: " + initialPointer1 + "\n"
				+ " initialPointer2: " + initialPointer2 + "\n"
				+ " pointer1: " + pointer1 + "\n"
				+ " pointer2: " + pointer2 + "\n"
		);
		return true;
	}

}
