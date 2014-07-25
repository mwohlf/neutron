package net.wohlfart.neutron.scene.node;

import net.wohlfart.neutron.scene.ICamera;
import net.wohlfart.neutron.scene.IRenderContext;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class RenderContext implements IRenderContext {

	private final ICamera camera;

	private ShaderProgram currentShader;

	private RenderConfigImpl currentConfig = RenderConfigImpl.NULL_CONFIG;

	public RenderContext(ICamera camera) {
		this.camera = camera;
	}

	@Override
	public void begin(ShaderProgram shader) {
		this.currentShader = shader;
		shader.begin();
	}

	@Override
	public void end() {
		if (currentShader != null) {
			currentShader.end();
			currentShader = null;
		}
	}

	@Override
	public void render(Mesh mesh, int primFormat) {
		mesh.render(currentShader, primFormat);
	}

	@Override
	public void render(ModelInstance model) {
		Renderable renderable = model.getRenderable(new Renderable());
		renderable.mesh.render(currentShader, renderable.primitiveType);
	}

	@Override
	public ICamera getCamera() {
		return camera;
	}

	@Override
	public void setRenderConfig(RenderConfigImpl newConfig) {
		if (!currentConfig.equals(newConfig)) {
			currentConfig = newConfig.switchValues(currentConfig);
		}
		
	}

}
