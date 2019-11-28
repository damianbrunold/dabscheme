package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;
import ch.dabsoft.dabscheme.vm.Values;

public class PrimitiveSetCarB extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 2) throw new IllegalStateException("set-car! expected 2 arguments, but got " + arguments.length);
        Value.asPair(arguments[0]).car = arguments[1];
        return new Values();
    }

    @Override
    public String toString() {
        return "#<primitive:set-car!>";
    }

}
