package net.wohlfart.neutron.scene;

import net.wohlfart.neutron.scene.graph.NodeSortStrategy.HasSortToken;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public interface IGraph {

	public interface IEntity extends IUpdateable {

		/**
		 * implementations should move/rotate and update their nodes
		 */
		@Override
	    public void update(Quaternion rot, Vector3 mov);

		/**
		 * callback from the graph, implementation should register their nodes and store the graph reference
		 * since it is needed in the unregister call
		 */
	    public void register(IGraph graph);

	    /**
	     * implementations should remove nodes from the graph
	     */
	    public void unregister();

	}


	public interface INode extends HasSortToken {

		/**
		 * unique id
		 */
		String getId();

		/**
		 * implementations should setup the render context,
		 * call render on the children and
		 * restore the old render context state
		 */
		void render(IRenderContext renderContext, Iterable<ITree<INode>> children);

		/**
		 * @return the model to world matrix which includes position, rotation and scaling of this node
		 */
		Matrix4 getModel2World();


	}


	void setup(IEntity... entities);

	void add(INode node);

	void add(IEntity entity);

	void remove(INode node);

	void remove(IEntity entity);

	void render(IRenderContext ctx);

	void resizeViewport(int width, int height);

	Matrix4 getViewMatrix();

}