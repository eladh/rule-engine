package com.rule.engine.evaluator.structures;

import java.io.Serializable;

public class Pair<First, Second> implements Serializable {

    private final First first;
    private final Second second;

    private final boolean useIdentityHash;

    public Pair(final First first, final Second second) {
        this.first = first;
        this.second = second;
        useIdentityHash = false;
    }

    public Pair(final First first, final Second second, final boolean useIdentityHash) {
        this.first = first;
        this.second = second;
        this.useIdentityHash = useIdentityHash;
    }

    public First getFirst() {
        return first;
    }

    public Second getSecond() {
        return second;
    }

    @Override
    public boolean equals(final Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (!(otherObject instanceof Pair)) {
            return false;
        }

        final Pair<?, ?> pair = (Pair<?, ?>) otherObject;

        if (first != null ? !first.equals(pair.getFirst()) : pair.getFirst() != null) {
            return false;
        }
        return !(second != null ? (!second.equals(pair.getSecond())) : (pair.getSecond() != null));
    }

    @Override
    public int hashCode() {
        int result = getHashFactor(first);
        result *= 29;
        result += getHashFactor(second);
        return result;
    }

    private int getHashFactor(final Object hashable) {
        //default value is 0, applicable for null objects:
        int hashFactor = 0;
        if (hashable != null) {
            if (useIdentityHash) {
                hashFactor = System.identityHashCode(hashable);
            } else {
                hashFactor = hashable.hashCode();
            }
        }
        return hashFactor;
    }

    @Override
    public String toString() {
        return "<" + getFirst() + ", " + getSecond() + ">";
    }

    public String getStringSeparateByComma() {
        return getFirst() + ", " + getSecond();
    }
}
