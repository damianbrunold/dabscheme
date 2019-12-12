package ch.dabsoft.dabscheme.vm;

import ch.dabsoft.dabscheme.primitive.*;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PushbackReader;
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
        bind("*input-port*", new PushbackReader(new InputStreamReader(System.in)));
        bind("*output-port*", new OutputStreamWriter(System.out));
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
        bind("make-vector", new PrimitiveMakeVector());
        bind("string", new PrimitiveString());
        bind("string-length", new PrimitiveStringLength());
        bind("string-ref", new PrimitiveStringRef());
        bind("string->symbol", new PrimitiveStringToSymbol());
        bind("string-set!", new PrimitiveStringSetB());
        bind("make-string", new PrimitiveMakeString());
        bind("string=?", new PrimitiveStringEqP());
        bind("string<?", new PrimitiveStringLessP());
        bind("string<=?", new PrimitiveStringLessEqP());
        bind("string>?", new PrimitiveStringGreaterP());
        bind("string>=?", new PrimitiveStringGreaterEqP());
        bind("string-copy", new PrimitiveStringCopy());
        bind("string-append", new PrimitiveStringAppend());
        bind("substring", new PrimitiveSubstring());
        bind("symbol->string", new PrimitiveSymbolToString());
        bind("char=?", new PrimitiveCharEqP());
        bind("char<?", new PrimitiveCharLessP());
        bind("char<=?", new PrimitiveCharLessEqP());
        bind("char>?", new PrimitiveCharGreaterP());
        bind("char>=?", new PrimitiveCharGreaterEqP());
        bind("char-alphabetic?", new PrimitiveCharAlphabeticP());
        bind("char-numeric?", new PrimitiveCharNumericP());
        bind("char-upper-case?", new PrimitiveCharUpperCaseP());
        bind("char-lower-case?", new PrimitiveCharLowerCaseP());
        bind("char-whitespace?", new PrimitiveCharWhitespaceP());
        bind("char-upcase", new PrimitiveCharUpcase());
        bind("char-downcase", new PrimitiveCharDowncase());
        bind("char->integer", new PrimitiveCharToInteger());
        bind("integer->char", new PrimitiveIntegerToChar());
        bind("quotient", new PrimitiveQuotient());
        bind("remainder", new PrimitiveRemainder());
        bind("modulo", new PrimitiveModulo());
        bind("truncate", new PrimitiveTruncate());
        bind("floor", new PrimitiveFloor());
        bind("ceiling", new PrimitiveCeiling());
        bind("round", new PrimitiveRound());
        bind("expt", new PrimitiveExpt());
        bind("exact->inexact", new PrimitiveExactToInexact());
        bind("inexact->exact", new PrimitiveInexactToExact());
        bind("string->number", new PrimitiveStringToNumber());
        bind("number->string", new PrimitiveNumberToString());
        bind("log", new PrimitiveLog());
        bind("sin", new PrimitiveSin());
        bind("cos", new PrimitiveCos());
        bind("tan", new PrimitiveTan());
        bind("asin", new PrimitiveAsin());
        bind("acos", new PrimitiveAcos());
        bind("atan", new PrimitiveAtan());
        bind("exact?", new PrimitiveExactP());
        bind("error", new PrimitiveError());
        bind("input-port?", new PrimitiveInputPortP());
        bind("output-port?", new PrimitiveOutputPortP());
        bind("open-input-file", new PrimitiveOpenInputFile());
        bind("open-input-string", new PrimitiveOpenInputString());
        bind("close-input-port", new PrimitiveCloseInputPort());
        bind("open-output-file", new PrimitiveOpenOutputFile());
        bind("open-output-string", new PrimitiveOpenOutputString());
        bind("get-output-string", new PrimitiveGetOutputString());
        bind("close-output-port", new PrimitiveCloseOutputPort());
        bind("read-char", new PrimitiveReadChar(this));
        bind("peek-char", new PrimitivePeekChar(this));
        bind("char-ready?", new PrimitiveCharReadyP(this));
        bind("read-line", new PrimitiveReadLine(this));
        bind("read", new PrimitiveRead(this));
        bind("load", new PrimitiveLoad(this));
        bind("eof-object?", new PrimitiveEOFObjectP());
        bind("write-char", new PrimitiveWriteChar(this));
        bind("write", new PrimitiveWrite(this));
        bind("display", new PrimitiveDisplay(this));
        bind("newline", new PrimitiveNewline(this));
        bind("values", new PrimitiveValues());
        bind("call-with-current-continuation", getCallCC());
        bind("apply", getApply());
        bind("call-with-values", getCallWithValues());
        bind("eval", new PrimitiveEval(this));
        bind("exit", new PrimitiveExit());
        bind("PI", Math.PI);
        bind("E", Math.E);
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
