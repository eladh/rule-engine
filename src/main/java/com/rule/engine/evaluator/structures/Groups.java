
package com.rule.engine.evaluator.structures;

/**
 *
 */
public abstract class Groups {

    private Groups() {
    }

    public static <First, Second> Pair<First, Second> pair(First first, Second second) {
        return new Pair<First, Second>(first, second);
    }

    public static <First, Second,Third> Trio<First, Second,Third> trio(First first, Second second,Third third) {
        return new Trio<First, Second, Third>(first, second, third);
    }

}