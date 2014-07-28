package net.wohlfart.neutron.scene;

import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class Root extends Entity  {

	@Override
	public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
		Renderable cleaner = pool.obtain();
		cleaner.material = null;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
