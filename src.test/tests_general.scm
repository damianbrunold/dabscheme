(factorial 10)
=>
3628800
<<

(call/cc
 (lambda (k)
   (* 5 4)))
=>
20
.
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

(/ 4)
=>
0.25
.
(/ 1 4)
=>
0.25
.
(/ 4 1)
=>
4
.
(/ 4 1.0)
=>
4.0
.
(/ 4 2)
=>
2
.
(/ 4.0 2)
=>
2.0
<<

(let* ((a 2) (b (+ a 2)))
  b)
=>
4
<<

(let ((x 4) (y 5))
  (case (+ x y)
    ((1 3 5 7 9) 'odd)
    ((0 2 4 6 8) 'even)
    (else 'out-of-range)))
=>
odd
<<

(append '() '(a b c))
=>
(a b c)
<<

(append '(a) '() '(b c))
=>
(a b c)
<<

(define fact
  (lambda (n)
    (do ((i n (- i 1)) (a 1 (* a i)))
	((zero? i) a))))
(fact 10)
=>
3628800
<<

(letrec* ((a 12) (b (+ 2 a)))
  (+ a b))
=>
26
<<

(letrec* ((odd? (lambda (n) (if (= n 0) #f (not (odd? (- n 1))))))
	  (even? (lambda (n) (not (odd? n)))))
  (even? 4))
=>
#t
<<

(apply + 1 2 3 '(4 5))
=>
15
.
(apply (lambda (x y) (+ (* x x) (* y y))) '(3 4))
=>
25
<<

(call-with-values
    (lambda () (values 1 2 3))
  (lambda (x y z) (list x y z)))
=>
(1 2 3)
<<

(receive (a b) (values 1 2)
  (list a b))
=>
(1 2)
<<

(let ((same-count 0))
  (for-each
   (lambda (x y)
     (if (= x y)
	 (set! same-count (+ same-count 1))))
   '(1 2 3 4 5 6)
   '(2 3 3 4 7 6))
  same-count)
=>
3
<<

(sqrt 25)
=>
5
.
(sqrt 9)
=>
3
.
(sqrt 9.0)
=>
3.0
<<

(let ((x 2))
  (cond
   ((assq x '((1 . 1) (2 . 4) (3 . 9))) => cdr)))
=>
4
<<

(let ((a 'a) (b 'b) (x 'x) (y 'y))
  (let-values (((a b) (values x y))
	       ((x y) (values a b)))
    (list a b x y)))
=>
(x y a b)
<<

(let ((a 'a) (b 'b) (x 'x) (y 'y))
  (let*-values (((a b) (values x y))
	       ((x y) (values a b)))
    (list a b x y)))
=>
(x y x y)
<<

(string-join '("foo" "bar" "baz") ":")
=>
"foo:bar:baz"
.
(string-join '("foo" "bar" "baz") ":" 'suffix)
=>
"foo:bar:baz:"
.
(string-join '()   ":")
=>
""
.
(string-join '("") ":")
=>
""
.
(string-join '()   ":" 'suffix)
=>
""
.
(string-join '("") ":" 'suffix)
=>
":"
<<

(string-join '("a" "b" "c") "-" 'prefix)
=>
"-a-b-c"
.
(string-join '("a" "b" "c") "-" 'infix)
=>
"a-b-c"
.
(string-join '("a" "b" "c") "-" 'suffix)
=>
"a-b-c-"
.
(string-join '("a") "-" 'prefix)
=>
"-a"
.
(string-join '("a") "-" 'infix)
=>
"a"
.
(string-join '("a") "-" 'suffix)
=>
"a-"
<<

(string-split "a b c")
=>
("a" "b" "c")
.
(string-split "a b\t c\n\rd")
=>
("a" "b" "c" "d")
.
(string-split "31.12.2016" "[.]")
=>
("31" "12" "2016")
<<

(string-split-vector "a b c")
=>
#("a" "b" "c")
<<

(string-take "Pete Szilagyi" 6)
=>
"Pete S"
.
(string-drop "Pete Szilagyi" 6)
=>
"zilagyi"
<<

(string-take-right "Beta rules" 5)
=>
"rules"
.
(string-drop-right "Beta rules" 5)
=>
"Beta "
<<

(string-skip "  abc  ")
=>
2
.
(string-skip-right "  abc  ")
=>
4
<<

(string-index "  abca  " #\a)
=>
2
.
(string-index-right "  abca  " #\a)
=>
5
<<

(string-trim "  The outlook wasn't brilliant,  \n\r")
=>
"The outlook wasn't brilliant,  \n\r"
.
(string-trim-right "  The outlook wasn't brilliant,  \n\r")
=>
"  The outlook wasn't brilliant,"
.
(string-trim-both "  The outlook wasn't brilliant,  \n\r")
=>
"The outlook wasn't brilliant,"
<<

(zip '(1 2 3) '(a b c))
=>
((1 . a) (2 . b) (3 . c))
<<

