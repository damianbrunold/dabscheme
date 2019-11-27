package ch.dabsoft.dabscheme.vm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Compiler {

    private Globals globals;

    public Compiler(Globals globals) {
        this.globals = globals;
    }

/*
(define in-frame-p
  (lambda (symbol frame)
    (cond ((null? frame) #f)
	  ((eq? symbol (car frame)) 0)
	  (else (let ((p (in-frame-p symbol (cdr frame))))
		  (if p
		      (+ 1 p)
		      #f))))))
*/

    private Object inFrameP(String symbol, Pair frame) {
        if (frame == Value.NIL) return Value.F;
        if (Value.asSymbol(frame.car).equals(symbol)) return 0;
        Object p = inFrameP(symbol, Value.asPair(frame.cdr));
        if (p != Value.F) return 1 + (int) p;
        return Value.F;
    }

/*
(define in-env-p
  (lambda (symbol env)
    (if (null? env)
	#f
	(let ((f (in-frame-p symbol (car env))))
	  (if f
	      (list 0 f)
	      (let ((e (in-env-p symbol (cdr env))))
		(if e
		    (list (+ 1 (first e)) (second e))
		    #f)))))))
 */

    private Object inEnvP(String symbol, Pair env) {
        if (env == Value.NIL) return Value.F;
        Object f = inFrameP(symbol, Value.asPair(env.car));
        if (f != Value.F) return Pair.list(0, f);
        Object e = inEnvP(symbol, Value.asPair(env.cdr));
        if (e != Value.F) return Pair.list(1 + (int) Value.asPair(e).first(), Value.asPair(e).second());
        return Value.F;
    }

/*
(define gen
  (lambda (opcode . args)
    (list (cons opcode args))))
*/

    private Pair gen(Opcode opcode, Object... args) {
        return Pair.list(Pair.list2(opcode, args));
    }

/*
(define seq
  (lambda code
    (apply append code)))
*/

    private Pair seq(Pair... lists) {
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < lists.length; i++) {
            Pair.appendToList(lists[i], result);
        }
        return Pair.list(result.toArray());
    }

/*
(define *label-num* 1)
*/

    private int labelNum = 1;

/*
(define gen-label
  (lambda args
    (let ((num *label-num*) (label "L"))
      (if (> 0 (length args)) (set! label (car args)) #f)
      (set! *label-num* (+ *label-num* 1))
      (string->symbol (string-append label (integer->string num))))))
*/

    private String genLabel() {
        return genLabel("L");
    }

    private String genLabel(String prefix) {
        return prefix + labelNum++;
    }

/*
(define gen-var
  (lambda (var env)
    (let ((p (in-env-p var env)))
      (if p
	  (gen 'LVAR (first p) (second p) ";" var)
	  (gen 'GVAR var)))))
*/

    private Pair genVar(String var, Pair env) {
        Object p = inEnvP(var, env);
        if (p != Value.F) return gen(Opcode.LVAR, Value.asPair(p).first(), Value.asPair(p).second(), ";", var);
        else return gen(Opcode.GVAR, var);
    }

/*
(define gen-set
  (lambda (var env)
    (let ((p (in-env-p var env)))
      (if p
	  (gen 'LSET (first p) (second p) ";" var)
	  (if (assoc var *primitive-fns*)
	      (error "Can't alter the constant ~a" var)
	      (gen 'GSET var))))))
*/

    private Pair genSet(String var, Pair env) {
        Object p = inEnvP(var, env);
        if (p != Value.F) return gen(Opcode.LSET, Value.asPair(p).first(), Value.asPair(p).second(), ";", var);
        else return gen(Opcode.GSET, var);
    }

