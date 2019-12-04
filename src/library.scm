(set! list (lambda x x))

; (define name value) --> (begin (set! name value) 'name)
; (define (name var ...) body --> (begin (set! name (lambda (var ...) body) 'name))
; (define (name . var) body --> (begin (set! name (lambda var body) 'name))
(set! define (list 'macro (lambda (expr)
			    (if (pair? (car expr))
				(list 'begin
				      (list 'set! (car (car expr)) (list 'lambda (cdr (car expr)) (car (cdr expr))))
				      (list 'quote (car (car expr))))
				(list 'begin
				      (list 'set! (car expr) (car (cdr expr)))
				      (list 'quote (car expr)))))))

(define call/cc call-with-current-continuation)

(define (caar pair) (car (car pair)))
(define (cadr pair) (car (cdr pair)))
(define (cdar pair) (cdr (car pair)))
(define (cddr pair) (cdr (cdr pair)))

(define (caaar pair) (car (car (car pair))))
(define (caadr pair) (car (car (cdr pair))))
(define (cadar pair) (car (cdr (car pair))))
(define (caddr pair) (car (cdr (cdr pair))))
(define (cdaar pair) (cdr (car (car pair))))
(define (cdadr pair) (cdr (car (cdr pair))))
(define (cddar pair) (cdr (cdr (car pair))))
(define (cdddr pair) (cdr (cdr (cdr pair))))

(define (caaaar pair) (car (car (car (car pair)))))
(define (caaadr pair) (car (car (car (cdr pair)))))
(define (caadar pair) (car (car (cdr (car pair)))))
(define (caaddr pair) (car (car (cdr (cdr pair)))))
(define (cadaar pair) (car (cdr (car (car pair)))))
(define (cadadr pair) (car (cdr (car (cdr pair)))))
(define (caddar pair) (car (cdr (cdr (car pair)))))
(define (cadddr pair) (car (cdr (cdr (cdr pair)))))
(define (cdaaar pair) (cdr (car (car (car pair)))))
(define (cdaadr pair) (cdr (car (car (cdr pair)))))
(define (cdadar pair) (cdr (car (cdr (car pair)))))
(define (cdaddr pair) (cdr (car (cdr (cdr pair)))))
(define (cddaar pair) (cdr (cdr (car (car pair)))))
(define (cddadr pair) (cdr (cdr (car (cdr pair)))))
(define (cdddar pair) (cdr (cdr (cdr (car pair)))))
(define (cddddr pair) (cdr (cdr (cdr (cdr pair)))))

(define (first pair) (car pair))
(define (second pair) (car (cdr pair)))
(define (third pair) (car (cdr (cdr pair))))
(define (fourth pair) (car (cdr (cdr (cdr pair)))))
(define (fifth pair) (car (cdr (cdr (cdr (cdr pair))))))
(define (sixth pair) (car (cdr (cdr (cdr (cdr (cdr pair)))))))
(define (seventh pair) (car (cdr (cdr (cdr (cdr (cdr (cdr pair))))))))
(define (eighth pair) (car (cdr (cdr (cdr (cdr (cdr (cdr (cdr pair)))))))))
(define (ninth pair) (car (cdr (cdr (cdr (cdr (cdr (cdr (cdr (cdr pair))))))))))

(define (rest pair) (cdr pair))
(define (rest2 pair) (cdr (cdr pair)))

(define (not x) (if x #f #t))
(define (zero? n) (= n 0))
(define (null? x) (eq? x nil))

(define (abs n)
  (if (< n 0)
      (- n)
      n))

; this is not tail recursive, will be replaced as soon as named-let is available
(define (length x)
  (if (null? x)
      0
      (+ 1 (length (cdr x)))))

; this is a single list map, later on, we will get the full version of map
(define (map f x)
  (if (null? x)
      '()
      (cons (f (car x)) (map f (cdr x)))))

; (defmacro name lambda) --> (define name (list 'macro lambda))
(define defmacro
  (list 'macro (lambda (expr)
		 (list 'define
		       (first expr)
		       (list 'list
			     (list 'quote 'macro)
			     (second expr))))))

(defmacro and
  (lambda (expr)
    (if (null? expr)
	#t
	(if (= 1 (length expr))
	    (first expr)
	    (list 'if (first expr) (append (list 'and) (cdr expr)) #f)))))

;(let ((var val) ...) body ...)
;=>
;((lambda (var ...) body ...) val ...)
;
;(let name ((var val) ...) body ...)
;=>
;((letrec ((name (lambda (var ...) body ...)))
;   name)
; val ...)
(defmacro let
  (lambda (expr)
    (if (if (pair? (first expr)) #t (null? (first expr)))
	(append (list (append (list 'lambda)
			      (list (map first (first expr)))
			      (cdr expr)))
		(map second (first expr)))
	(append (list (append (list 'letrec)
			      (list (list (append (list (first expr))
						  (list (append (list 'lambda)
								(list (map first (second expr)))
								(cddr expr))))))
			      (list (first expr))))
		(map second (second expr))))))
		  

;(letrec ((var val) ...) body ...)
;=>
;(let ((var #f) ...)
;  (set! var val)
;  ...
;  body ...)
(defmacro letrec
  (lambda (expr)
    (append (list 'let)
	    (list (map (lambda (x) (list (first x) #f)) (first expr)))
	    (map (lambda (x) (list 'set! (first x) (second x))) (first expr))
	    (cdr expr))))

(defmacro or
  (lambda (expr)
    (if (null? expr)
	#f
	(if (= 1 (length expr))
	    (first expr)
	    (let ((var (gensym)))
	      (list 'let
		    (list (list var (first expr)))
		    (list 'if var var (append (list 'or) (cdr expr)))))))))

(define (number? n) (or (integer? n) (real? n)))
(define (procedure? obj) (or (lambda? obj) (primitive? obj)))

(define (macro? obj)
  (and (pair? obj)
       (eq? (car obj) 'macro)
       (lambda? (cadr obj))))

(define (constant? obj)
  (or (number? obj)
      (boolean?  obj)
      (char? obj)
      (string? obj)
      (and (pair? obj)
	   (eq? (car obj) 'quote))))

(define (atom? obj)
  (or (number? obj)
      (boolean? obj)
      (char? obj)
      (string? obj)
      (symbol? obj)))

(define (length x)
  (let loop ((x x) (n 0))
    (if (null? x)
	n
	(loop (cdr x) (+ n 1)))))

(define (list? x)
  (letrec ((race
	    (lambda (h t)
	      (if (pair? h)
		  (let ((h (cdr h)))
		    (if (pair? h)
			(if (not (eq? h t))
			    (race (cdr h)(cdr t))
			    #f)
			(null? h)))
		  (null? h)))))
    (race x x)))

(defmacro cond
  (lambda (expr)
    (if (null? expr)
	#f
	(let ((clause (car expr)))
	  (if (eq? (car clause) 'else)
	      (append (list 'begin) (cdr clause))
	      (if (pair? (cdr clause))
		  (if (eq? (cadr clause) '=>)
		      (list 'if (car clause)
			    (append (list 'begin) (cdr clause) (list (list (caddr clause) (car clause))))
			    (append (list 'cond) (cdr expr)))
		      (list 'if (car clause)
			    (append (list 'begin) (cdr clause))
			    (append (list 'cond) (cdr expr))))
		  (list 'or (car clause)
			(append (list 'cond) (cdr expr)))))))))

(define (sign n)
  (cond ((< n 0) -1)
	((> n 0) +1)
	(else 0)))

; if performance is an issue, this could be made primitive
(define (string=? a b)
  (if (not (= (string-length a) (string-length b)))
      #f
      (let loop ((n 0))
	(cond ((= n (string-length a)) #t)
	      ((not (char=? (string-ref a n) (string-ref b n))) #f)
	      (else (loop (+ n 1)))))))

(define (eqv? x y)
  (cond
   ((eq? x y))
   ((number? x)
    (and (number? y) (= x y))) ; TODO handle exact/inexact
   ((char? x) (and (char? y) (char=? x y)))
   (else #f)))

(define (equal? x y)
  (cond
   ((eqv? x y))
   ((pair? x)
    (and (pair? y)
	 (equal? (car x) (car y))
	 (equal? (cdr x) (cdr y))))
   ((string? x)
    (and (string? y)
         (string=? x y)))
	; TODO handle vectors
   (else #f)))

(define (memq x ls)
  (if (or (null? ls) (not (pair? ls)))
      #f
      (if (eq? x (first ls))
	  ls
	  (memq x (cdr ls)))))

(define (memv x ls)
  (if (or (null? ls) (not (pair? ls)))
      #f
      (if (eqv? x (first ls))
	  ls
	  (memv x (cdr ls)))))

(define (member x ls)
  (if (or (null? ls) (not (pair? ls)))
      #f
      (if (equal? x (first ls))
	  ls
	  (member x (cdr ls)))))

(define (assq x ls)
  (cond ((null? ls) #f)
	((eq? x (first (first ls))) (first ls))
	(else (assq x (rest ls)))))

(define (assv x ls)
  (cond ((null? ls) #f)
	((eqv? x (first (first ls))) (first ls))
	(else (assv x (rest ls)))))

(define (assoc x ls)
  (cond ((null? ls) #f)
	((equal? x (first (first ls))) (first ls))
	(else (assoc x (rest ls)))))

(define (factorial n)
  (let fact ((i n) (a 1))
    (if (= i 0)
	a
	(fact (- i 1) (* a i)))))

(define (vector->list v)
  (let loop ((n 0))
    (if (= n (vector-length v))
	'()
	(cons (vector-ref v n) (loop (+ n 1))))))

(defmacro quasiquote
  (lambda (x)
    (letrec ((combine-skeletons (lambda (left right exp)
				  (cond ((and (constant? left) (constant? right))
					 (if (and (eqv? (if (pair? left) (cadr left) left) (car exp))
						  (eqv? (if (pair? right) (cadr right) right) (cdr exp)))
					     (list 'quote exp)
					     (list 'quote (cons (if (pair? left) (cadr left) left)
								(if (pair? right) (cadr right) right)))))
					((null? right) (list 'list left))
					((and (pair? right) (eq? (car right) 'list))
					 (cons 'list (cons left (cdr right))))
					(else (list 'cons left right)))))
	     (expand-quasiquote (lambda (exp nesting)
				  (cond
				   ((vector? exp)
				    (list 'apply 'vector (expand-quasiquote (vector->list exp) nesting)))
				   ((not (pair? exp))
				    (if (constant? exp) exp (list 'quote exp)))
				   ((and (eq? (car exp) 'unquote) (eqv? (length exp) 2))
				    (if (eqv? nesting 0)
					(second exp)
					(combine-skeletons ''unquote
							   (expand-quasiquote (cdr exp) (- nesting 1))
							   exp)))
				   ((and (eq? (car exp) 'quasiquote) (eqv? (length exp) 2))
				    (combine-skeletons ''quasiquote
						       (expand-quasiquote (cdr exp) (+ nesting 1))
						       exp))
				   ((and (pair? (car exp))
					 (eq? (caar exp) 'unquote-splicing)
					 (eqv? (length (car exp)) 2))
				    (if (eqv? nesting 0)
					(list 'append (second (first exp))
					      (expand-quasiquote (cdr exp) nesting))
					(combine-skeletons (expand-quasiquote (car exp) (- nesting 1))
							   (expand-quasiquote (cdr exp) nesting)
							   exp)))
				   (else (combine-skeletons (expand-quasiquote (car exp) nesting)
							    (expand-quasiquote (cdr exp) nesting)
							    exp))))))
      (expand-quasiquote (car x) 0))))

(define (reverse ls)
  (let loop ((ls ls) (acc '()))
    (if (null? ls)
	acc
	(loop (cdr ls) (cons (car ls) acc)))))

(defmacro let*
  (lambda (expr)
    (let ((bindings (car expr))
	  (body (cdr expr)))
      (if (null? bindings)
	  `((lambda () . ,body))
	  `(let (,(first bindings))
	     (let* ,(rest bindings) . ,body))))))

(defmacro letrec*
  (lambda (expr)
    (let ((bindings (car expr))
	  (body (cdr expr)))
      (if (null? bindings) `((lambda () . ,body))
	  `(let ((,(first (first bindings)) #f))
	     (set! ,(first (first bindings)) ,(second (first bindings)))
	     (letrec* ,(rest bindings) . ,body))))))

(defmacro case
  (lambda (expr)
    (let ((exp (car expr))
	  (cases (cdr expr))
	  (val (gensym)))
      (let ((do-case (lambda (acase)
		       (cond ((not (pair? acase)) (error 'case "bad syntax in case ~s", acase))
			     ((eq? (first acase) 'else) acase)
			     (else `((member ,val ',(first acase)) . ,(rest acase)))))))
	`(let ((,val ,exp))
	   (cond . ,(map do-case cases)))))))

(defmacro do
  (lambda (expr)
    (let ((bindings (first expr))
	  (test-and-result (second expr))
	  (body (rest2 expr))
	  (loop (gensym)))
      (let ((variables (map first bindings))
	    (inits (map second bindings))
	    (steps (map (lambda (clause)
			  (if (null? (cddr clause))
			      (first clause)
			      (third clause)))
			bindings))
	    (test (first test-and-result))
	    (result (rest test-and-result)))
	`(letrec ((,loop
		   (lambda ,variables
		     (if ,test
			 (begin . ,result)
			 (begin
			   ,@body
			   (,loop . ,steps))))))
	   (,loop . ,inits))))))

(defmacro receive
  (lambda (expr)
    (let ((formals (first expr))
	  (expression (second expr))
	  (body (rest2 expr)))
      `(call-with-values (lambda () ,expression)
	 (lambda ,formals ,@body)))))

(define (map f ls . more)
  (if (null? more)
      (let map1 ((ls ls))
	(if (null? ls)
	    '()
	    (cons (f (car ls))
		  (map1 (cdr ls)))))
      (let map-more ((ls ls) (more more))
	(if (null? ls)
	    '()
	    (cons (apply f (car ls) (map car more))
		  (map-more (cdr ls)
			    (map cdr more)))))))

(define (for-each f ls . more)
  (do ((ls ls (cdr ls)) (more more (map cdr more)))
      ((null? ls))
    (apply f (car ls) (map car more))))

(defmacro unless
  (lambda (expr)
    (let ((test (car expr))
	  (body (cdr expr)))
      `(if (not ,test) (begin ,@body)))))

(defmacro when
  (lambda (expr)
    (let ((test (car expr))
	  (body (cdr expr)))
      `(if ,test (begin ,@body)))))

(define (min . ls)
  (let loop ((ls ls) (m (car ls)))
    (cond ((null? ls) m)
	  ((< (car ls) m)
	   (loop (cdr ls) (car ls)))
	  (else
	   (loop (cdr ls) m)))))

(define (max . ls)
  (let loop ((ls ls) (m (car ls)))
    (cond ((null? ls) m)
	  ((> (car ls) m)
	   (loop (cdr ls) (car ls)))
	  (else
	   (loop (cdr ls) m)))))

(define (even? n)
  (= n (* 2 (quotient n 2))))

(define (odd? n)
  (not (even? n)))

(define (list-tail ls n)
  (if (= n 0)
      ls
      (list-tail (cdr ls) (- n 1))))

(define dynamic-wind #f)
(let ((winders '()))
  (define common-tail
    (lambda (x y)
      (let ((lx (length x)) (ly (length y)))
	(do ((x (if (> lx ly) (list-tail x (- lx ly)) x) (cdr x))
	     (y (if (> ly lx) (list-tail y (- ly lx)) y) (cdr y)))
	    ((eq? x y) x)))))
  (define do-wind
    (lambda (new)
      (let ((tail (common-tail new winders)))
	(let f ((l winders))
	  (if (not (eq? l tail))
	      (begin
		(set! winders (cdr l))
		((cdar l))
		(f (cdr l)))))
	(let f ((l new))
	  (if (not (eq? l tail))
	      (begin
		(f (cdr l))
		((caar l))
		(set! winders l)))))))
  (set! call/cc
	(let ((c call/cc))
	  (lambda (f)
	    (c (lambda (k)
		 (f (let ((save winders))
		      (lambda x
			(if (not (eq? save winders)) (do-wind save))
			(apply k x)))))))))
  (set! call-with-current-continuation call/cc)
  (set! dynamic-wind
	(lambda (in body out)
	  (in)
	  (set! winders (cons (cons in out) winders))
	  (let ((ans (body)))
	    (set! winders (cdr winders))
	    (out)
	    ans))))

(defmacro unwind-protect
  (lambda (expr)
    (let ((body (car expr))
	  (cleanup (cdr expr)))
      `(dynamic-wind
	   (lambda () #f)
	   (lambda () ,body)
	   (lambda () ,@cleanup)))))

(define (make-promise p)
  (let ((vals #f) (set? #f))
    (lambda ()
      (if (not set?)
	  (call-with-values p
	    (lambda x
	      (if (not set?)
		  (begin (set! vals x)
			 (set! set? #t))))))
      (apply values vals))))

(define (force promise)
  (promise))

(defmacro delay
  (lambda (expr)
    `(make-promise (lambda () ,(car expr)))))
