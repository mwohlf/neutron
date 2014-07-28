package net.wohlfart.neutron;

import com.badlogic.gdx.ApplicationListener;

public interface IStage extends ApplicationListener {

	// this method might be run async to do some heavy loading
	// for this stage
	IStage prepare();
	
	
		
	public final static IStage NULL = new IStage() {

		@Override
		public IStage prepare() {
			return this;
		}
		
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

	};


	
}
