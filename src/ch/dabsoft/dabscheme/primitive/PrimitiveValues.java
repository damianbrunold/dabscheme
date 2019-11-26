package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Values;

public class PrimitiveValues extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length == 1) return arguments[0];
        Values result = new Values();
        result.values = arguments; // TODO maybe need to copy?
        return result;
    }

    @Override
    public String toString() {
        return "#<primitive:values>";
    }

}
