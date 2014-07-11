package net.wohlfart.neutron.scene.graph;

import java.util.HashSet;
import java.util.Set;

import net.wohlfart.neutron.scene.ICamera;
import net.wohlfart.neutron.scene.IGraph;
import net.wohlfart.neutron.scene.IRenderCache;
import net.wohlfart.neutron.scene.IRenderContext;
import net.wohlfart.neutron.scene.ITree;
import net.wohlfart.neutron.scene.IUpdateable;
import net.wohlfart.neutron.scene.node.RootNode;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

public class Graph implements IGraph, IUpdateable {

	private final Camera camera = new Camera();


	/** ordered list of render nodes */
	private final IRenderCache renderCache = new RenderCache(new RootNode());

	/** set or semantic entities in this scene graph */
	private final Set<IEntity> semanticView = new HashSet<IEntity>();

	// this.spatialView = new TreeImpl<>(new BoundingVolumeSphere(new Vector3d(), 0));

	@Override
	public void setup(IEntity... entities) {
		for (IEntity entity : entities) {
			entity.register(this);
		}
	}

	/**
	 * incoming call for move or rotate events that would normally go to a camera
	 * since the camera is fixed we have to move or rotate the whole scene
	 */
	@Override
	public void update(Quaternion rot, Vector3 mov) {
		for (IEntity entity : semanticView) {
			entity.update(rot, mov);
		}
	}

	@Override
	public void render(IRenderContext ctx) {
		INode root = renderCache.getRoot().getValue();
		root.render(ctx, renderCache.getRoot());
	}

	@Override
	public void add(INode node) {
		this.renderCache.add(node);
	}

	@Override
	public void add(IEntity entity) {
		this.semanticView.add(entity);
	}

	@Override
	public void remove(INode node) {
		this.renderCache.remove(node);

	}

	@Override
	public void remove(IEntity entity) {
		this.semanticView.remove(entity);

	}

	public ITree<INode> getRootNode() {
		renderCache.reOrder();
		return renderCache.getRoot();
	}

	@Override
	public ICamera getCamera() {
		return camera;
	}

}
