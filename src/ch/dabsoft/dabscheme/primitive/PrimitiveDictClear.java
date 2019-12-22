package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.SchemeError;
import ch.dabsoft.dabscheme.vm.Value;
import ch.dabsoft.dabscheme.vm.Values;

import java.util.HashMap;

public class PrimitiveDictClear extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("dict-clear expected 1 argument, but got " + arguments.length);
        Value.asDict(arguments[0]).clear();
        return new Values();
    }

    @Override
    public String toString() {
        return "#<primitive:dict-clear";
    }

}
