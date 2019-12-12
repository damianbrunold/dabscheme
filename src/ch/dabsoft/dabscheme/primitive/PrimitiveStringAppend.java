package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveStringAppend extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        StringBuilder result = new StringBuilder();
        for (Object argument : arguments) {
            result.append(Value.asString(argument));
        }
        return result.toString().toCharArray();
    }

    @Override
    public String toString() {
        return "#<primitive:string-append>";
    }

}
