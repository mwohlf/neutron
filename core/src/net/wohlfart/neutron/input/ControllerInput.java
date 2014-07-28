package net.wohlfart.neutron.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.IntSet;

// events are dispatched right before the call to ApplicationListener.render()
public class ControllerInput implements InputProcessor {

	float MOV_SPEED = 0.7f;
	float ROT_SPEED = 0.6f;
	float SCROLL_SPEED = 1.2f;
	float MOUSE_SPEED = 0.6f;

	private Vector3 mov = new Vector3();
	private Quaternion rot = new Quaternion();

	private final IntMap<Command> keyCodes = new IntMap<Command>();
	private final IntSet pressedKeys = new IntSet();
	private final InputQueue inputQueue;
	
	private final Translate scroll = new Translate(Vector3.Z, 0);

	public ControllerInput(InputQueue inputQueue) {
		this.inputQueue = inputQueue;
		setup();
	}

	private void setup() {
		keyCodes.put(Input.Keys.LEFT, new Rotate(Vector3.Y, +ROT_SPEED));
		keyCodes.put(Input.Keys.RIGHT, new Rotate(Vector3.Y, -ROT_SPEED));
		keyCodes.put(Input.Keys.UP, new Rotate(Vector3.X, +ROT_SPEED));
		keyCodes.put(Input.Keys.DOWN, new Rotate(Vector3.X, -ROT_SPEED));
		keyCodes.put(Input.Keys.PAGE_DOWN, new Rotate(Vector3.Z, +ROT_SPEED));
		keyCodes.put(Input.Keys.PAGE_UP, new Rotate(Vector3.Z, -ROT_SPEED));
		keyCodes.put(Input.Keys.Q, new Translate(Vector3.Y, +MOV_SPEED));
		keyCodes.put(Input.Keys.X, new Translate(Vector3.Y, -MOV_SPEED));
		keyCodes.put(Input.Keys.W, new Translate(Vector3.Z, -MOV_SPEED));
		keyCodes.put(Input.Keys.Y, new Translate(Vector3.Z, +MOV_SPEED));
		keyCodes.put(Input.Keys.A, new Translate(Vector3.X, -MOV_SPEED));
		keyCodes.put(Input.Keys.S, new Translate(Vector3.X, +MOV_SPEED));
	}

	@Override
	public boolean keyDown(int key) {
		if (keyCodes.containsKey(key)) {
			keyCodes.get(key).execute();
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
//		rot.setFromAxis(Vector3.Y, screenX * MOUSE_SPEED);
//		rot.setFromAxis(Vector3.X, screenY * MOUSE_SPEED);
		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		scroll.speed = (float)amount * SCROLL_SPEED;
		scroll.execute();
		return true;
	}


	interface Command {

		void execute();

	}

	// a rotation command
	private class Rotate implements Command {
		final Vector3 vec;
		float speed;

		Rotate(Vector3 vec, float speed) {
			this.vec = vec;
			this.speed = speed;
		}

		@Override
		public void execute() {
			rot.setFromAxis(vec, speed);
			Gdx.app.debug("input", "rotate: (" + vec.x + "/" + vec.y + "/" + vec.z + ") speed:" + speed);
			inputQueue.add(rot);
		}
	}

	// a translate command
	private class Translate implements Command {
		final Vector3 vec;
		float speed;

		Translate(Vector3 vec, float speed) {
			this.vec = vec;
			this.speed = speed;
		}

		@Override
		public void execute() {
			mov.set(vec.x * speed,
					vec.y * speed,
					vec.z * speed);
			Gdx.app.debug("input", "moving: (" + mov.x + "/" + mov.y + "/" + mov.z + ")");
			inputQueue.add(mov);
		}
	}

}
