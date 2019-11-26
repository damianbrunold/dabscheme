package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveVectorLength extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("vector-length expected 1 argument, but got " + arguments.length);
        return (long) Value.asVector(arguments[0]).size();
    }

    @Override
    public String toString() {
        return "#<primitive:vector-length>";
    }

}
