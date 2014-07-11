package net.wohlfart.neutron.scene.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.wohlfart.neutron.scene.ITree;


/**
 * implementing a 2 way navigate-able tree where the tree data structure is
 * responsible for keeping the children/parent relationships not the elements in the tree
 *
 * this class is not thread-safe at all
 */
class TreeImpl<T> implements ITree<T>, Iterable<ITree<T>> {

    protected final T value;

    protected final TreeImpl<T> parent;

    protected final List<TreeImpl<T>> children; // never null, size 0 for a leaf node

    // there is only one instance per tree structure in the root node,
    // each child has a link to the root's removeValues
    private final Set<T> removeValues;

    // exactly one instance per node, handles the remove values before returning a child element
    private final ChildIterator iterator = new ChildIterator();


    TreeImpl(T value) {
        assert value != null;
        this.parent = null;
        this.removeValues = new HashSet<T>();
        this.value = value;
        this.children = new ArrayList<TreeImpl<T>>();
    }

    TreeImpl(TreeImpl<T> parent, T value) {
        assert value != null;
        assert parent != null;
        this.parent = parent;
        this.removeValues = parent.removeValues;
        this.value = value;
        this.children = new ArrayList<TreeImpl<T>>();
    }

    TreeImpl(TreeImpl<T> parent, T value, List<TreeImpl<T>> children) {
        assert value != null;
        assert parent != null;
        assert children != null;
        this.parent = parent;
        this.removeValues = parent.removeValues;
        this.value = value;
        this.children = children;
    }

    // we reuse a single iterator
	@Override
	public Iterator<ITree<T>> iterator() {
        return iterator.reset();
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public ITree<T> add(T value) {
        TreeImpl<T> result = new TreeImpl<T>(this, value);
        children.add(result);
        return result;
    }

    public void removeAll(Collection<? extends T> values) {
        assert values != null;
        for (T value : values) {
            remove(value);
        }
    }

    @Override
    public void remove(T value) {
        removeValues.add(value);
    }

    private class ChildIterator implements Iterator<ITree<T>> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            while (index < children.size() && removeValues.contains(children.get(index).value)) {
                removeValues.remove(children.remove(index).value);
            }
            return index < children.size();
        }

        @Override
        public TreeImpl<T> next() {
            assert index < children.size();
            assert !removeValues.contains(children.get(index).value);
            return children.get(index++);
        }

        Iterator<ITree<T>> reset() {
            index = 0;
            return this;
        }

		@Override
		public void remove() {
			throw new IllegalStateException("remove not supported");
		}

    }

}
