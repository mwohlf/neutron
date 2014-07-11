package net.wohlfart.neutron.input;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.wohlfart.neutron.scene.IUpdateable;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

// Events are dispatched right before the call to ApplicationListener.render()
public class KeyboardInput implements InputProcessor {

	float MOV_SPEED = 0.7f;
	float ROT_SPEED = 0.6f;

	private Vector3 mov = new Vector3();
	private Quaternion rot = new Quaternion();

	private final Map<Integer, Command> keyCodes = new HashMap<Integer, Command>();
	private final Set<Integer> pressedKeys = new HashSet<Integer>();

	public KeyboardInput() {
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

	public void pull(IUpdateable updateable) {
		Iterator<Integer> iter = pressedKeys.iterator();
		while (iter.hasNext()) {
			Command cmd = keyCodes.get(iter.next());
			if (cmd != null) {
				cmd.execute();
			}
		}
		updateable.update(rot, mov);
		reset();
	}

	private void reset() {
		mov.set(0, 0, 0);
		rot.set(0, 0, 0, 1);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keyCodes.containsKey(keycode)) {
			pressedKeys.add(keycode);
			return true;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (pressedKeys.contains(keycode)) {
			pressedKeys.remove(keycode);
			return true;
		}
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
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}


	interface Command {

		void execute();

	}

	class Rotate implements Command {
		final Vector3 vec;
		final float speed;

		Rotate(Vector3 vec, float speed) {
			this.vec = vec;
			this.speed = speed;
		}

		@Override
		public void execute() {
			rot.setFromAxis(vec, speed);
		}
	}

	class Translate implements Command {
		final Vector3 vec;
		final float speed;

		Translate(Vector3 vec, float speed) {
			this.vec = vec;
			this.speed = speed;
		}

		@Override
		public void execute() {
			mov.set(vec.x * speed,
					vec.y * speed,
					vec.z * speed);
		}
	}

}
