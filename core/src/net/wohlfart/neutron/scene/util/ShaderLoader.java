package net.wohlfart.neutron.scene.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShaderLoader {

	private static final Map<String, ShaderProgram> map = new HashMap<String, ShaderProgram>();

	public static ShaderProgram load(String name) {
		final ShaderProgram shader;
		if (map.containsKey(name)) {
			shader = map.get(name);
		} else {
			final String vertexShader = Gdx.files.internal("shader/" + name + ".vert").readString("UTF-8");
			final String fragmentShader = Gdx.files.internal("shader/" + name + ".frag").readString("UTF-8");
			shader = new ShaderProgram(vertexShader, fragmentShader);
			if (!shader.isCompiled()) {
				throw new IllegalStateException("shader not compiled: " + shader.getLog());
			}
		} 
		return shader;
	}

}
