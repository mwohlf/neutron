package net.wohlfart.neutron.scene.node;


import net.wohlfart.neutron.scene.node.RenderConfigImpl.Blending;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.Clear;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.ClearColor;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.ClearDepth;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.ColorMask;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.DepthTest;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.FaceCulling;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.RenderProperty;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.ScissorTest;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.StencilTest;


public interface IRenderConfig<T extends IRenderConfig<T>> {

	// this is for testing and debugging
	public static final RenderConfigImpl DEFAULT = new RenderConfigImpl(
			Clear.OFF,
			Blending.OFF,
			ClearColor.BLUE,
			ClearDepth.ONE,
			ColorMask.ON,
			DepthTest.OFF,
			FaceCulling.OFF,
			ScissorTest.OFF,
			StencilTest.OFF);

	public static final RenderConfigImpl CLEAR = new RenderConfigImpl(
			Clear.ON,
			Blending.OFF,
			ClearColor.BLUE,
			ClearDepth.ONE,
			ColorMask.ON,
			DepthTest.OFF,
			FaceCulling.OFF,
			ScissorTest.OFF,
			StencilTest.OFF);

	public static final RenderConfigImpl BLENDING_ON = new RenderConfigImpl(
			Clear.OFF,
			Blending.ON,
			ClearColor.BLUE,
			ClearDepth.ONE,
			ColorMask.ON,
			DepthTest.GL_LEQUAL,
			FaceCulling.BACK,
			ScissorTest.OFF,
			StencilTest.OFF);

	public static final RenderConfigImpl ATMOSPHERE_FRONT = new RenderConfigImpl(
			Clear.OFF,
			Blending.ON,
			ClearColor.BLUE,
			ClearDepth.ONE,
			ColorMask.ON,
			DepthTest.GL_LEQUAL,
			FaceCulling.FRONT,
			ScissorTest.OFF,
			StencilTest.OFF);

	public static final RenderConfigImpl DEFAULT_3D = new RenderConfigImpl(
			Clear.OFF,
			Blending.OFF,
			ClearColor.BLUE,
			ClearDepth.ONE,
			ColorMask.ON,
			DepthTest.GL_LEQUAL,
			FaceCulling.BACK,                   // use BACK for production
			ScissorTest.OFF,
			StencilTest.OFF);

	public static final RenderConfigImpl SPRITE_CLOUD = new RenderConfigImpl(
			Clear.OFF,
			Blending.OFF,
			ClearColor.BLUE,
			ClearDepth.ONE,
			ColorMask.ON,
			DepthTest.GL_LEQUAL,
			FaceCulling.BACK,
			ScissorTest.OFF,
			StencilTest.OFF);

	public static final RenderConfigImpl SKYBOX = new RenderConfigImpl(
			Clear.OFF,
			Blending.OFF,
			ClearColor.BLUE,
			ClearDepth.ONE,
			ColorMask.ON,
			DepthTest.GL_LEQUAL,
			FaceCulling.BACK,
			ScissorTest.OFF,
			StencilTest.OFF);

	public static final RenderConfigImpl NULL_CONFIG = new RenderConfigImpl();

	
    public T switchValues(T oldValues);

    public boolean hasValue(RenderProperty property);

}
