package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.*;

import java.io.IOException;
import java.io.Writer;

public class PrimitiveGetCode extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("get-code expected 1 argument, but got " + arguments.length);
        return Value.asLambda(arguments[0]).code;
    }

    @Override
    public String toString() {
        return "#<primitive:get-code>";
    }

}
