package net.wohlfart.neutron.scene.graph;

public interface ISortToken {
	
    public static final ISortToken POS_INF_TOKEN
    	= new StaticSortToken(false, Double.POSITIVE_INFINITY, "POS_INF_TOKEN");

    public static final ISortToken NEG_INF_TOKEN
    	= new StaticSortToken(false, Double.NEGATIVE_INFINITY, "NEG_INF_TOKEN");
   

	boolean isTranslucent();

	// using distance^2 for normal entities
    double getZOrder();
    
    String getShaderName();
    
    // TODO add a state object here we need one anyways to 
    // setup stae values like back culling, depkth filter, etc...

}