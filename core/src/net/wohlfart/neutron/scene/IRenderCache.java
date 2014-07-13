package net.wohlfart.neutron.scene;

import net.wohlfart.neutron.scene.IGraph.INode;

/**
 * list of nodes to be rendered in a particular order
 *
 * @author mwohlf
 */
public interface IRenderCache {

    ITree<INode> getRoot();

    ITree<INode> add(INode node);

    void remove(INode node);

    void reOrder();

}
