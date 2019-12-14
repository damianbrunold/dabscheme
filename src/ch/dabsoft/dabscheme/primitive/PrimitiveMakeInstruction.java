package ch.dabsoft.dabscheme.primitive;

import ch.dabsoft.dabscheme.vm.Instruction;
import ch.dabsoft.dabscheme.vm.Opcode;
import ch.dabsoft.dabscheme.vm.Primitive;
import ch.dabsoft.dabscheme.vm.Value;

public class PrimitiveMakeInstruction extends Primitive {

    @Override
    public Object apply(Object[] arguments) {
        if (arguments.length == 0 || arguments.length > 3) throw new IllegalStateException("make-instruction expected 1 to 3 arguments, but got " + arguments.length);
        Instruction instruction = new Instruction(Opcode.valueOf(Value.asSymbol(arguments[0])));
        if (arguments.length > 1) instruction.arg1 = arguments[1];
        if (arguments.length > 2) instruction.arg2 = arguments[2];
        return instruction;
    }

    @Override
    public String toString() {
        return "#<primitive:make-instruction>";
    }

}
