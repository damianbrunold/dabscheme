package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.*;

import java.util.List;

public class PrimitiveSetCodeB extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 2) throw new IllegalStateException("set-code! expected 2 arguments, but got " + arguments.length);
        Lambda fn = Value.asLambda(arguments[0]);
        fn.code = (List<Instruction>) arguments[1];
        return new Values();
    }

    @Override
    public String toString() {
        return "#<primitive:set-code!>";
    }

}
