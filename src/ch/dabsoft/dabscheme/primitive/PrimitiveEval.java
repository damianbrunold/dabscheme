package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.*;

import java.io.IOException;

public class PrimitiveEval extends Primitive {

    private Globals globals;

    public PrimitiveEval(Globals globals) {
        this.globals = globals;
    }

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 2) throw new IllegalStateException("eval expected 2 arguments, but got " + arguments.length);
        try {
            Pair envspec = Value.asPair(arguments[1]);
            if (envspec.car == Value.intern("interaction")) {
                Scheme scheme = new Scheme(globals);
                return scheme.eval(arguments[0]);
            } else {
                // TODO this is inefficient as it loads all libraries. Better copy a preloaded globals?!
                // TODO handle different versions!
                long version = Value.asInteger(envspec.second());
                Scheme scheme = new Scheme();
                scheme.loadLibraries();
                return scheme.eval(arguments[0]);
            }
        } catch (IOException e) {
            return Value.F;
        }
    }

    @Override
    public String toString() {
        return "#<primitive:eval>";
    }

}
