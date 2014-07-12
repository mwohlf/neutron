package net.wohlfart.neutron.scene.graph;

public class StaticSortToken implements ISortToken {

	private final boolean translucent;
	private final double zOrder;
	private final String shaderName;

	StaticSortToken(boolean translucent, double zOrder, String shaderName) {
		this.translucent = translucent;
		this.zOrder = zOrder;
		this.shaderName = shaderName;
	}

	@Override
	public boolean isTranslucent() {
		return translucent;
	}

	@Override
	public double getZOrder() {
		return zOrder;
	}

	@Override
	public String getShaderName() {
		return shaderName;
	}
}