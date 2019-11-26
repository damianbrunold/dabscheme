package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Pair;
import ch.dabsoft.dabscheme.vm.Primitive;

public class PrimitiveCons extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 2) throw new IllegalStateException("cons expected 2 arguments, but got " + arguments.length);
        return new Pair(arguments[0], arguments[1]);
    }

    @Override
    public String toString() {
        return "#<primitive:cons>";
    }

}
