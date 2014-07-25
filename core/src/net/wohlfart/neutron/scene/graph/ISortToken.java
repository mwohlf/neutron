package net.wohlfart.neutron.scene.graph;

public interface ISortToken {

	boolean isTranslucent();

	// using distance^2 for normal entities
    double getZOrder();
    
    String getShaderName();
    
    // TODO: add a state object here, we need one anyways to 
    // setup gl states like back culling, depth filter, stencil stuff, etc...
    // also we can unify the render method
    
}