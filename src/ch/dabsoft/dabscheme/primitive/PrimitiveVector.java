package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrimitiveVector extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        List<Object> result = new ArrayList<>();
        Collections.addAll(result, arguments);
        return result;
    }

    @Override
    public String toString() {
        return "#<primitive:vector>";
    }

}
