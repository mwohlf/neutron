package net.wohlfart.neutron.scene;

import net.wohlfart.neutron.scene.IGraph.INode;


public interface IRenderCache {

    ITree<INode> getRoot();

    ITree<INode> add(INode node);

    void remove(INode node);

    void reOrder();

}
