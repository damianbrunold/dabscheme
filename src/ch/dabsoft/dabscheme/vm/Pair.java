package ch.dabsoft.dabscheme.vm;

import java.util.List;

public class Pair {
    public Object car;
    public Object cdr;

    public Pair() {}

    public Pair(Object car, Object cdr) {
        this.car = car;
        this.cdr = cdr;
    }

    public static Pair list(Object... values) {
        Pair result = Value.NIL;
        for (int i = values.length - 1; i >= 0; i--) {
            result = new Pair(values[i], result);
        }
        return result;
    }

    public static Pair list2(Object first, Object... rest) {
        return new Pair(first, list(rest));
    }

    public static void appendToList(Pair list, List<Object> collect) {
        while (list != Value.NIL) {
            collect.add(list.car);
            list = Value.asPair(list.cdr);
        }
    }

    public int length() {
        int result = 0;
        Pair p = this;
        while (p != Value.NIL) {
            result++;
            p = Value.asPair(p.cdr);
        }
        return result;
    }

    public Object nthCdr(int n) {
        Object result = this;
        while (n > 0) {
            result = Value.asPair(result).cdr;
            n--;
        }
        return result;
    }

    public Object nth(int n) {
        return Value.asPair(nthCdr(n)).car;
    }

    public Object first() { return car; }
    public Object second() { return Value.asPair(cdr).car; }
    public Object third() { return nth(2); }
    public Object fourth() { return nth(3); }
    public Object fifth() { return nth(4); }
    public Object sixth() { return nth(5); }
    public Object seventh() { return nth(6); }
    public Object eight() { return nth(7); }
    public Object ninth() { return nth(8); }
    public Object tenth() { return nth(9); }

    @Override
    public String toString() {
        return Value.printRep(this);
    }
}
