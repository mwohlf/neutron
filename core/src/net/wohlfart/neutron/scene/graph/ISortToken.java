package net.wohlfart.neutron.scene.graph;

public interface ISortToken {

	boolean isTranslucent();

	// using distance^2 for normal entities
    double getZOrder();
    
    String getShaderName();
    
    // TODO add a state object here we need one anyways to 
    // setup stae values like back culling, depkth filter, etc...

}