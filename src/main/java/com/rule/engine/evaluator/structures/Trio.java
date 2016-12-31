package com.rule.engine.evaluator.structures;

import java.io.Serializable;

public class Trio<First, Second, Third> implements Serializable {

    private final First _first;
    private final Second _second;
    private final Third _third;

    public Trio(First first, Second second, Third third) {
        _first = first;
        _second = second;
        _third = third;
    }

    public First getFirst() {
        return _first;
    }

    public Second getSecond() {
        return _second;
    }

    public Third getThird() {
        return _third;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Trio)) {
            return false;
        }

        final Trio trio = (Trio) o;

        if (_first != null ? !_first.equals(trio._first) : trio._first != null) {
            return false;
        }
        if (_second != null ? !_second.equals(trio._second) : trio._second != null) {
            return false;
        }
        if (_third != null ? !_third.equals(trio._third) : trio._third != null) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        int result = (_first != null ? _first.hashCode() : 0);
        result = 29 * result + (_second != null ? _second.hashCode() : 0);
        result = 29 * result + (_third != null ? _third.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "<" + getFirst() + ", " + getSecond() + ", " + getThird() + ">";
    }

    public String getStringSeparateByComma() {
        return getFirst() + ", " + getSecond() + ", " + getThird();
    }

    public String getStringSeparateByCommaWithoutSpaces() {
        return getFirst() + "," + getSecond() + "," + getThird();
    }
}

