package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;

public class PrimitiveAcos extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("acos expected 1 argument, but got " + arguments.length);
        return Math.acos(toReal(arguments[0]));
    }

    @Override
    public String toString() {
        return "#<primitive:acos>";
    }

}
