package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveGetEnv extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("get-env expected 1 argument, but got " + arguments.length);
        return Value.asLambda(arguments[0]).env;
    }

    @Override
    public String toString() {
        return "#<primitive:get-env>";
    }

}