/*
(define comp
  (lambda (x env val? more?)
    (cond
     ((member? x '(#t #f)) (comp-const x val? more?))
     ((symbol? x) (comp-var x env val? more?))
     ((atom? x) (comp-const x val? more?))
     ((macro? (first x)) (comp (macro-expand x) env val? more?))
     ((eq? (first x) 'quote)
      (arg-count x 1 1)
      (comp-const (second x) val? more?))
     ((eq? (first x) 'begin)
      (comp-begin (rest x) env val? more?))
     ((eq? (first x) 'set!)
      (arg-count x 2 2)
      (seq (comp (third x) env #t #t)
	   (gen-set (second x) env)
	   (if (not val?) (gen 'POP))
	   (if (not more?) (gen 'RETURN) '())))
     ((eq? (first x) 'if)
      (arg-count x 2 3)
      (comp-if (second x) (third x) (fourth x) env val? more?))
     ((eq? (first x) 'lambda)
      (if val?
	  (let ((f (comp-lambda (second x) (rest2 x) env)))
	    (seq (gen 'FN f) (if (not more?) (gen 'RETURN) '())))))
     (else
      (comp-funcall (first x) (rest x) env val? more?)))))
*/

    private Pair comp(Object x, Pair env, boolean val, boolean more) {
        if (x == Value.T || x == Value.F) return compConst(x, val, more);
        if (Value.isSymbol(x)) return compVar(Value.asSymbol(x), env, val, more);
        if (Value.isAtom(x)) return compConst(x, val, more);
        if (Value.isVector(x)) return compConst(x, val, more);
        if (Value.isValues(x)) return compConst(x, val, more);
        Object first = Value.asPair(x).first();
        if (Value.isSymbol(first) && inEnvP(Value.asSymbol(first), env) == Value.F && globals.isBound(Value.asSymbol(first))) {
            Object obj = globals.resolve(Value.asSymbol(first));
            if (Value.isPair(obj) && Value.isSymbol(Value.asPair(obj).first()) && Value.asSymbol(Value.asPair(obj).first()).equals(Value.intern("macro"))) {
                Object expr = Value.asPair(x).cdr;
                VM vm = new VM(globals);
                Lambda lambda = new Lambda(env, Instruction.seq(
                        new Instruction(Opcode.ARGS, 0),
                        new Instruction(Opcode.CONST, expr),
                        new Instruction(Opcode.CONST, Value.asPair(obj).second()),
                        new Instruction(Opcode.CALLJ, 1)));
                Object result = vm.execute(lambda);
                return comp(result, env, val, more);
            }
        }
        if (first.equals("quote")) return compConst(Value.asPair(x).second(), val, more);
        if (first.equals("begin")) return compBegin(Value.asPair(x).cdr, env, val, more);
        if (first.equals("set!")) {
            return seq(
                    comp(Value.asPair(x).third(), env, true, true),
                    genSet(Value.asSymbol(Value.asPair(x).second()), env),
                    val ? Value.NIL : gen(Opcode.POP),
                    more ? Value.NIL : gen(Opcode.RETURN));
        }
        if (first.equals("if")) {
            if (Value.asPair(x).length() == 4) {
                return compIf(Value.asPair(x).second(), Value.asPair(x).third(), Value.asPair(x).fourth(), env, val, more);
            } else {
                return compIf(Value.asPair(x).second(), Value.asPair(x).third(), new Values(), env, val, more);
            }
        }
        if (first.equals("lambda")) {
            if (!val) return Value.NIL;
            Pair f = compLambda(Value.asPair(x).second(), Value.asPair(x).nthCdr(2), env);
            return seq(
                    gen(Opcode.FN, f),
                    more ? Value.NIL : gen(Opcode.RETURN));
        }
        return compFuncall(first, Value.asPair(x).cdr, env, val, more);
    }

/*
(define comp-begin
  (lambda (exps env val? more?)
    (cond ((null? exps) (comp-const '() val? more?))
	  ((= 1 (length exps)) (comp (first exps) env val? more?))
	  (else (seq (comp (first exps) env #f #t)
		     (comp-begin (rest exps) env val? more?))))))
*/

    private Pair compBegin(Object exps, Pair env, boolean val, boolean more) {
        if (exps == Value.NIL) return compConst(Value.NIL, val, more);
        if (Value.asPair(exps).cdr == Value.NIL) return comp(Value.asPair(exps).first(), env, val, more);
        return seq(
                comp(Value.asPair(exps).first(), env, false, true),
                compBegin(Value.asPair(exps).cdr, env, val, more));
    }

/*
(define comp-list
  (lambda (exps env)
    (if (null? exps)
	'()
	(seq (comp (first exps) env #t #t)
	     (comp-list (rest exps) env)))))
*/

    private Pair compList(Object exps, Pair env) {
        if (exps == Value.NIL) return Value.NIL;
        return seq(
                comp(Value.asPair(exps).first(), env, true, true),
                compList(Value.asPair(exps).cdr, env));
    }

