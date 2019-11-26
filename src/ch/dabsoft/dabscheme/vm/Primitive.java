package ch.dabsoft.dabscheme.vm;

public abstract class Primitive {
    public abstract Object apply(Object[] arguments);

    protected double toReal(Object value) {
        if (value instanceof Long) return (double) (Long) value;
        return (Double) value;
    }

    protected boolean allIntegers(Object[] args) {
        for (Object arg : args) if (!Value.isInteger(arg)) return false;
        return true;
    }

    protected Object toIntegerIfPossible(Object value) {
        if (Value.isInteger(value)) return value;
        double val = (double) value;
        if (val == Math.floor(val)) return (long) val;
        return value;
    }

}
