package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Pair;
import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.SchemeError;
import ch.dabsoft.dabscheme.vm.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PrimitiveDictKeys extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 2) throw new IllegalStateException("dict-keys expected 1 argument, but got " + arguments.length);
        HashMap dict = Value.asDict(arguments[0]);
        List<char[]> keys = new ArrayList<>();
        for (Object key : dict.keySet()) {
            keys.add(((String) key).toCharArray());
        }
        return Pair.list(keys.toArray());
    }

    @Override
    public String toString() {
        return "#<primitive:dict-keys";
    }

}
