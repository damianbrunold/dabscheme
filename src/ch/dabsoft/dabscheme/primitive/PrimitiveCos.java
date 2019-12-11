package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;

public class PrimitiveCos extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("cos expected 1 argument, but got " + arguments.length);
        return Math.cos(toReal(arguments[0]));
    }

    @Override
    public String toString() {
        return "#<primitive:cos>";
    }

}
