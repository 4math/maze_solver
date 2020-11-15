package dip107;

import java.util.Comparator;

public class TupleComparator implements Comparator<Tuple> {

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
