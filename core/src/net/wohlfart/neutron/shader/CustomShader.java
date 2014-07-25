package net.wohlfart.neutron.shader;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.shaders.BaseShader;

// TODO
public class CustomShader extends BaseShader {
	private String name;	

	@Override
	public void init() {
		
	}

	@Override
	public int compareTo(Shader other) {
		return 0;
	}

	@Override
	public boolean canRender(Renderable instance) {
		return false;
	}

}
