package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;
import ch.dabsoft.dabscheme.vm.Values;

public class PrimitiveVectorSetB extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 3) throw new IllegalStateException("vector-set! expected 3 arguments, but got " + arguments.length);
        Value.asVector(arguments[0]).set((int) (long) arguments[1], arguments[2]);
        return new Values();
    }

    @Override
    public String toString() {
        return "#<primitive:vector-set!>";
    }

}
