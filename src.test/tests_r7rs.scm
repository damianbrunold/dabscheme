(define x 28)
x
=>
28
<<

(quote a)
=>
a
.
(quote #(a b c))
=>
#(a b c)
.
(quote (+ 1 2))
=>
(+ 1 2)
<<

'a
=>
a
.
'#(a b c)
=>
#(a b c)
.
'()
=>
()
.
'(+ 1 2)
=>
(+ 1 2)
.
'(quote a)
=>
'a
.
''a
=>
'a
<<

'145932
=>
145932
.
145932
=>
145932
.
'"abc"
=>
"abc"
.
"abc"
=>
"abc"
.
'#(a 10)
=>
#(a 10)
.
#(a 10)
=>
#(a 10)
.
'#t
=>
#t
.
#t
=>
#t
<<

(+ 3 4)
=>
7
<<

((if #f + *) 3 4)
=>
12
<<

(lambda (x) (+ x x))
=>
#<lambda>
<<

((lambda (x) (+ x x)) 4)
=>
8
<<

(define reverse-subtract
  (lambda (x y) (- y x)))
(reverse-subtract 7 10)
=>
3
<<

(define add4
  (let ((x 4))
    (lambda (y) (+ x y))))
(add4 6)
=>
10
<<

((lambda x x) 3 4 5 6)
=>
(3 4 5 6)
.
((lambda (x y . z) z)
 3 4 5 6)
=>
(5 6)
<<

(if (> 3 2) 'yes 'no)
=>
yes
.
(if (> 2 3) 'yes 'no)
=>
no
.
(if (> 3 2)
    (- 3 2)
    (+ 3 2))
=>
1
<<

(define x 2)
=
(+ x 1)
=>
3
.
(set! x 4)
(+ x 1)
=>
5
<<

(cond ((> 3 2) 'greater)
      ((< 3 2) 'less))
=>
greater
.
(cond ((> 3 3) 'greater)
      ((< 3 3) 'less)
      (else 'equal))
=>
equal
.
(cond ((assv 'b '((a 1) (b 2))) => cadr)
      (else #f))
=>
2
<<
