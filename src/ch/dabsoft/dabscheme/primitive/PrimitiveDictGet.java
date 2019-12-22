package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.SchemeError;
import ch.dabsoft.dabscheme.vm.Value;
import ch.dabsoft.dabscheme.vm.Values;

import java.util.HashMap;

public class PrimitiveDictGet extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 2) throw new IllegalStateException("dict-get expected 2 arguments, but got " + arguments.length);
        HashMap dict = Value.asDict(arguments[0]);
        String key;
        if (Value.isString(arguments[1])) key = new String(Value.asString(arguments[1]));
        else if (Value.isSymbol(arguments[1])) key = Value.asSymbol(arguments[1]);
        else throw new SchemeError(Value.intern("dict-get"), "Key must be string or symbol");
        Object value = dict.get(key);
        if (value == null) throw new SchemeError(Value.intern("dict-get"), "Key ~s not found", key);
        return value;
    }

    @Override
    public String toString() {
        return "#<primitive:dict-get";
    }

}
