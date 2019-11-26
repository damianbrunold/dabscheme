package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveVectorRef extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 2) throw new IllegalStateException("vector-ref expected 2 arguments, but got " + arguments.length);
        return Value.asVector(arguments[0]).get((int) (long) arguments[1]);
    }

    @Override
    public String toString() {
        return "#<primitive:vector-ref>";
    }

}
