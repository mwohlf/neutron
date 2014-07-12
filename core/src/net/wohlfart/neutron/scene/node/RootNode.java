package net.wohlfart.neutron.scene.node;

import java.util.Iterator;

import net.wohlfart.neutron.scene.IGraph.INode;
import net.wohlfart.neutron.scene.IRenderContext;
import net.wohlfart.neutron.scene.ITree;
import net.wohlfart.neutron.scene.graph.ISortToken;
import net.wohlfart.neutron.scene.util.ShaderLoader;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

public class RootNode implements INode {

	public static final String ROOT = "root";

	private ShaderProgram shader;
	private Matrix4 matrix = new Matrix4();

	public RootNode() {
		this.shader = ShaderLoader.load("root");
	}

	@Override
	public String getId() {
		return ROOT;
	}

	@Override
	public ISortToken getSortToken() {
		return ISortToken.NEG_INF_TOKEN;
	}

	@Override
	public Matrix4 getModel2World() {
		return matrix;
	}
	
	@Override
	public void render(IRenderContext ctx, Iterable<ITree<INode>> children) {
	    ctx.setRenderConfig(IRenderConfig.CLEAR);
	    ctx.begin(shader);
		shader.setUniformMatrix("u_worldToClip", ctx.getCamera().getViewMatrix());
	    Iterator<ITree<INode>> iter = children.iterator();
		while (iter.hasNext()) {
			final ITree<INode> child = iter.next();
			child.getValue().render(ctx, child);
		}
		ctx.end();
	}




}
