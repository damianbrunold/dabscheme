package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveInstructionOpcode extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length != 1) throw new IllegalStateException("instruction-opcode expected 1 argument, but got " + arguments.length);
        return Value.intern(Value.asInstruction(arguments[0]).opcode.toString());
    }

    @Override
    public String toString() {
        return "#<primitive:instruction-opcode>";
    }

}
