package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;
import ch.dabsoft.dabscheme.vm.Values;

public class PrimitiveDictSize extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("dict-size expected 1 argument, but got " + arguments.length);
        return (long) Value.asDict(arguments[0]).size();
    }

    @Override
    public String toString() {
        return "#<primitive:dict-size";
    }

}
