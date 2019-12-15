package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Pair;
import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

import java.util.ArrayList;
import java.util.List;

public class PrimitiveStringSplit extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        // TODO check arg count and types
        String s = new String(Value.asString(arguments[0]));
        String regexp = "[ \\t\\r\\n]+";
        if (arguments.length > 1) regexp = new String(Value.asString(arguments[1]));
        String[] parts = s.split(regexp);
        List<char[]> result = new ArrayList<>();
        for (String part : parts) result.add(part.toCharArray());
        return Pair.list(result.toArray());
    }

    @Override
    public String toString() {
        return "#<primitive:string-split>";
    }

}
