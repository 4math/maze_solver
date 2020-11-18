package dip107;

// Tuple for Priority Queue, in order to sort comparing f value
public class Tuple {

    public final Pair pair;
    public final double f;

    Tuple(Pair pair, double f) {
        this.pair = pair;
        this.f = f;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Tuple other = (Tuple) obj;
        return this.pair.equals(other.pair) && Math.abs(other.f - this.f) <= 1e-8;
    }
}

