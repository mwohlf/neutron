package net.wohlfart.neutron.scene;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.DefaultRenderableSorter;
import com.badlogic.gdx.graphics.g3d.utils.DefaultShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;


/**
 *  abstraction for a 3D scene
 */
public class SceneGraph {
		
	// fixed position cam
	private final SceneCamera cam;
	
	// contains the light sources
	private final Environment environment;
	
	// responsible for sorting the Renderables
	private final ModelBatch batch;
	
	
	private final Set<RenderableProvider> models = new HashSet<RenderableProvider>();
	
	
	public SceneGraph() {
		
		cam = new SceneCamera();
		
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		
		batch = new ModelBatch(
				    // contains depth function blend state etc.
					new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1)), 	
					// returns a shader for a renderable
					new DefaultShaderProvider(), 
					// dist and transient sorting
					new DefaultRenderableSorter());	
	}
	
	public void render() {
		batch.begin(cam);
		batch.render(models);
		batch.end();
	}

	public void add(RenderableProvider model) {
		models.add(model);
	}
	
	public void remove(RenderableProvider model) {
		models.remove(model);
	}
	
}
