package net.wohlfart.neutron.scene.node;

import java.util.Iterator;

import net.wohlfart.neutron.scene.IGraph;
import net.wohlfart.neutron.scene.IGraph.INode;
import net.wohlfart.neutron.scene.IRenderContext;
import net.wohlfart.neutron.scene.ITree;
import net.wohlfart.neutron.scene.graph.NodeSortStrategy;
import net.wohlfart.neutron.scene.graph.NodeSortStrategy.ISortToken;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

public class Root implements INode {

	public static final String ROOT = "root";

	private IGraph graph;
	private ShaderProgram shader;
	private Matrix4 matrix = new Matrix4();

	public Root(IGraph graph) {
		this.graph = graph;
		loadShader("root");
	}

	@Override
	public String getId() {
		return ROOT;
	}

	@Override
	public ISortToken getSortToken() {
		return NodeSortStrategy.NEG_INF_TOKEN;
	}


	@Override
	public Matrix4 getModel2World() {
		return matrix;
	}
	@Override
	public void render(IRenderContext ctx, Iterable<ITree<INode>> children) {
		Gdx.gl.glClearColor(0, 1, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT
	    		  | GL20.GL_DEPTH_BUFFER_BIT
	    		  | GL20.GL_STENCIL_BUFFER_BIT);

	    ctx.begin(shader);
		shader.setUniformMatrix("u_worldToClip", graph.getViewMatrix());
	    Iterator<ITree<INode>> iter = children.iterator();
		while (iter.hasNext()) {
			final ITree<INode> child = iter.next();
			child.getValue().render(ctx, child);
		}
		ctx.end();
	}


	private void loadShader(String name) {
		String vertexShader = Gdx.files.internal("shader/" + name + ".vert").readString("UTF-8");
		String fragmentShader = Gdx.files.internal("shader/" + name + ".frag").readString("UTF-8");
		shader = new ShaderProgram(vertexShader, fragmentShader);
		if (!shader.isCompiled()) {
			throw new IllegalStateException("shader not compiled: " + shader.getLog());
		}
	}

}
