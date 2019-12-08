package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.*;

import java.io.File;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.List;

public class PrimitiveLoad extends Primitive {

    private Globals globals;

    public PrimitiveLoad(Globals globals) {
        this.globals = globals;
    }

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("load 1 argument, but got " + arguments.length);
        try {
            Scheme scheme = new Scheme(globals);
            return scheme.evalFile(new File(new String(Value.asString(arguments[0]))));
        } catch (IOException e) {
            throw new SchemeError("load", "io failure: ~s", e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "#<primitive:load>";
    }

}
