;;; list utilities

(find even? '(3 1 4 1 5 9))
=>
4
.
(find even? '(3 1 5 1 5 9))
=>
#f
<<

(for-all even? '(3 1 4 1 5 9))
=>
#f
.
(for-all even? '(2 4 14))
=>
#t
.
(for-all (lambda (n) (and (even? n) n))
	 '(2 4 14))
=>
14
.
(for-all < '(1 2 3) '(2 3 4))
=>
#t
.
(for-all < '(1 2 4) '(2 3 4))
=>
#f
<<

(exists even? '(3 1 4 1 5 9))
=>
#t
.
(exists even? '(3 1 1 5 9))
=>
#f
.
(exists (lambda (n) (and (even? n) n)) '(2 1 4 14))
=>
2
.
(exists < '(1 2 4) '(2 3 4))
=>
#t
.
(exists > '(1 2 3) '(2 3 4))
=>
#f
<<

(filter even? '(3 1 4 1 5 9 2 6))
=>
(4 2 6)
.
(partition even? '(3 1 4 1 5 9 2 6))
=>
(4 2 6)
(3 1 1 5 9)
<<

(fold-left + 0 '(1 2 3 4 5))
=>
15
.
(fold-left (lambda (a e) (cons e a)) '() '(1 2 3 4 5))
=>
(5 4 3 2 1)
.
(fold-left (lambda (count x)
	     (if (odd? x) (+ count 1) count))
	   0
	   '(3 1 4 1 5 9 2 6 5 3))
=>
7
.
(fold-left (lambda (max-len s)
	     (max max-len (string-length s)))
	   0
	   '("longest" "long" "longer"))
=>
7
.
(fold-left cons '(q) '(a b c))
=>
((((q) . a) . b) . c)
.
(fold-left + 0 '(1 2 3) '(4 5 6))
=>
21
<<

(fold-right + 0 '(1 2 3 4 5))
=>
15
.
(fold-right cons '() '(1 2 3 4 5))
=>
(1 2 3 4 5)
.
(fold-right (lambda (x l)
	      (if (odd? x) (cons x l) l))
	    '()
	    '(3 1 4 1 5 9 2 6 5))
=>
(3 1 1 5 9 5)
.
(fold-right cons '(q) '(a b c))
=>
(a b c q)
.
(fold-right + 0 '(1 2 3) '(4 5 6))
=>
21
<<

	   
	
