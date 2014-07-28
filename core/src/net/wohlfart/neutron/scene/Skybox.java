package net.wohlfart.neutron.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.CubemapAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Skybox extends Entity  {

	private static final String SKY_TEXTURE_PATH = "skybox/new";

	private final String entityName;
	
	private ModelInstance instance;

	public Skybox(String entityName) {
		this.entityName = entityName;
	}
	
	public void createRenderables() {
		Cubemap cubemap = new Cubemap(
				Gdx.files.internal(SKY_TEXTURE_PATH + "/" + "back6.png"),
				Gdx.files.internal(SKY_TEXTURE_PATH + "/" + "front5.png"),
				Gdx.files.internal(SKY_TEXTURE_PATH + "/" + "top3.png"),
				Gdx.files.internal(SKY_TEXTURE_PATH + "/" + "bottom4.png"),
				Gdx.files.internal(SKY_TEXTURE_PATH + "/" + "right1.png"),
				Gdx.files.internal(SKY_TEXTURE_PATH + "/" + "left2.png"),
				true);
		
		Material material = new Material(ColorAttribute.createDiffuse(Color.RED));
		material.set(new CubemapAttribute(CubemapAttribute.EnvironmentMap));
		
		Model model = new ModelBuilder().createBox(
				5f, 5f, 5f, 
				material,
				Usage.Position);
		instance = new ModelInstance(model);
		instance.transform.set(getTransform());
	}


	@Override
	public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
		if (instance == null) {
			createRenderables();
		}
		instance.getRenderables(renderables, pool);
	}

	@Override
	public void dispose() {
		instance.model.dispose();
	}

}
