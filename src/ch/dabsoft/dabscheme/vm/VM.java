package ch.dabsoft.dabscheme.vm;

import java.util.ArrayList;
import java.util.List;

public class VM {

    private Globals globals;
    private Lambda fn;
    private int ip;
    private Pair env = Value.NIL;
    private Pair stack = Value.NIL;
    private int nargs = 0;
    private int flattened = 0;

    public VM(Globals globals) {
        this.globals = globals;
    }

    private Object elt(Object list, int n) {
        for (int i = 0; i < n; i++) {
            list = Value.asPair(list).cdr;
        }
        return Value.asPair(list).car;
    }

    private Object getenv(int frame, int var) {
        return elt(elt(env, frame), var);
    }

    private void setenv(int frame, int var, Object value) {
        Object frame_ = elt(env, frame);
        for (int i = 0; i < var; i++) {
            frame_ = Value.asPair(frame_).cdr;
        }
        Value.asPair(frame_).car = value;
    }

    private void push(Object value) {
        stack = new Pair(value, stack);
    }

    private Object pop() {
        Object value = stack.car;
        stack = Value.asPair(stack.cdr);
        return value;
    }

    private Object top() {
        return stack.car;
    }

    private Object second() {
        return Value.asPair(stack.cdr).car;
    }

    private Object primaryValue(Object value) {
        if (Value.isValues(value)) return Value.asValues(value).values[0];
        return value;
    }

    public Object execute(Lambda func) {
        //System.out.println("code: " + func.code);
        //System.out.println("stack: " + stack);
        push(new ReturnAddress(func.code.size(), func, env));
        this.fn = func;
        this.ip = 0;
        this.nargs = 0;
        while (ip < this.fn.code.size()) {
            Instruction instruction = this.fn.code.get(ip++);
            switch (instruction.opcode) {
                case LVAR:
                    push(getenv((int) instruction.arg1, (int) instruction.arg2));
                    break;

                case LSET:
                    setenv((int) instruction.arg1, (int) instruction.arg2, primaryValue(top()));
                    break;

                case GVAR:
                    push(globals.resolve(Value.asSymbol(instruction.arg1)));
                    break;

                case GSET: {
                    Object value = primaryValue(top());
                    globals.bind(Value.asSymbol(instruction.arg1), value);
                    if (Value.isLambda(value)) Value.asLambda(value).name = instruction.arg1.toString();
                    break;
                }

                case POP:
                    pop();
                    break;

                case CONST:
                    push(instruction.arg1);
                    break;

                case JUMP:
                    ip = (int) instruction.arg1;
                    break;

                case FJUMP:
                    if (pop() == Value.F) ip = (int) instruction.arg1;
                    break;

                case TJUMP:
                    if (pop() != Value.F) ip = (int) instruction.arg1;
                    break;

                case SAVE:
                    push(new ReturnAddress((int) instruction.arg1, this.fn, env));
                    break;

                case RETURN: {
                    ReturnAddress address = (ReturnAddress) second();
                    this.fn = address.fn;
                    env = address.env;
                    ip = address.ip;
                    Object value = pop();
                    pop();
                    push(value);
                    break;
                }

                case CALLJ: {
                    int argcount = (int) instruction.arg1;
                    if (argcount == -1) {
                        argcount = flattened;
                        flattened = 0;
                    }
                    env = Value.asPair(env.cdr); // discard top frame
                    Object f = pop();
                    if (Value.isLambda(f)) {
                        this.fn = Value.asLambda(f);
                        env = fn.env;
                        ip = 0;
                        nargs = argcount;
                    } else if (Value.isPrimitive(f)) {
                        Object[] args = new Object[argcount];
                        nargs = args.length;
                        for (int i = args.length - 1; i >= 0; i--) args[i] = pop();
                        push(Value.asPrimitive(f).apply(args));
                        ReturnAddress address = (ReturnAddress) second();
                        this.fn = address.fn;
                        env = address.env;
                        ip = address.ip;
                        Object value = pop();
                        pop();
                        push(value);
                    } else {
                        throw new IllegalStateException(f + " is not a function, cannot apply");
                    }
                    break;
                }

                case ARGS: {
                    if (nargs != (int) instruction.arg1)
                        throw new IllegalStateException("Wrong number of arguments: " + instruction.arg1 + " expected, " + nargs + " supplied");
                    Pair frame = Value.NIL;
                    for (int i = nargs - 1; i >= 0; i--) frame = new Pair(pop(), frame);
                    env = new Pair(frame, env);
                    break;
                }

                case ARGSDOT: {
                    if (nargs < (int) instruction.arg1)
                        throw new IllegalStateException("Wrong number of arguments: " + instruction.arg1 + " or more expected, " + nargs + " supplied");
                    Pair rest = Value.NIL;
                    for (int i = 0; i < nargs - (int) instruction.arg1; i++) rest = new Pair(pop(), rest);
                    Pair frame = new Pair(rest, Value.NIL);
                    for (int i = (int) instruction.arg1 - 1; i >= 0; i--) frame = new Pair(pop(), frame);
                    env = new Pair(frame, env);
                    break;
                }

                case ARGMV: {
                    if (nargs == 1) {
                        env = new Pair(new Pair(pop(), Value.NIL), env);
                    } else {
                        Values mv = new Values();
                        mv.values = new Object[nargs];
                        for (int i = 0; i < nargs; i++) mv.values[i] = pop();
                        env = new Pair(new Pair(mv, Value.NIL), env);
                    }
                    break;
                }

                case FN:
                    Object instructions = Value.asPair(instruction.arg1).sixth();
                    //noinspection unchecked
                    push(new Lambda(env, (List<Instruction>) instructions));
                    break;

                case SETCC:
                    stack = Value.asPair(top());
                    break;

                case CC: {
                    List<Instruction> newcode = new ArrayList<>();
                    newcode.add(new Instruction(Opcode.ARGMV));
                    newcode.add(new Instruction(Opcode.LVAR, 1, 0));
                    newcode.add(new Instruction(Opcode.SETCC));
                    newcode.add(new Instruction(Opcode.LVAR, 0, 0));
                    newcode.add(new Instruction(Opcode.RETURN));
                    push(new Lambda(Pair.list(Pair.list(stack)), newcode));
                    break;
                }

                case FLATTEN_APPLY: {
                    List<Object> args = new ArrayList<>();
                    Pair pair = Value.asPair(getenv((int) instruction.arg1, (int) instruction.arg2));
                    while (pair != Value.NIL) {
                        if (pair.cdr == Value.NIL) {
                            pair = Value.asPair(pair.car);
                            while (pair != Value.NIL) {
                                args.add(pair.car);
                                pair = Value.asPair(pair.cdr);
                            }
                            break;
                        } else {
                            args.add(pair.car);
                            pair = Value.asPair(pair.cdr);
                        }
                    }
                    flattened = args.size();
                    for (int i = 0; i < args.size(); i++) push(args.get(i));
                    break;
                }

                case FLATTEN_MULTVALS: {
                    Object val = pop();
                    if (Value.isValues(val)) {
                        Object[] values = Value.asValues(val).values;
                        flattened = values.length;
                        for (int i = 0; i < values.length; i++) push(values[i]);
                    } else {
                        flattened = 1;
                        push(val);
                    }
                    break;
                }

                default:
                    throw new IllegalStateException("Unknown opcode " + instruction.opcode);
            }
        }
        return pop();
    }

}
