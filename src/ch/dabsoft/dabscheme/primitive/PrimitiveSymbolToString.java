package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveSymbolToString extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("symbol->string expected 1 argument, but got " + arguments.length);
        return Value.asSymbol(arguments[0]).toCharArray();
    }

    @Override
    public String toString() {
        return "#<primitive:symbol->string>";
    }

}
