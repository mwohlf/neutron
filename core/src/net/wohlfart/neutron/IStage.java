package net.wohlfart.neutron;

import com.badlogic.gdx.ApplicationListener;

public interface IStage extends ApplicationListener {

	IStage initialize();
	
	
	public final static IStage NULL = new IStage() {

		@Override
		public void create() {}

		@Override
		public void resize(int width, int height) {}

		@Override
		public void render() {}

		@Override
		public void pause() {}

		@Override
		public void resume() {}

		@Override
		public void dispose() {}

		@Override
		public IStage initialize() {
			return null;
		}
		
	};
	

}
