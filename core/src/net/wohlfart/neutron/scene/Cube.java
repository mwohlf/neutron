package net.wohlfart.neutron.scene;

import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Cube extends Entity  {

	protected final String entityName;
	
	protected ModelInstance instance;
	
	
	public Cube(String entityName) {
		this.entityName = entityName;
	}
	
	public void createRenderables() {
		Model model = new ModelBuilder().createBox(5f, 5f, 5f, 
				new Material(ColorAttribute.createDiffuse(color)),
				Usage.Position | Usage.Normal);
		instance = new ModelInstance(model);
		instance.transform.set(getTransform());
	}
	
	@Override
	public void update(Quaternion rot, Vector3 mov) {
		if (instance == null) {
			createRenderables();
		}
		super.update(rot, mov);
		instance.transform.set(getTransform());		
	};

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
