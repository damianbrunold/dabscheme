package ch.dabsoft.dabscheme.vm;

import ch.dabsoft.dabscheme.primitive.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Globals {

    private Map<String, Object> globals = new HashMap<>();

    public Globals(Globals copyfrom) {
        this.globals.putAll(copyfrom.globals);
    }

    public Globals() {
        bind("nil", Value.NIL);
        bind("+", new PrimitiveAdd());
        bind("*", new PrimitiveMul());
        bind("-", new PrimitiveSub());
        bind("/", new PrimitiveDiv());
        bind("=", new PrimitiveNumequal());
        bind("<", new PrimitiveNumless());
        bind("<=", new PrimitiveNumlessequal());
        bind(">", new PrimitiveNumgreater());
        bind(">=", new PrimitiveNumgreaterequal());
        bind("eq?", new PrimitiveEqP());
        bind("cons", new PrimitiveCons());
        bind("car", new PrimitiveCar());
        bind("cdr", new PrimitiveCdr());
        bind("set-car!", new PrimitiveSetCarB());
        bind("set-cdr!", new PrimitiveSetCdrB());
        bind("append", new PrimitiveAppend());
        bind("pair?", new PrimitivePairP());
        bind("symbol?", new PrimitiveSymbolP());
        bind("boolean?", new PrimitiveBooleanP());
        bind("char?", new PrimitiveCharP());
        bind("string?", new PrimitiveStringP());
        bind("integer?", new PrimitiveIntegerP());
        bind("real?", new PrimitiveRealP());
        bind("vector?", new PrimitiveVectorP());
        bind("lambda?", new PrimitiveLambdaP());
        bind("primitive?", new PrimitivePrimitiveP());
        bind("sqrt", new PrimitiveSqrt());
        bind("gensym", new PrimitiveGensym());
        bind("compile", new PrimitiveCompile(this));
        bind("vector-length", new PrimitiveVectorLength());
        bind("vector-ref", new PrimitiveVectorRef());
        bind("vector", new PrimitiveVector());
        bind("vector-set!", new PrimitiveVectorSetB());
        bind("string-length", new PrimitiveStringLength());
        bind("string-ref", new PrimitiveStringRef());
        bind("char=?", new PrimitiveCharEqP());
        bind("quotient", new PrimitiveQuotient());
        bind("error", new PrimitiveError());
        bind("values", new PrimitiveValues());
        bind("call-with-current-continuation", getCallCC());
        bind("apply", getApply());
        bind("call-with-values", getCallWithValues());
        bind("eval", new PrimitiveEval(this));
    }

    private Object getCallCC() {
        List<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(Opcode.ARGS, 1));
        instructions.add(new Instruction(Opcode.CC));
        instructions.add(new Instruction(Opcode.LVAR, 0, 0));
        instructions.add(new Instruction(Opcode.CALLJ, 1));
        Lambda callcc = new Lambda(Value.NIL, instructions);
        callcc.name = "call-with-current-continuation";
        return callcc;
    }

    private Object getApply() {
        List<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(Opcode.ARGSDOT, 1));
        instructions.add(new Instruction(Opcode.FLATTEN_APPLY, 0, 1));
        instructions.add(new Instruction(Opcode.LVAR, 0, 0));
        instructions.add(new Instruction(Opcode.CALLJ, -1));
        Lambda apply = new Lambda(Value.NIL, instructions);
        apply.name = "apply";
        return apply;
    }

    private Object getCallWithValues() {
        List<Instruction> instructions = new ArrayList<>();
        instructions.add(new Instruction(Opcode.ARGS, 2));
        instructions.add(new Instruction(Opcode.SAVE, 4));
        instructions.add(new Instruction(Opcode.LVAR, 0, 0));
        instructions.add(new Instruction(Opcode.CALLJ, 0));
        instructions.add(new Instruction(Opcode.FLATTEN_MULTVALS, 0, 1));
        instructions.add(new Instruction(Opcode.LVAR, 0, 1));
        instructions.add(new Instruction(Opcode.CALLJ, -1));
        Lambda apply = new Lambda(Value.NIL, instructions);
        apply.name = "call-with-values";
        return apply;
    }

    public boolean isBound(String symbol) {
        return globals.containsKey(Value.intern(symbol));
    }

    public Object resolve(String symbol) {
        Object result = globals.get(Value.intern(symbol));
        if (result == null) throw new IllegalStateException(symbol + " is not bound");
        return result;
    }

    public void bind(String symbol, Object value) {
        globals.put(Value.intern(symbol), value);
    }

    public void resetTo(Globals base) {
        this.globals.clear();
        this.globals.putAll(base.globals);
    }

}
