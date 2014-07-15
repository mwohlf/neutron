package net.wohlfart.neutron.scene.node;

import net.wohlfart.neutron.scene.util.EnumWeights;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;


/**
 *  see: "3D Engine Design for Virtual Globes"
 */
public class RenderConfigImpl implements IRenderConfig<RenderConfigImpl> {

	// need to setup the weights first since they are used in the constructor...
	private static final EnumWeights WEIGHTS = new EnumWeights(
			Clear.class,
			Blending.class,   // opaque objects first front to back, then the transparent back to front
			DepthTest.class,
			PointSprites.class,
			// probably never change:
			ColorMask.class,
			ScissorTest.class,
			StencilTest.class,
			// values should never change:
			ClearDepth.class,
			ClearColor.class,
			FaceCulling.class
			);

	private final int hash;


	private final Clear clear;

	private final Blending blending;

	private final DepthTest depthTest;

	private final PointSprites pointSprites;

	private final ColorMask colorMask;

	private final ScissorTest scissorTest;

	private final StencilTest stencilTest;

	private final ClearDepth clearDepth;

	private final ClearColor clearColor;

	private final FaceCulling faceCulling;




	RenderConfigImpl() {
		this.clear = null;
		this.blending = null;
		this.depthTest = null;
		this.pointSprites = null;
		
		this.colorMask = null;
		this.scissorTest = null;
		this.stencilTest = null;
		
		this.clearDepth = null;
		this.clearColor = null;
		this.faceCulling = null;
		hash = Integer.MIN_VALUE;
	}

	RenderConfigImpl(
			Clear clear,
			Blending blending,
			DepthTest depthTest,
			PointSprites pointSprites,

			ColorMask colorMask,
			ScissorTest scissorTest,
			StencilTest stencilTest,
			
			ClearDepth clearDepth,
			ClearColor clearColor,
			FaceCulling faceCulling) {

		this.clear = clear;
		this.blending = blending;
		this.clearColor = clearColor;
		this.clearDepth = clearDepth;
		this.depthTest = depthTest;
		this.pointSprites = pointSprites;
		this.colorMask = colorMask;
		this.faceCulling = faceCulling;
		this.scissorTest = scissorTest;
		this.stencilTest = stencilTest;

		hash = WEIGHTS.getWeightFor(
				clear,
				blending,
				clearColor,
				clearDepth,
				pointSprites,
				colorMask,
				depthTest,
				faceCulling,
				scissorTest,
				stencilTest);
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if ((object == null) || (this.getClass() != object.getClass())) {
			return false;
		}
		RenderConfigImpl that = (RenderConfigImpl) object;
		return this.hash == that.hash;
	}

