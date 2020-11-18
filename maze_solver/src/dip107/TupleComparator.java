package dip107;

import java.util.Comparator;

// Comparator for Priority Queue
public class TupleComparator implements Comparator<Tuple> {

    // sorts in descending order, so that the first element in queue is with the least f value
    @Override
    public int compare(Tuple o1, Tuple o2) {
        if (o1.f < o2.f) {
            return -1;
        } else if (o1.f > o2.f) {
            return 1;
        }
        return 0;
    }

}
