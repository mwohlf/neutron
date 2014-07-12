package net.wohlfart.neutron.scene.graph;

import net.wohlfart.neutron.scene.util.Vector3d;

public class SortTokenImpl implements ISortToken {

	private final boolean translucent;
	private final Vector3d position;
	private final String shaderName;
		
	public SortTokenImpl(boolean translucent, Vector3d position, String shaderName) {
		this.translucent = translucent;
		this.position = position;
		this.shaderName = shaderName;
	}
	
	@Override
	public boolean isTranslucent() {
		return translucent;
	}

	@Override
	public double getZOrder() {
		return position.getLength2();
	}

	@Override
	public String getShaderName() {
		return shaderName;
	}

}
