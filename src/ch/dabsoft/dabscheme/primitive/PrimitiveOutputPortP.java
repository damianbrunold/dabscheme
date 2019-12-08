package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveOutputPortP extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("output-port? expected 1 argument, but got " + arguments.length);
        return Value.isOutputPort(arguments[0]);
    }

    @Override
    public String toString() {
        return "#<primitive:output-port?>";
    }

}
