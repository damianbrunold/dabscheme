package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveString extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        StringBuilder result = new StringBuilder();
        for (Object argument : arguments) {
            result.append(Value.asChar(argument));
        }
        return result.toString().toCharArray();
    }

    @Override
    public String toString() {
        return "#<primitive:string>";
    }

}
