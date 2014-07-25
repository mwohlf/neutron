package net.wohlfart.neutron.scene.node;

import net.wohlfart.neutron.scene.IGraph.INode;
import net.wohlfart.neutron.scene.IRenderContext;
import net.wohlfart.neutron.scene.ITree;
import net.wohlfart.neutron.scene.graph.ISortToken;
import net.wohlfart.neutron.shader.ShaderLoader;
import net.wohlfart.neutron.util.Vector3d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

public class ObjModelNode implements INode {
		
	private final Matrix4 matrix = new Matrix4();

	private final ISortToken sortToken = new SortToken();

	private final Vector3d position;

	private final ModelInstance model;
	
	private final String id;
	
	private final String shaderName;

	private final ShaderProgram shader;
	
	
	public ObjModelNode(String id, String shaderName, String modelPath, Vector3d position) {
		this.model = new ModelInstance(new ObjLoader().loadModel(Gdx.files.internal(modelPath)));
		this.id = id;
		this.shaderName = shaderName;
		this.shader = ShaderLoader.load(shaderName);
		this.position = position;
	}

	@Override
	public ISortToken getSortToken() {
		return sortToken;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void render(IRenderContext ctx, Iterable<ITree<INode>> children) {
		ctx.begin(shader);
		ctx.setRenderConfig(IRenderConfig.DEFAULT_3D);
		shader.setUniformMatrix("u_worldToClip", ctx.getCamera().getViewMatrix());
		shader.setUniformMatrix("u_modelToWorld", matrix);
		ctx.render(model);
		ctx.end();
	}

	@Override
	public Matrix4 getModel2World() {
		return matrix;
	}

	private class SortToken implements ISortToken {

		@Override
		public boolean isTranslucent() {
			return false;
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

}
