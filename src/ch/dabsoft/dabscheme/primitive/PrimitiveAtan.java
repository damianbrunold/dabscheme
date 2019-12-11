package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;

public class PrimitiveAtan extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1 && arguments.length != 2) throw new IllegalStateException("atan expected 1 or 2 arguments, but got " + arguments.length);
        if (arguments.length == 1) return Math.atan(toReal(arguments[0]));
        else return Math.atan2(toReal(arguments[0]), toReal(arguments[1]));
    }

    @Override
    public String toString() {
        return "#<primitive:atan>";
    }

}
