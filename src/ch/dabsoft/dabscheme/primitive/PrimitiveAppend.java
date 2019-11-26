package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Pair;
import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

import java.util.ArrayList;
import java.util.List;

public class PrimitiveAppend extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < arguments.length - 1; i++) {
            Pair pair = Value.asPair(arguments[i]);
            while (pair != Value.NIL) {
                result.add(pair.car);
                pair = Value.asPair(pair.cdr);
            }
        }
        if (result.isEmpty()) {
            return arguments[arguments.length - 1];
        } else {
            Pair list = Pair.list(result.toArray());
            Pair last = list;
            while (last.cdr != Value.NIL) last = Value.asPair(last.cdr);
            last.cdr = arguments[arguments.length - 1];
            return list;
        }
    }

    @Override
    public String toString() {
        return "#<primitive:append>";
    }

}
