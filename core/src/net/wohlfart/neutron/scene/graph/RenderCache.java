package net.wohlfart.neutron.scene.graph;

import java.util.Collections;

import net.wohlfart.neutron.scene.IGraph.INode;
import net.wohlfart.neutron.scene.IRenderCache;
import net.wohlfart.neutron.scene.ITree;


//
public class RenderCache implements IRenderCache {

    private final TreeImpl<INode> root;

    private final NodeSortStrategy<INode> sorter = new NodeSortStrategy<INode>();


    public RenderCache(INode root) {
		this.root = new TreeImpl<INode>(root);
	}

	@Override
    public ITree<INode> getRoot() {
        return root;
    }

    @Override
    public ITree<INode> add(INode command) {
        return root.add(command);
    }

    @Override
    public void reOrder() {
        Collections.sort(root.children, sorter);
    }

    @Override
    public void remove(INode node) {
        root.remove(node);
    }

}
