package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.SchemeError;
import ch.dabsoft.dabscheme.vm.Value;
import ch.dabsoft.dabscheme.vm.Values;

import java.util.HashMap;

public class PrimitiveDictPut extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 3) throw new IllegalStateException("dict-put expected 3 arguments, but got " + arguments.length);
        HashMap dict = Value.asDict(arguments[0]);
        String key;
        if (Value.isString(arguments[1])) key = new String(Value.asString(arguments[1]));
        else if (Value.isSymbol(arguments[1])) key = Value.asSymbol(arguments[1]);
        else throw new SchemeError(Value.intern("dict-put"), "Key must be string or symbol");
        dict.put(key, arguments[2]);
        return new Values();
    }

    @Override
    public String toString() {
        return "#<primitive:dict-put>";
    }

}
