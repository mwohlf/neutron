package net.wohlfart.neutron.event;


// can't use annotations in android
// we need something like GWT's event bus
// see: http://stackoverflow.com/questions/6030202/how-to-use-the-gwt-eventbus
public interface IEventBus<T> {
	
	
    void register(Object subscriber);
    
    void unregister(Object subscriber);

    // resets the event as soon as it is delivered
    void post(T event);
   
    // return true if there are some event waiting to be delivered
    boolean hasEvent();
    
    // fires the event in the current thread
    void fireEvent();
    
    // remove all events from the queue
    void flush();
   
}
