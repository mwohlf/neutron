package net.wohlfart.neutron.scene;

import net.wohlfart.neutron.scene.node.RenderConfigImpl;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;


// models the state consisting of current shader, view matrix etc.
public interface IRenderContext {

	void begin(ShaderProgram shader);

	void render(Mesh mesh, int primFormat);

	void end();

	ICamera getCamera();

	void setRenderConfig(RenderConfigImpl newRenderConfig);

}
