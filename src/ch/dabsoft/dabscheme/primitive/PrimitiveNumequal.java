package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveNumequal extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        Object current = arguments[0];
        for (int i = 1; i < arguments.length; i++) {
            Object next = arguments[i];
            if (Value.isInteger(current) && Value.isInteger(next)) {
                if (!Value.asInteger(current).equals(Value.asInteger(next))) return false;
            } else if (Value.isReal(current) && Value.isReal(next)) {
                if (!Value.asReal(current).equals(Value.asReal(next))) return false;
            } else {
                Object a = toIntegerIfPossible(current);
                Object b = toIntegerIfPossible(next);
                if (Value.isInteger(a) && Value.isInteger(b)) {
                    if (!Value.asInteger(a).equals(Value.asInteger(b))) return false;
                } else {
                    if (toReal(a) != toReal(b)) return false;
                }
            }
            current = next;
        }
        return true;
    }

    @Override
    public String toString() {
        return "#<primitive:=>";
    }

}
