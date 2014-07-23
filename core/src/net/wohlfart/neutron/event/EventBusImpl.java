package net.wohlfart.neutron.event;

import com.badlogic.gdx.utils.ObjectMap;

public class EventBusImpl implements IEventBus {
	
	private ObjectMap<Class<?>, ListenerSet> subscribers;

	@Override
	public void register(Object subscriber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregister(Object subscriber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void post(Object event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasEvent() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void fireEvent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}


	private static class ListenerSet {
		
	}
}
