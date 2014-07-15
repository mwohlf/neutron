package net.wohlfart.neutron.scene.node;


import net.wohlfart.neutron.scene.node.RenderConfigImpl.Blending;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.Clear;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.ClearColor;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.ClearDepth;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.ColorMask;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.DepthTest;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.FaceCulling;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.PointSprites;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.RenderProperty;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.ScissorTest;
import net.wohlfart.neutron.scene.node.RenderConfigImpl.StencilTest;


public interface IRenderConfig<T extends IRenderConfig<T>> {

	// this is for testing and debugging
	public static final RenderConfigImpl DEFAULT = new RenderConfigImpl(
			Clear.OFF,
			Blending.OFF,
			DepthTest.OFF,
			PointSprites.OFF,
			
			ColorMask.ON,
			ScissorTest.OFF,
			StencilTest.OFF,
			
			ClearDepth.ONE,
			ClearColor.BLACK,
			FaceCulling.OFF);

	public static final RenderConfigImpl CLEAR = new RenderConfigImpl(
			Clear.ON,
			Blending.OFF,
			DepthTest.OFF,
			PointSprites.OFF,
			
			ColorMask.ON,
			ScissorTest.OFF,
			StencilTest.OFF,
			
			ClearDepth.ONE,
			ClearColor.BLACK,
			FaceCulling.OFF);

	public static final RenderConfigImpl BLENDING_ON = new RenderConfigImpl(
			Clear.OFF,
			Blending.ON,
			DepthTest.GL_LEQUAL,
			PointSprites.OFF,
			
			ColorMask.ON,
			ScissorTest.OFF,
			StencilTest.OFF,
			
			ClearDepth.ONE,
			ClearColor.BLACK,
			FaceCulling.BACK);

	public static final RenderConfigImpl ATMOSPHERE_FRONT = new RenderConfigImpl(
			Clear.OFF,
			Blending.ON,
			DepthTest.GL_LEQUAL,
			PointSprites.OFF,
			
			ColorMask.ON,
			ScissorTest.OFF,
			StencilTest.OFF,

			ClearDepth.ONE,
			ClearColor.BLACK,
			FaceCulling.FRONT);

	public static final RenderConfigImpl DEFAULT_3D = new RenderConfigImpl(
			Clear.OFF,
			Blending.OFF,
			DepthTest.GL_LEQUAL,
			PointSprites.OFF,
		
			ColorMask.ON,
			ScissorTest.OFF,                   // use BACK for production
			StencilTest.OFF,
			
			ClearDepth.ONE,
			ClearColor.BLACK,
			FaceCulling.BACK);

	public static final RenderConfigImpl POINT_SPRITES = new RenderConfigImpl(
			Clear.OFF,
			Blending.OFF,
			DepthTest.GL_LEQUAL,
			PointSprites.ON,
		
			ColorMask.ON,
			ScissorTest.OFF,                   // use BACK for production
			StencilTest.OFF,
			
			ClearDepth.ONE,
			ClearColor.BLACK,
			FaceCulling.OFF);

	public static final RenderConfigImpl SPRITE_CLOUD = new RenderConfigImpl(
			Clear.OFF,
			Blending.OFF,
			DepthTest.GL_LEQUAL,
			PointSprites.OFF,

			ColorMask.ON,
			ScissorTest.OFF,
			StencilTest.OFF,
			
			ClearDepth.ONE,
			ClearColor.BLACK,
			FaceCulling.BACK);

	public static final RenderConfigImpl SKYBOX = new RenderConfigImpl(
			Clear.OFF,
			Blending.OFF,
			DepthTest.GL_LEQUAL,
			PointSprites.OFF,

			ColorMask.ON,
			ScissorTest.OFF,
			StencilTest.OFF,

			ClearDepth.ONE,
			ClearColor.BLACK,
			FaceCulling.BACK);

	public static final RenderConfigImpl NULL_CONFIG = new RenderConfigImpl();

	
    public T switchValues(T oldValues);

    public boolean hasValue(RenderProperty property);

}
