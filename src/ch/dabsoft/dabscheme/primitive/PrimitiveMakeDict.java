package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.*;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class PrimitiveMakeDict extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 0) throw new IllegalStateException("make-dict expected 0 arguments, but got " + arguments.length);
        return new HashMap();
    }

    @Override
    public String toString() {
        return "#<primitive:make-dict>";
    }

}
