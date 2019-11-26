package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveCdr extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("cdr expected 1 argument, but got " + arguments.length);
        return Value.asPair(arguments[0]).cdr;
    }

    @Override
    public String toString() {
        return "#<primitive:cdr>";
    }

}
