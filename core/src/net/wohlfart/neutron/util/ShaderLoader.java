package net.wohlfart.neutron.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;


/**
 * simple shader cache
 *
 * @author mwohlf
 */
public class ShaderLoader {
	
	private static final String SHADER_DIR = "shader/";

	private static final Map<String, ShaderProgram> SHADER_CACHE = new HashMap<String, ShaderProgram>();

	public static ShaderProgram load(String name) {
		final ShaderProgram shader;
		if (SHADER_CACHE.containsKey(name)) {
			shader = SHADER_CACHE.get(name);
		} else {
			final String vertexShader = Gdx.files.internal(SHADER_DIR + name + ".vert").readString("UTF-8");
			final String fragmentShader = Gdx.files.internal(SHADER_DIR + name + ".frag").readString("UTF-8");
			shader = new ShaderProgram(vertexShader, fragmentShader);
			if (!shader.isCompiled()) {
				throw new IllegalStateException("the shader could not be compiled,"
						+ " shader name is: " + name
						+ " error log is: " + shader.getLog());
			}
		} 
		return shader;
	}

}