/*
(define comp-const
  (lambda (x val? more?)
    (if val?
	(seq (gen 'CONST x)
	     (if (not more?)
		 (gen 'RETURN)
		 '()))
	'())))
*/

    private Pair compConst(Object x, boolean val, boolean more) {
        if (val) {
            return seq(
                    gen(Opcode.CONST, x),
                    more ? Value.NIL : gen(Opcode.RETURN));
        } else {
            return Value.NIL;
        }
    }

/*
(define comp-var
  (lambda (x env val? more?)
    (if val?
	(seq (gen-var x env)
	     (if (not more?)
		 (gen 'RETURN)
		 '()))
	'())))
*/

    private Pair compVar(String x, Pair env, boolean val, boolean more) {
        if (val) {
            return seq(
                    genVar(x, env),
                    more ? Value.NIL : gen(Opcode.RETURN));
        } else {
            return Value.NIL;
        }
    }

/*
(define comp-if
  (lambda (pred then else env val? more?)
    (cond
     ((null? pred) ; (if #f x y) ==> y
      (comp else env val? more?))
     ((constant? pred) ; (if #t x y) ==> x
      (comp then env val? more?))
     (else
      (let ((pcode (comp pred env #t #t))
	    (tcode (comp then env val? more?))
	    (ecode (comp else env val? more?)))
	(cond
	 ((equal? tcode ecode) ; (if p x x) ==> (begin p x)
	  (seq (comp pred env #f #t) ecode))
	 ((null? tcode) ; (if p #f y) ==> p (TJUMP L2) y L2:
	  (let ((L2 (gen-label)))
	    (seq pcode
		 (gen 'TJUMP L2)
		 ecode
		 (list L2)
		 (if (not more?)
		     (gen 'RETURN)
		     '()))))
	 ((null? ecode) ; (if p x) ==> p (FJUMP L1) x L1:
	  (let ((L1 (gen-label)))
	    (seq pcode
		 (gen 'FJUMP L1)
		 tcode
		 (list L1)
		 (if (not more?)
		     (gen 'RETURN)
		     '()))))
	 (else
	  (let ((L1 (gen-label))
		(L2 (if more? (gen-label))))
	    (seq pcode
		 (gen 'FJUMP L1)
		 tcode
		 (if more? (gen 'JUMP L2) '())
		 (list L1)
		 ecode
		 (if more? (list L2) '()))))))))))
*/

    private Pair compIf(Object pred, Object then, Object alternative, Pair env, boolean val, boolean more) {
        if (pred == Value.F) return comp(alternative, env, val, more);
        if (Value.isConstant(pred)) return comp(then, env, val, more);
        Pair pcode = comp(pred, env, true, true);
        Pair tcode = comp(then, env, val, more);
        Pair ecode = comp(alternative, env, val, more);
        // we handle only the generic case
        String l1 = genLabel();
        String l2 = more ? genLabel() : null;
        return seq(
                pcode,
                gen(Opcode.FJUMP, l1),
                tcode,
                more ? gen(Opcode.JUMP, l2) : Value.NIL,
                Pair.list(l1),
                ecode,
                more ? Pair.list(l2) : Value.NIL);
    }

/*
(define comp-funcall
  (lambda (f args env val? more?)
    (cond
     ((and (pair? f) (eq? (first f) 'lambda) (null? (second f))) ; ((lambda () body)) ==> (begin body)
      (comp-begin (rest2 f) env val? more?))
     (more?
      (let ((k (gen-label 'K)))
	(seq (gen 'SAVE k)
	     (comp-list args env)
	     (comp f env #t #t)
	     (gen 'CALLJ (length args))
	     (list k)
	     (if (not val?) (gen 'POP) '()))))
     (else
      (seq (comp-list args env)
	   (comp f env #t #t)
	   (gen 'CALLJ (length args)))))))
*/

    private Pair compFuncall(Object f, Object args, Pair env, boolean val, boolean more) {
        if (Value.isPair(f) && Value.asPair(f).car.equals(Value.intern("lambda")) && Value.asPair(f).second() == Value.NIL) {
            // ((lambda () body)) ==> (begin body)
            return compBegin(Value.asPair(f).nthCdr(2), env, val, more);
        }
        if (more) {
            String k = genLabel("K");
            return seq(
                    gen(Opcode.SAVE, k),
                    compList(args, env),
                    comp(f, env, true, true),
                    gen(Opcode.CALLJ, Value.asPair(args).length()),
                    Pair.list(k),
                    val ? Value.NIL : gen(Opcode.POP));
        }
        return seq(
                compList(args, env),
                comp(f, env, true, true),
                gen(Opcode.CALLJ, Value.asPair(args).length()));
    }

