package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;
import ch.dabsoft.dabscheme.vm.Values;

public class PrimitiveSetCdrB extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 2) throw new IllegalStateException("set-cdr! expected 2 arguments, but got " + arguments.length);
        Value.asPair(arguments[0]).cdr = arguments[1];
        return new Values();
    }

    @Override
    public String toString() {
        return "#<primitive:set-cdr!>";
    }

}
