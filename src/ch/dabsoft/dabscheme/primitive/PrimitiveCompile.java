package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Compiler;
import ch.dabsoft.dabscheme.vm.Globals;
import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveCompile extends Primitive {

    private Globals globals;

    public PrimitiveCompile(Globals globals) {
        this.globals = globals;
    }

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("compile expected 1 argument, but got " + arguments.length);
        Compiler compiler = new Compiler(globals);
        Object result = compiler.compile(arguments[0]);
        return result;
    }

    @Override
    public String toString() {
        return "#<primitive:compile>";
    }

}
