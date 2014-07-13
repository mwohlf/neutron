package net.wohlfart.neutron.scene;

import net.wohlfart.neutron.scene.node.RenderConfigImpl;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;


// models the state of the gl context
//  consisting of current shader, view matrix, ...
public interface IRenderContext {

	void begin(ShaderProgram shader);

	ICamera getCamera();

	void setRenderConfig(RenderConfigImpl newRenderConfig);

	void render(Mesh mesh, int primFormat);

	void end();

}
