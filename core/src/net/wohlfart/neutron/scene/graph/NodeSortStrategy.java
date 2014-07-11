package net.wohlfart.neutron.scene.graph;

import java.io.Serializable;
import java.util.Comparator;



// the final render order should be:
//
// - hud (solid)      \
// - solid           -- (front to back)
// - skybox           /
// - transparent/hud  - (back to front) depth writing off
//
public class NodeSortStrategy<T extends NodeSortStrategy.HasSortToken> implements Comparator<TreeImpl<T>>, Serializable {
    private static final long serialVersionUID = 1L;

    public static final ISortToken POS_INF_TOKEN
    	= new SortToken("POS_INF_TOKEN", Double.POSITIVE_INFINITY, false);

    public static final ISortToken NEG_INF_TOKEN
    	= new SortToken("NEG_INF_TOKEN", Double.NEGATIVE_INFINITY, false);

    public static final ISortToken ZERO_SORT_TOKEN
    	= new SortToken("ZERO_TOKEN", 0, false);

    public interface HasSortToken  {

        ISortToken getSortToken();

    }

    public interface ISortToken {

        boolean isTranslucent();

        double getZOrder();

    }

    public static class SortToken implements ISortToken {
    	final String id;
    	final double zOrder;
    	final boolean translucent;

    	SortToken(String id, double zOrder, boolean translucent) {
    		this.id = id;
    		this.zOrder = zOrder;
    		this.translucent = translucent;
    	}

		@Override
		public boolean isTranslucent() {
			return translucent;
		}

		@Override
		public double getZOrder() {
			return zOrder;
		}

		@Override
        public String toString() {
            return id;
        }
    }

    @Override
    public int compare(TreeImpl<T> left, TreeImpl<T> right) {
        return compare(left.value, right.value);
    }

    private int compare(HasSortToken left, HasSortToken right) {
        return compare(left.getSortToken(), right.getSortToken());
    }

    // neg : left < right
    // pos : left > right
    //  0  : left == right
    private int compare(ISortToken left, ISortToken right) {
        assert null != left;
        assert null != right;

        if (left.isTranslucent() ^ right.isTranslucent()) { // exactly one is transparent, we want to draw the solids first
            return left.isTranslucent()?+10:-10;
        }

        // both are transparent or not
        return Double.compare(left.getZOrder(), right.getZOrder()) * (left.isTranslucent()?-1:+1);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