	@Override
	public RenderConfigImpl switchValues(RenderConfigImpl oldVal) {
		if (oldVal.clear != this.clear) {
			clear.switchValue();
		}
		if (oldVal.blending != this.blending) {
			blending.switchValue();
		}
		if (oldVal.clearColor != this.clearColor) {
			clearColor.switchValue();
		}
		if (oldVal.clearDepth != this.clearDepth) {
			clearDepth.switchValue();
		}
		if (oldVal.pointSprites != this.pointSprites) {
			pointSprites.switchValue();
		}
		if (oldVal.colorMask != this.colorMask) {
			colorMask.switchValue();
		}
		if (oldVal.depthTest != this.depthTest) {
			depthTest.switchValue();
		}
		if (oldVal.faceCulling != this.faceCulling) {
			faceCulling.switchValue();
		}
		if (oldVal.scissorTest != this.scissorTest) {
			scissorTest.switchValue();
		}
		if (oldVal.stencilTest != stencilTest) {
			stencilTest.switchValue();
		}
		return this;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [clear=" + clear
	            + ", blending=" + blending
				+ ", clearColor=" + clearColor
				+ ", clearDepth=" + clearDepth
				+ ", colorMask=" + colorMask
				+ ", depthTest=" + depthTest
				+ ", hash=" + hash + "]";
	}


	interface RenderProperty {
		
		boolean isValueIn(RenderConfigImpl that);
		
		void switchValue();
		
	}

	public enum Clear implements RenderProperty {
		OFF {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.clear == OFF);
			}
			@Override
			public void switchValue() {
				// do nothing
			}
		},
		ON {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.clear == ON);
			}
			@Override
			public void switchValue() {
			    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT
			    		  | GL20.GL_DEPTH_BUFFER_BIT
			    		  | GL20.GL_STENCIL_BUFFER_BIT);
			}
		}
		
	}


	public enum Blending implements RenderProperty {
		OFF {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.blending == OFF);
			}
			@Override
			public void switchValue() {
				Gdx.gl.glDisable(GL20.GL_BLEND);
			}
		},
		ON {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.blending == ON);
			}
			@Override
			public void switchValue() {
				Gdx.gl.glEnable(GL20.GL_BLEND);
				Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
			}
		}
	}

	public enum ClearColor implements RenderProperty {
		IGNORE {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return true;
			}
			@Override
			public void switchValue() {
				// do nothing
			}
		},
		BLACK {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.clearColor == BLACK);
			}
			@Override
			public void switchValue() {
				Gdx.gl.glClearColor(0f, 0f, 0f, 0.5f);
			}
		},
		GREY {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.clearColor == GREY);
			}
			@Override
			public void switchValue() {
				Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
			}
		},
		BLUE {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.clearColor == BLUE);
			}
			@Override
			public void switchValue() {
				Gdx.gl.glClearColor(0.0f, 0.0f, 0.5f, 0.5f);
			}
		},
		WHITE {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.clearColor == WHITE);
			}
			@Override
			public void switchValue() {
				Gdx.gl.glClearColor(1f, 1f, 1f, 0.5f);
			}
		}
	}

	public enum PointSprites implements RenderProperty {
		ON {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.pointSprites == ON);
			}
			@Override
			public void switchValue() {
				Gdx.gl20.glEnable(GL20.GL_VERTEX_PROGRAM_POINT_SIZE);
			    Gdx.gl20.glEnable(34913); // see: http://badlogicgames.com/forum/viewtopic.php?t=1646&p=9199
			}
		},
		OFF {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.pointSprites == OFF);
			}
			@Override
			public void switchValue() {
				Gdx.gl20.glDisable(GL20.GL_VERTEX_PROGRAM_POINT_SIZE);
				Gdx.gl20.glDisable(34913);
			}	
		}
	}

	public enum ClearDepth implements RenderProperty {
		ONE {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.clearDepth == ONE);
			}
			@Override
			public void switchValue() {
				Gdx.gl.glClearDepthf(1f);
			}
		}
	}

	public enum ColorMask implements RenderProperty {
		ON {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.colorMask == ON);
			}
			@Override
			public void switchValue() {
				Gdx.gl.glColorMask(true, true, true, true);
			}
		}
	}

	public enum DepthTest implements RenderProperty {
		GL_LEQUAL {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.depthTest == GL_LEQUAL);
			}
			@Override
			public void switchValue() {
				Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
				Gdx.gl.glDepthFunc(GL20.GL_LEQUAL);
			}
		},
		GL_LESS {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.depthTest == GL_LESS);
			}
			@Override
			public void switchValue() {
				Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
				Gdx.gl.glDepthFunc(GL20.GL_LESS);

			}
		},
		OFF {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.depthTest == OFF);
			}
			@Override
			public void switchValue() {
				Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
			}
		};
	}

	public enum FaceCulling implements RenderProperty {
		BACK { // the default
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.faceCulling == BACK);
			}
			@Override
			public void switchValue() {
				Gdx.gl.glEnable(GL20.GL_CULL_FACE);
				Gdx.gl.glCullFace(GL20.GL_BACK);
			}
		},
		FRONT {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.faceCulling == FRONT);
			}
			@Override
			public void switchValue() {
				Gdx.gl.glEnable(GL20.GL_CULL_FACE);
				Gdx.gl.glCullFace(GL20.GL_FRONT);

			}
		},
		FRONT_AND_BACK {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.faceCulling == FRONT_AND_BACK);
			}
			@Override
			public void switchValue() {
				Gdx.gl.glEnable(GL20.GL_CULL_FACE);
				Gdx.gl.glCullFace(GL20.GL_FRONT_AND_BACK);

			}
		},
		OFF {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.faceCulling == OFF);
			}
			@Override
			public void switchValue() {
				Gdx.gl.glDisable(GL20.GL_CULL_FACE);
			}
		};
	}

	public enum ScissorTest implements RenderProperty {
		OFF {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.scissorTest == OFF);
			}
			@Override
			public void switchValue() {
				Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
			}
		}
	}

	public enum StencilTest implements RenderProperty {
		OFF {
			@Override
			public boolean isValueIn(RenderConfigImpl that) {
				return (that.stencilTest == OFF);
			}
			@Override
			public void switchValue() {
				Gdx.gl.glDisable(GL20.GL_DEPTH_TEST);
			}
		}
	}

	@Override
	public boolean hasValue(RenderProperty property) {
		property.isValueIn(this);
		return false;
	}

}
