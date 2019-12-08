package ch.dabsoft.dabscheme.vm;

import ch.dabsoft.dabscheme.primitive.PrimitiveRead;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Scheme {

    private Globals globals;
    private Globals rootGlobals;
    private Compiler compiler;
    private VM vm;

    public Scheme() throws IOException {
        globals = new Globals();
        compiler = new Compiler(globals);
        vm = new VM(globals);
        loadLibraries();
        rootGlobals = new Globals(globals);
    }

    public Scheme(Globals globals) throws IOException {
        this.globals = globals;
        compiler = new Compiler(this.globals);
        vm = new VM(this.globals);
        rootGlobals = new Globals(this.globals);
    }

    public void reset() {
        globals.resetTo(rootGlobals);
    }

    public Object read(PushbackReader reader) {
        PrimitiveRead read = (PrimitiveRead) globals.resolve(Value.intern("read"));
        return read.apply(new Object[] { reader });
    }

    public Object eval(Object expr) {
        Pair fn = compiler.compile(expr);
        Pair env = Value.asPair(fn.second());
        List<Instruction> instructions = (List<Instruction>) fn.sixth();
        Lambda lambda = new Lambda(env, instructions);
        return vm.execute(lambda);
    }

    public Object evalString(String string) {
        try {
            return evalFile(new StringReader(string));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Value.F;
        }
    }

    public Object evalFile(File file) {
        try {
            return evalFile(new FileInputStream(file));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Value.F;
        }
    }

    public Object evalFile(InputStream stream) {
        return evalFile(new InputStreamReader(stream, StandardCharsets.UTF_8));
    }

    public Object evalFile(Reader reader) {
        try {
            PushbackReader in = new PushbackReader(reader);
            Object expr = read(in);
            Object value = Value.F;
            while (expr != Value.EOF) {
                try {
                    value = eval(expr);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                expr = read(in);
            }
            return value;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Values();
        }
    }

    public void loadLibraries() throws IOException {
        evalFile(Scheme.class.getResourceAsStream("/library.scm"));
    }

    public void repl() throws IOException {
        System.out.print("> ");
        System.out.flush();
        PushbackReader in = new PushbackReader(new InputStreamReader(System.in));
        Object expr = read(in);
        while (true) {
            if (expr == Value.EOF) break;
            try {
                Object value = eval(expr);
                System.out.println(Value.printRep(value));
            } catch (SchemeError e) {
                System.out.println(e);
            } catch (SchemeExit e) {
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.print("> ");
            System.out.flush();
            expr = read(in);
        }
    }

    public static void main(String... args) throws IOException {
        new Scheme().repl();
    }
}
