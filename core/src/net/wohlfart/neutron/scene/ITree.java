package net.wohlfart.neutron.scene;


public interface ITree<T> extends Iterable<ITree<T>>{

    T getValue();

    ITree<T> add(T value);

    void remove(T value);

}
