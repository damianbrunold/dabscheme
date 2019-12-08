package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.*;

public class PrimitiveExit extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 0) {
            throw new SchemeError("exit", "expected 0 arguments, but got " + arguments.length);
        }
        throw new SchemeExit();
    }

    @Override
    public String toString() {
        return "#<primitive:exit>";
    }

}
