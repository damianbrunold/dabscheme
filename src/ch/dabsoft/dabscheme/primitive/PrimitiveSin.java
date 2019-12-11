package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;

public class PrimitiveSin extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("sin expected 1 argument, but got " + arguments.length);
        return Math.sin(toReal(arguments[0]));
    }

    @Override
    public String toString() {
        return "#<primitive:sin>";
    }

}
