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
