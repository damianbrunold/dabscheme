"Hi Mom!"
=>
"Hi Mom!"
<<

42
=>
42
<<

3.141592653
=>
3.141592653
<<

+
=>
#<primitive:+>
<<

(+ 76 31)
=>
107
<<

'(a b c d)
=>
(a b c d)
<<

(car '(a b c))
=>
a
<<

(cdr '(a b c))
=>
(b c)
<<

(cons 'a '(b c))
=>
(a b c)
<<

(cons (car '(a b c))
      (cdr '(d e f)))
=>
(a e f)
<<

(define square
  (lambda (n)
    (* n n)))
=
(square 5)
=>
25
.
(square -200)
=>
40000
.
(square 0.5)
=>
0.25
<<

(+ (+ 2 2) (+ 2 2))
=>
8
<<

(* 2 (* 2 (* 2 (* 2 2))))
=>
32
<<

(quote (1 2 3 4 5))
=>
(1 2 3 4 5)
<<

(quote ("this" "is" "a" "list"))
=>
("this" "is" "a" "list")
<<

(quote (+ 3 4))
=>
(+ 3 4)
<<

'(1 2 3 4)
=>
(1 2 3 4)
<<

'((1 2) (3 4))
=>
((1 2) (3 4))
<<

'(/ (* 2 -1) 3)
=>
(/ (* 2 -1) 3)
<<

(quote hello)
=>
hello
<<

'2
=>
2
<<

(quote "Hi Mom!")
=>
"Hi Mom!"
<<

(car '(a b c))
=>
a
<<

(cdr '(a b c))
=>
(b c)
<<

(cdr '(a))
=>
()
<<

(car (cdr '(a b c)))
=>
b
<<

(cdr (cdr '(a b c)))
=>
(c)
<<

(car '((a b) (c d)))
=>
(a b)
<<

(cdr '((a b) (c d)))
=>
((c d))
<<

(cons 'a '())
=>
(a)
.
(cons 'a '(b c))
=>
(a b c)
.
(cons 'a (cons 'b (cons 'c '())))
=>
(a b c)
.
(cons '(a b) '(c d))
=>
((a b) c d)
<<

(car (cons 'a '(b c)))
=>
a
.
(cdr (cons 'a '(b c)))
=>
(b c)
.
(cons (car '(a b c))
      (cdr '(d e f)))
=>
(a e f)
.
(cons (car '(a b c))
      (cdr '(a b c)))
=>
(a b c)
<<

(cons 'a 'b)
=>
(a . b)
.
(cdr '(a . b))
=>
b
.
(cons 'a '(b . c))
=>
(a b . c)
<<

'(a . (b . (c . ())))
=>
(a b c)
<<

(list 'a 'b 'c)
=>
(a b c)
.
(list 'a)
=>
(a)
.
(list)
=>
()
<<

(let ((x 2))
  (+ x 3))
=>
5
.
(let ((y 3))
  (+ 2 y))
=>
5
.
(let ((x 2) (y 3))
  (+ x y))
=>
5
<<

(+ (* 4 4) (* 4 4))
=>
32
.
(let ((a (* 4 4)))
  (+ a a))
=>
32
<<

(let ((list1 '(a b c)) (list2 '(d e f)))
  (cons (cons (car list1)
	      (car list2))
	(cons (car (cdr list1))
	      (car (cdr list2)))))
=>
((a . d) b . e)
<<

(let ((f +))
  (f 2 3))
=>
5
.
(let ((f +) (x 2))
  (f x 3))
=>
5
.
(let ((f +) (x 2) (y 3))
  (f x y))
=>
5
<<

(let ((+ *))
  (+ 2 3))
=>
6
<<

(let ((+ *))
  (+ 2 3))
(+ 2 3)
=>
5
<<

(let ((a 4) (b -3))
  (let ((a-squared (* a a))
	(b-squared (* b b)))
    (+ a-squared b-squared)))
=>
25
<<

(let ((x 1))
  (let ((x (+ x 1)))
    (+ x x)))
=>
4
<<

(let ((x 1))
  (let ((new-x (+ x 1)))
    (+ new-x new-x)))
=>
4
<<

(lambda (x) (+ x x))
=>
#<lambda>
<<

((lambda (x) (+ x x)) (* 3 4))
=>
24
<<

(let ((double (lambda (x) (+ x x))))
  (list (double (* 3 4))
	(double (/ 99 11))
	(double (- 2 7))))
=>
(24 18 -10)
<<

(let ((double-cons (lambda (x) (cons x x))))
  (double-cons 'a))
=>
(a . a)
<<

(let ((double-any (lambda (f x) (f x x))))
  (list (double-any + 13)
	(double-any cons 'a)))
=>
(26 (a . a))
<<

(let ((x 'a))
  (let ((f (lambda (y) (list x y))))
    (f 'b)))
=>
(a b)
<<

(let ((f (let ((x 'sam))
	   (lambda (y z) (list x y z)))))
  (f 'i 'am))
=>
(sam i am)
<<

(let ((f (let ((x 'sam))
	   (lambda (y z) (list x y z)))))
  (let ((x 'not-sam))
    (f 'i 'am)))
=>
(sam i am)
<<

(let ((f (lambda x x)))
  (f 1 2 3 4))
=>
(1 2 3 4)
.
(let ((f (lambda x x)))
  (f))
=>
()
.
(let ((g (lambda (x . y) (list x y))))
  (g 1 2 3 4))
=>
(1 (2 3 4))
.
(let ((h (lambda (x y . z) (list x y z))))
  (h 'a 'b 'c 'd))
=>
(a b (c d))
<<

(define double-any
  (lambda (f x)
    (f x x)))
=
(double-any + 10)
=>
20
.
(double-any cons 'a)
=>
(a . a)
<<

(define sandwich "peanut-butter-and-jelly")
sandwich
=>
"peanut-butter-and-jelly"
<<

(define xyz '(x y z))
(let ((xyz '(z y x)))
  xyz)
=>
(z y x)
<<

(define doubler
  (lambda (f)
    (lambda (x) (f x x))))
=
(define double (doubler +))
(double 6)
=>
12
.
(define double-cons (doubler cons))
(double-cons 'a)
=>
(a . a)
<<

(define proc1
  (lambda (x y)
    (proc2 y x)))
(define proc2 cons)
(proc1 'a 'b)
=>
(b . a)
<<

(define abs
  (lambda (n)
    (if (< n 0)
	(- 0 n)
	n)))
=
(abs 77)
=>
77
.
(abs -77)
=>
77
<<

(< -1 0)
=>
#t
.
(> -1 0)
=>
#f
<<

(if #t 'true 'false)
=>
true
.
(if #f 'true 'false)
=>
false
.
(if '() 'true 'false)
=>
true
.
(if 1 'true 'false)
=>
true
.
(if '(a b c) 'true 'false)
=>
true
<<

(not #t)
=>
#f
.
(not "false")
=>
#f
.
(not #f)
=>
#t
<<

(or)
=>
#f
.
(or #f)
=>
#f
.
(or #f #t)
=>
#t
.
(or #f 'a #f)
=>
a
<<

(define reciprocal
  (lambda (n)
    (and (not (= n 0))
	 (/ n))))
=
(reciprocal 2)
=>
0.5
.
(reciprocal 0.25)
=>
4.0
.
(reciprocal 0)
=>
#f
<<

(null? '())
=>
#t
.
(null? 'abc)
=>
#f
.
(null? '(x y z))
=>
#f
.
(null? (cdddr '(x y z)))
=>
#t
<<

(define lisp-cdr
  (lambda (x)
    (if (null? x)
	'()
	(cdr x))))
=
(lisp-cdr '(a b c))
=>
(b c)
.
(lisp-cdr '(c))
=>
()
.
(lisp-cdr '())
=>
()
<<

(eqv? 'a 'a)
=>
#t
.
(eqv? 'a 'b)
=>
#f
.
(eqv? #f #f)
=>
#t
.
(eqv? #t #f)
=>
#f
.
(eqv? 3 3)
=>
#t
.
(eqv? 3 2)
=>
#f
.
(let ((x "Hi Mom!"))
  (eqv? x x))
=>
#t
.
(let ((x (cons 'a 'b)))
  (eqv? x x))
=>
#t
.
(eqv? (cons 'a 'b) (cons 'a 'b))
=>
#f
<<

(pair? '(a . b))
=>
#t
.
(pair? '(a b c))
=>
#t
.
(pair? '())
=>
#f
.
(pair? 'abc)
=>
#f
.
(pair? "Hi Mom!")
=>
#f
.
(pair? 1234567890)
=>
#f
<<

(define reciprocal
  (lambda (n)
    (if (and (number? n) (not (= n 0)))
	(/ 1 n)
	"oops!")))
=
(reciprocal 0.25)
=>
4.0
.
(reciprocal 'a)
=>
"oops!"
<<

(define sign
  (lambda (n)
    (if (< n 0)
	-1
	(if (> n 0)
	    +1
	    0))))
=
(sign -88.3)
=>
-1
.
(sign 0)
=>
0
.
(sign 3333333333)
=>
1
.
(* (sign -88.3) (abs -88.3))
=>
-88.3
<<

(define income-tax
  (lambda (income)
    (cond
     ((<= income 10000) (* income .05))
     ((<= income 20000) (+ (* (- income 10000) .08) 500.00))
     ((<= income 30000) (+ (* (- income 20000) .13) 1300.00))
     (else (+ (* (- income 30000) .21) 2600.00)))))
=
(income-tax 5000)
=>
250.0
.
(income-tax 15000)
=>
900.0
.
(income-tax 25000)
=>
1950.0
.
(income-tax 50000)
=>
6800.0
<<

(define length
  (lambda (ls)
    (if (null? ls)
	0
	(+ (length (cdr ls)) 1))))
=
(length '())
=>
0
.
(length '(a))
=>
1
.
(length '(a b))
=>
2
<<

(define list-copy
  (lambda (ls)
    (if (null? ls)
	'()
	(cons (car ls)
	      (list-copy (cdr ls))))))
=
(list-copy '())
=>
()
.
(list-copy '(a b c))
=>
(a b c)
<<

(define memv
  (lambda (x ls)
    (cond
     ((null? ls) #f)
     ((eqv? (car ls) x) ls)
     (else (memv x (cdr ls))))))
=
(memv 'a '(a b b d))
=>
(a b b d)
.
(memv 'b '(a b b d))
=>
(b b d)
.
(memv 'c '(a b b d))
=>
#f
.
(memv 'd '(a b b d))
=>
(d)
.
(if (memv 'b '(a b b d))
    "yes"
    "no")
=>
"yes"
<<

(define remv
  (lambda (x ls)
    (cond
     ((null? ls) '())
     ((eqv? (car ls) x) (remv x (cdr ls)))
     (else (cons (car ls) (remv x (cdr ls)))))))
=
(remv 'a '(a b b d))
=>
(b b d)
.
(remv 'b '(a b b d))
=>
(a d)
.
(remv 'c '(a b b d))
=>
(a b b d)
.
(remv 'd '(a b b d))
=>
(a b b)
<<

(define tree-copy
  (lambda (tr)
    (if (not (pair? tr))
	tr
	(cons (tree-copy (car tr))
	      (tree-copy (cdr tr))))))
=
(tree-copy '((a . b) . c))
=>
((a . b) . c)
<<

(define abs-all
  (lambda (ls)
    (if (null? ls)
	'()
	(cons (abs (car ls))
	      (abs-all (cdr ls))))))
=
(abs-all '(1 -2 3 -4 5 -6))
=>
(1 2 3 4 5 6)
<<

(define abs-all
  (lambda (ls)
    (map abs ls)))
=
(abs-all '(1 -2 3 -4 5 -6))
=>
(1 2 3 4 5 6)
<<

(map abs '(1 -2 3 -4 5 -6))
=>
(1 2 3 4 5 6)
<<

(map (lambda (x) (* x x))
     '(1 -3 -5 7))
=>
(1 9 25 49)
<<

(map cons '(a b c) '(1 2 3))
=>
((a . 1) (b . 2) (c . 3))
<<

(define map1
  (lambda (p ls)
    (if (null? ls)
	'()
	(cons (p (car ls))
	      (map1 p (cdr ls))))))
=
(map1 abs '(1 -2 3 -4 5 -6))
=>
(1 2 3 4 5 6)
<<

(define abcde '(a b c d e))
=
abcde
=>
(a b c d e)
.
(set! abcde (cdr abcde))
=>
(b c d e)
.
(let ((abcde '(a b c d e)))
  (set! abcde (reverse abcde))
  abcde)
=>
(e d c b a)
<<

(define quadratic-formula
  (lambda (a b c)
    (let ((root1 0) (root2 0) (minusb 0) (radical 0) (divisor 0))
      (set! minusb (- 0 b))
      (set! radical (sqrt (- (* b b) (* 4 (* a c)))))
      (set! divisor (* 2 a))
      (set! root1 (/ (+ minusb radical) divisor))
      (set! root2 (/ (- minusb radical) divisor))
      (cons root1 root2))))
(quadratic-formula 2 -4 -6)
=>
(3 . -1)
<<

(define quadratic-formula
  (lambda (a b c)
    (let ((minusb (- 0 b))
	  (radical (sqrt (- (* b b) (* 4 (* a c)))))
	  (divisor (* 2 a)))
      (let ((root1 (/ (+ minusb radical) divisor))
	    (root2 (/ (- minusb radical) divisor)))
	(cons root1 root2)))))
(quadratic-formula 2 -4 -6)
=>
(3 . -1)
<<

(define cons-count 0)
(define cons
  (let ((old-cons cons))
    (lambda (x y)
      (set! cons-count (+ cons-count 1))
      (old-cons x y))))
=
(cons 'a '(b c))
=>
(a b c)
.
cons-count
=>
1
.
(cons 'a (cons 'b (cons 'c '())))
cons-count
=>
4
<<

(define next 0)
(define count
  (lambda ()
    (let ((v next))
      (set! next (+ next 1))
      v)))
=
(count)
=>
0
.
(count)
=>
1
<<

(define count
  (let ((next 0))
    (lambda ()
      (let ((v next))
	(set! next (+ next 1))
	v))))
=
(count)
=>
0
.
(count)
=>
1
<<

(define make-counter
  (lambda ()
    (let ((next 0))
      (lambda ()
	(let ((v next))
	  (set! next (+ next 1))
	  v)))))
(define count1 (make-counter))
(define count2 (make-counter))
=
(count1)
=>
0
.
(count2)
=>
0
.
(count1)
=>
1
.
(count1)
=>
2
.
(count2)
=>
1
<<

(define shhh #f)
(define tell #f)
(let ((secret 0))
  (set! shhh
	(lambda (message)
	  (set! secret message)))
  (set! tell
	(lambda ()
	  secret)))
(shhh "sally likes harry")
(tell)
=>
"sally likes harry"
<<

(define lazy
  (lambda (t)
    (let ((val #f) (flag #f))
      (lambda ()
	(if (not flag)
	    (begin (set! val (t))
		   (set! flag #t)))
	val))))
(define n 0)
(define p (lazy (lambda () (set! n (+ n 1)) "got me")))
=
(p)
=>
"got me"
.
n
=>
1
.
(p)
=>
"got me"
.
n
=>
1
<<

(define make-stack
  (lambda ()
    (let ((ls '()))
      (lambda (msg . args)
	(cond
	 ((eqv? msg 'empty?) (null? ls))
	 ((eqv? msg 'push!) (set! ls (cons (car args) ls)))
	 ((eqv? msg 'top) (car ls))
	 ((eqv? msg 'pop!) (set! ls (cdr ls)))
	 (else "oops"))))))
(define stack1 (make-stack))
(define stack2 (make-stack))
=
(stack1 'empty?)
=>
#t
.
(stack2 'empty?)
=>
#t
.
(stack1 'push! 'a)
(stack1 'empty?)
=>
#f
.
(stack2 'empty?)
=>
#t
.
(stack1 'push! 'b)
(stack2 'push! 'c)
(stack1 'top)
=>
b
.
(stack2 'top)
=>
c
.
(stack1 'pop!)
(stack2 'empty?)
=>
#f
.
(stack1 'top)
=>
a
.
(stack2 'pop!)
(stack2 'empty?)
=>
#t
<<

(define p (list 1 2 3))
=
(set-car! (cdr p) 'two)
p
=>
(1 two 3)
.
(set-cdr! p '())
p
=>
(1)
<<

(define make-queue
  (lambda ()
    (let ((end (cons 'ignored '())))
      (cons end end))))
(define putq!
  (lambda (q v)
    (let ((end (cons 'ignored '())))
      (set-car! (cdr q) v)
      (set-cdr! (cdr q) end)
      (set-cdr! q end))))
(define getq
  (lambda (q)
    (car (car q))))
(define delq!
  (lambda (q)
    (set-car! q (cdr (car q)))))
(define myq (make-queue))
=
(putq! myq 'a)
(putq! myq 'b)
(getq myq)
=>
a
.
(delq! myq)
(getq myq)
=>
b
.
(delq! myq)
(putq! myq 'c)
(putq! myq 'd)
(getq myq)
=>
c
.
(delq! myq)
(getq myq)
=>
d
<<

(let* ((a 5) (b (+ a a)) (c (+ a b)))
  (list a b c))
=>
(5 10 15)
<<

(let ((x 3))
  (unless (= x 0) (set! x (+ x 1)))
  (when (= x 4) (set! x (* x 2)))
  x)
=>
8
<<

(let ((sum (lambda (sum ls)
	     (if (null? ls)
		 0
		 (+ (car ls) (sum sum (cdr ls)))))))
  (sum sum '(1 2 3 4 5)))
=>
15
<<

(letrec ((sum (lambda (ls)
		(if (null? ls)
		    0
		    (+ (car ls) (sum (cdr ls)))))))
  (sum '(1 2 3 4 5)))
=>
15
<<

(letrec ((even?
	  (lambda (x)
	    (or (= x 0)
		(odd? (- x 1)))))
	 (odd?
	  (lambda (x)
	    (and (not (= x 0))
		 (even? (- x 1))))))
  (list (even? 20) (odd? 20)))
=>
(#t #f)
<<

(letrec ((f (lambda () (+ x 2)))
	 (x 1))
  (f))
=>
3
<<

(define factorial
  (lambda (n)
    (let fact ((i n))
      (if (= i 0)
	  1
	  (* i (fact (- i 1)))))))
=
(factorial 0)
=>
1
.
(factorial 1)
=>
1
.
(factorial 2)
=>
2
.
(factorial 3)
=>
6
.
(factorial 10)
=>
3628800
<<

(define factorial
  (lambda (n)
    (let fact ((i n) (a 1))
      (if (= i 0)
	  a
	  (fact (- i 1) (* a i))))))
=
(factorial 0)
=>
1
.
(factorial 1)
=>
1
.
(factorial 2)
=>
2
.
(factorial 3)
=>
6
.
(factorial 10)
=>
3628800
<<

(define fibonacci
  (lambda (n)
    (let fib ((i n))
      (cond
       ((= i 0) 0)
       ((= i 1) 1)
       (else (+ (fib (- i 1)) (fib (- i 2))))))))
=
(fibonacci 0)
=>
0
.
(fibonacci 1)
=>
1
.
(fibonacci 2)
=>
1
.
(fibonacci 3)
=>
2
.
(fibonacci 4)
=>
3
.
(fibonacci 5)
=>
5
.
(fibonacci 6)
=>
8
.
(fibonacci 20)
=>
6765
<<

(define fibonacci
  (lambda (n)
    (if (= n 0)
	0
	(let fib ((i n) (a1 1) (a2 0))
	  (if (= i 1)
	      a1
	      (fib (- i 1) (+ a1 a2) a1))))))
=
(fibonacci 0)
=>
0
.
(fibonacci 1)
=>
1
.
(fibonacci 2)
=>
1
.
(fibonacci 3)
=>
2
.
(fibonacci 4)
=>
3
.
(fibonacci 5)
=>
5
.
(fibonacci 6)
=>
8
.
(fibonacci 20)
=>
6765
.
(fibonacci 30)
=>
832040
<<

(define factor
  (lambda (n)
    (let f ((n n) (i 2))
      (cond
       ((>= i n) (list n))
       ((integer? (/ n i))
	(cons i (f (/ n i) i)))
       (else (f n (+ i 1)))))))
=
(factor 0)
=>
(0)
.
(factor 1)
=>
(1)
.
(factor 12)
=>
(2 2 3)
.
(factor 3628800)
=>
(2 2 2 2 2 2 2 2 3 3 3 3 5 5 7)
.
(factor 9239)
=>
(9239)
<<

(call/cc
 (lambda (k)
   (* 5 4)))
=>
20
<<

(call/cc
 (lambda (k)
   (* 5 (k 4))))
=>
4
<<

(+ 2
   (call/cc
    (lambda (k)
      (* 5 (k 4)))))
=>
6
<<

(define product
  (lambda (ls)
    (call/cc
     (lambda (break)
       (let f ((ls ls))
	 (cond
	  ((null? ls) 1)
	  ((= (car ls) 0) (break 0))
	  (else (* (car ls) (f (cdr ls))))))))))
=
(product '(1 2 3 4 5))
=>
120
.
(product '(7 3 8 0 1 9 5))
=>
0
<<

(let ((x (call/cc (lambda (k) k))))
  (x (lambda (ignore) "hi")))
=>
"hi"
<<

(((call/cc (lambda (k) k)) (lambda (x) x)) "HEY!")
=>
"HEY!"
<<

(define retry #f)
(define factorial
  (lambda (x)
    (if (= x 0)
	(call/cc (lambda (k) (set! retry k) 1))
	(* x (factorial (- x 1))))))
=
(factorial 4)
=>
24
.
(retry 1)
=>
24
.
(retry 2)
=>
48
<<

(letrec ((f (lambda (x) (cons 'a x)))
	 (g (lambda (x) (cons 'b (f x))))
	 (h (lambda (x) (g (cons 'c x)))))
  (cons 'd (h '())))
=>
(d b a c)
<<

(letrec ((f (lambda (x k) (k (cons 'a x))))
	 (g (lambda (x k) (f x (lambda (v) (k (cons 'b v))))))
	 (h (lambda (x k) (g (cons 'c x) k))))
  (h '() (lambda (v) (cons 'd v))))
=>
(d b a c)
<<

(define car&cdr
  (lambda (p k)
    (k (car p) (cdr p))))
=
(car&cdr '(a b c)
	 (lambda (x y)
	   (list y x)))
=>
((b c) a)
.
(car&cdr '(a b c) cons)
=>
(a b c)
.
(car&cdr '(a b c a d) memv)
=>
(a d)
<<

(define integer-divide
  (lambda (x y success failure)
    (if (= y 0)
	(failure "divide by zero")
	(let ((q (quotient x y)))
	  (success q (- x (* q y)))))))
=
(integer-divide 10 3 list (lambda (x) x))
=>
(3 1)
.
(integer-divide 10 0 list (lambda (x) x))
=>
"divide by zero"
<<

(define product
  (lambda (ls k)
    (let ((break k))
      (let f ((ls ls) (k k))
	(cond
	 ((null? ls) (k 1))
	 ((= (car ls) 0) (break 0))
	 (else (f (cdr ls)
		  (lambda (x)
		    (k (* (car ls) x))))))))))
=
(product '(1 2 3 4 5) (lambda (x) x))
=>
120
.
(product '(7 3 8 0 1 9 5) (lambda (x) x))
=>
0
<<

(define f (lambda (x) (* x x)))
=
(let ((x 3))
  (define f (lambda (y) (+ y x)))
  (f 4))
=>
7
.
(f 4)
=>
16
<<

(let ()
  (define even?
    (lambda (x)
      (or (= x 0)
	  (odd? (- x 1)))))
  (define odd?
    (lambda (x)
      (and (not (= x 0))
	   (even? (- x 1)))))
  (even? 20))
=>
#t
<<

(define calc #f)
(let ()
  (define do-calc
    (lambda (ek exp)
      (cond
       ((number? exp) exp)
       ((and (list? exp) (= (length exp) 3))
	(let ((op (car exp)) (args (cdr exp)))
	  (case op
	    ((add) (apply-op ek + args))
	    ((sub) (apply-op ek - args))
	    ((mul) (apply-op ek * args))
	    ((div) (apply-op ek / args))
	    (else (complain ek "invalid operator" op)))))
       (else (complain ek "invalid expression" exp)))))
  (define apply-op
    (lambda (ek op args)
      (op (do-calc ek (car args)) (do-calc ek (cadr args)))))
  (define complain
    (lambda (ek msg exp)
      (ek (list msg exp))))
  (set! calc
	(lambda (exp)
	  (call/cc
	   (lambda (ek)
	     (do-calc ek exp))))))
=
(calc '2)
=>
2
.
(calc '(add 2 3))
=>
5
.
(calc '(add (mul 3 2) -4))
=>
2
.
(calc '(div 6 2))
=>
3
.
(calc '(add (mul 3 2) (div 4)))
=>
("invalid expression" (div 4))
.
(calc '(mul (add 1 -2) (pow 2 7)))
=>
("invalid operator" pow)
<<

list
=>
#<list>
<<

(define x 'a)
=
(list x x)
=>
(a a)
.
(let ((x 'b))
  (list x x))
=>
(b b)
.
(let ((let 'let)) let)
=>
let
<<

(define f
  (lambda (x)
    (g x)))
(define g
  (lambda (x)
    (+ x x)))
(f 3)
=>
6
<<

(lambda (x) (+ x 3))
=>
#<lambda>
.
((lambda (x) (+ x 3)) 7)
=>
10
.
((lambda (x y) (* x (+ x y))) 7 13)
=>
140
.
((lambda (f x) (f x x)) + 11)
=>
22
.
((lambda () (+ 3 4)))
=>
7
.
((lambda (x . y) (list x y))
 28 37)
=>
(28 (37))
.
((lambda (x . y) (list x y))
 28 37 47 28)
=>
(28 (37 47 28))
.
((lambda (x y . z) (list x y z))
 1 2 3 4)
=>
(1 2 (3 4))
.
((lambda x x) 7 13)
=>
(7 13)
<<

(let ((x (* 3.0 3.0)) (y (* 4.0 4.0)))
  (sqrt (+ x y)))
=>
5.0
.
(let ((x 'a) (y '(b c)))
  (cons x y))
=>
(a b c)
.
(let ((x 0) (y 1))
  (let ((x y) (y x))
    (list x y)))
=>
(1 0)
<<

(let* ((x (* 5.0 5.0))
       (y (- x (* 4.0 4.0))))
  (sqrt y))
=>
3.0
.
(let ((x 0) (y 1))
  (let* ((x y) (y x))
    (list x y)))
=>
(1 1)
<<

(letrec ((sum (lambda (x)
		(if (zero? x)
		    0
		    (+ x (sum (- x 1)))))))
  (sum 5))
=>
15
<<

(define x 3)
x
=>
3
.
(define f
  (lambda (x y)
    (* (+ x y) 2)))
(f 5 4)
=>
18
.
(define (sum-of-squares x y)
  (+ (* x x) (* y y)))
(sum-of-squares 3 4)
=>
25
<<

(define f
  (lambda (x)
    (+ x 1)))
=
(let ((x 2))
  (define f
    (lambda (y)
      (+ y x)))
  (f 3))
=>
5
.
(f 3)
=>
4
<<

(define flip-flop
  (let ((state #f))
    (lambda ()
      (set! state (not state))
      state)))
=
(flip-flop)
=>
#t
.
(flip-flop)
=>
#f
.
(flip-flop)
=>
#t
<<

(define memoize
  (lambda (proc)
    (let ((cache '()))
      (lambda (x)
	(cond
	 ((assq x cache) => cdr)
	 (else (let ((ans (proc x)))
		 (set! cache (cons (cons x ans) cache))
		 ans)))))))
(define fibonacci
  (memoize
   (lambda (n)
     (if (< n 2)
	 n
	 (+ (fibonacci (- n 1)) (fibonacci (- n 2)))))))
(fibonacci 30)
=>
832040
<<

(+ 3 4)
=>
7
.
((if (odd? 3) + -) 6 2)
=>
8
.
((lambda (x) x) 5)
=>
5
.
(let ((f (lambda (x) (+ x x))))
  (f 8))
=>
16
<<


(apply + '(4 5))
=>
9
.
(apply min '(6 8 3 2 5))
=>
2
.
(apply min 5 1 3 '(6 8 3 2 5))
=>
1
.
(apply vector 'a 'b '(c d e))
=>
#(a b c d e)
<<

(define first
  (lambda (l)
    (apply (lambda (x . y) x)
	   l)))
(define rest
  (lambda (l)
    (apply (lambda (x . y) y)
	   l)))
=
(first '(a b c d))
=>
a
.
(rest '(a b c d))
=>
(b c d)
<<

(define x 3)
(begin
  (set! x (+ x 1))
  (+ x x))
=>
8
<<

(let ()
  (begin (define x 3) (define y 4))
  (+ x y))
=>
7
<<

(define x 2)
(let ()
  (begin (define x 3) (define y 4))
  (+ x y))
x
=>
2
<<

(define swap-pair!
  (lambda (x)
    (let ((temp (car x)))
      (set-car! x (cdr x))
      (set-cdr! x temp)
      x)))
(swap-pair! (cons 'a 'b))
=>
(b . a)
<<

(let ((l '(a b c)))
  (if (null? l)
      '()
      (cdr l)))
=>
(b c)
<<

(let ((l '()))
  (if (null? l)
      '()
      (cdr l)))
=>
()
<<

(let ((abs
       (lambda (x)
	 (if (< x 0)
	     (- 0 x)
	     x))))
  (abs -4))
=>
4
<<

(let ((x -4))
  (if (< x 0)
      (list 'minus (- 0 x))
      (list 'plus x)))
=>
(minus 4)
<<

(not #f)
=>
#t
.
(not #t)
=>
#f
.
(not '())
=>
#f
.
(not (< 4 6))
=>
#f
<<

(let ((x 3))
  (and (> x 2) (< x 4)))
=>
#t
.
(let ((x 5))
  (and (> x 2) (< x 4)))
=>
#f
.
(and #f '(a b) '(c d))
=>
#f
.
(and '(a b) '(c d) '(e f))
=>
(e f)
<<

(let ((x 3))
  (or (< x 2) (> x 4)))
=>
#f
.
(let ((x 5))
  (or (< x 2) (> x 4)))
=>
#t
.
(or #f '(a b) '(c d))
=>
(a b)
<<

(let ((x 0))
  (cond
   ((< x 0) (list 'minus (abs x)))
   ((> x 0) (list 'plus x))
   (else (list 'zero x))))
=>
(zero 0)
<<

(define select
  (lambda (x)
    (cond
     ((not (symbol? x)))
     ((assq x '((a . 1) (b . 2) (c . 3))) => cdr)
     (else 0))))
=
(select 3)
=>
#t
.
(select 'b)
=>
2
.
(select 'e)
=>
0
<<

(let ((x 4) (y 5))
  (case (+ x y)
    ((1 3 5 7 9) 'odd)
    ((0 2 4 6 8) 'even)
    (else 'out-of-range)))
=>
odd
<<