/*
(define comp-lambda
  (lambda (args body env)
    (new-fn env args (seq (gen-args args 0)
			  (comp-begin body
				      (cons (make-true-list args) env)
				      #t #f)))))
*/

    private Pair compLambda(Object args, Object body, Pair env) {
        return assemble(Pair.list(
                Value.intern("env:"),
                env,
                Value.intern("args:"),
                args,
                Value.intern("code:"),
                optimize(seq(
                        genArgs(args, 0),
                        compBegin(body, new Pair(makeTrueList(args), env), true, false)))));
    }

/*
(define gen-args
  (lambda (args n-so-far)
    (cond ((null? args) (gen 'ARGS n-so-far))
	  ((symbol? args) (gen 'ARGS. n-so-far))
	  ((and (pair? args) (symbol? (first args))) (gen-args (rest args) (+ n-so-far 1)))
	  (else (error "Illegal argument list")))))
*/

    private Pair genArgs(Object args, int nsofar) {
        if (args == Value.NIL) return gen(Opcode.ARGS, nsofar);
        if (Value.isSymbol(args)) return gen(Opcode.ARGSDOT, nsofar);
        if (Value.isPair(args) && Value.isSymbol(Value.asPair(args).first())) return genArgs(Value.asPair(args).cdr, 1 + nsofar);
        throw new IllegalStateException("Illegal argument list");
    }

/*
(define make-true-list
  (lambda (dotted-list)
    (cond ((null? dotted-list) '())
	  ((atom? dotted-list) (list dotted-list))
	  (else (cons (first dotted-list)
		      (make-true-list (rest dotted-list)))))))
*/

    private Pair makeTrueList(Object dottedList) {
        if (dottedList == Value.NIL) return Value.NIL;
        if (Value.isAtom(dottedList)) return Pair.list(dottedList);
        return new Pair(Value.asPair(dottedList).first(), makeTrueList(Value.asPair(dottedList).cdr));
    }

/*
(define new-fn
  (lambda (env args code)
    (assemble (list 'env: env
		    'args: args
		    'code: (optimize code)))))
*/

/*
(define optimize
  (lambda (code)
    code))
*/

    private Pair optimize(Pair code) {
        return code;
    }

/*
(define assemble
  (lambda (fn)
    fn))
*/

    private Pair assemble(Pair fn) {
        Pair code = Value.asPair(fn.sixth());
        Map<String, Integer> labels = new HashMap<>();
        List<Instruction> instructions = new ArrayList<>();
        int i = 0;
        while (code != Value.NIL) {
            if (Value.isSymbol(code.car)) {
                labels.put(Value.asSymbol(code.car), i);
            } else {
                Pair current = Value.asPair(code.car);
                Instruction instr = new Instruction();
                instr.opcode = (Opcode) current.first();
                if (current.length() > 1) instr.arg1 = current.second();
                if (current.length() > 2) instr.arg2 = current.third();
                instructions.add(instr);
                i++;
            }
            code = Value.asPair(code.cdr);
        }
        for (Instruction instruction : instructions) {
            if (instruction.opcode == Opcode.JUMP ||
                    instruction.opcode == Opcode.TJUMP ||
                    instruction.opcode == Opcode.FJUMP ||
                    instruction.opcode == Opcode.SAVE) {
                instruction.arg1 = labels.get(instruction.arg1);
            }
        }
        Value.asPair(fn.nthCdr(5)).car = instructions;
        return fn;
    }

/*
(define compile
  (lambda (x)
    (set! *label-num* 1)
    (comp-lambda '() (list x) '())))
*/

    public Pair compile(Object x) {
        labelNum = 1;
        return compLambda(Value.NIL, Pair.list(x), Value.NIL);
    }

}
