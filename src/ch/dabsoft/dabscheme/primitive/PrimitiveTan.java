package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;

public class PrimitiveTan extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("tan expected 1 argument, but got " + arguments.length);
        return Math.tan(toReal(arguments[0]));
    }

    @Override
    public String toString() {
        return "#<primitive:tan>";
    }

}
