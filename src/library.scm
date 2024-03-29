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

(define (current-input-port)
  *input-port*)

(define (current-output-port)
  *output-port*)

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

(define complex? real?)
(define rational? integer?)
(define (number? n) (or (integer? n) (real? n)))
(define (procedure? obj) (or (lambda? obj) (primitive? obj)))

(define (positive? num) (> num 0))
(define (negative? num) (< num 0))

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

(define (list-ref ls n)
  (if (= n 0)
      (car ls)
      (list-ref (cdr ls) (- n 1))))

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

(define (inexact? n)
  (not (exact? n)))

(define (eqv? x y)
  (cond
   ((eq? x y))
   ((number? x)
    (and (number? y)
	 (if (exact? x)
	     (and (exact? y) (= x y))
	     (and (inexact? y) (= x y)))))
   ((char? x) (and (char? y) (char=? x y)))
   (else #f)))

(define (equal? x y)
  (cond
   ((eqv? x y))
   ((pair? x)
    (and (pair? y)
	 (equal? (car x) (car y))
	 (equal? (cdr x) (cdr y))))
   ((string? x) (and (string? y) (string=? x y)))
   ((vector? x)
    (and (vector? y)
	 (let ((n (vector-length x)))
	   (and (= (vector-length y) n)
		(let loop ((i 0))
		  (or (= i n)
		      (and (equal? (vector-ref x i) (vector-ref y i))
			   (loop (+ i 1)))))))))
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

(defmacro with-values
  (lambda (expr)
    `(call-with-values (lambda () ,(car expr)) ,(cadr expr))))

(defmacro let-values
  (lambda (expr)
    (let* ((defs (first expr))
	   (var (caar defs))
	   (val (cadar defs))
	   (body (cdr expr)))
      (if (= (length defs) 1)
	  `(with-values ,val (lambda ,var ,@body))
	  (let ((vars (map car defs))
		(vals (map cadr defs))
		(temps (map (lambda (x) (list (gensym) (gensym))) defs)))
	    (let loop ((vals vals) (tmps temps))
	      (if (null? vals)
		  `((lambda ,(apply append vars) ,@body) ,@(apply append temps))
		  `(with-values ,(car vals) (lambda ,(car tmps) ,(loop (cdr vals) (cdr tmps)))))))))))

(defmacro let*-values
  (lambda (expr)
    (let* ((defs (first expr))
	   (var (caar defs))
	   (val (cadar defs))
	   (body (cdr expr)))
      (if (null? (cdr defs))
	  `(with-values ,val (lambda ,var ,@body))
	  `(with-values ,val (lambda ,var (let*-values ,(cdr defs) ,@(cdr expr))))))))

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

(define (split ls)
  (if (or (null? ls) (null? (cdr ls)))
      (values ls '())
      (call-with-values
	  (lambda () (split (cddr ls)))
	(lambda (odds evens)
	  (values (cons (car ls) odds)
		  (cons (cadr ls) evens))))))

(define (scheme-report-environment version) (list 'scheme-report version))
(define (null-environment version) (list 'null version))
(define (interaction-environment) (list 'interaction))

(define (call-with-input-file filename proc)
  (let ((p (open-input-file filename)))
    (let ((v (proc p)))
      (close-input-port p)
      v)))

(define (with-input-from-file filename thunk)
  (let ((p (open-input-file filename))
	(orig-input-port *input-port*))
    (dynamic-wind
	(lambda () (set! *input-port* p))
	(lambda () (thunk))
	(lambda () (set! *input-port* orig-input-port) (close-input-port p)))))

(define (call-with-output-file filename proc)
  (let ((p (open-output-file filename)))
    (let ((v (proc p)))
      (close-output-port p)
      v)))

(define (with-output-to-file filename thunk)
  (let ((p (open-output-file filename))
	(orig-output-port *output-port*))
    (dynamic-wind
	(lambda () (set! *output-port* p))
	(lambda () (thunk))
	(lambda () (set! *output-port* orig-output-port) (close-output-port p)))))

(define (call-with-input-string str proc)
  (let ((p (open-input-string str)))
    (let ((v (proc p)))
      (close-input-port p)
      v)))

(define (with-input-from-string str thunk)
  (let ((p (open-input-string str))
	(orig-input-port *input-port*))
    (dynamic-wind
	(lambda () (set! *input-port* p))
	(lambda () (thunk))
	(lambda () (set! *input-port* orig-input-port) (close-input-port p)))))

(define (gcd . ls)
  (let ((gcd2 (lambda (a b)
		(let loop ((a a) (b b))
		  (cond
		   ((= b 0) a)
		   (else (loop b (modulo a b))))))))
    (if (= (length ls) 2)
	(apply gcd2 ls)
	(let loop ((ls ls) (g 0))
	  (if (null? ls)
	      g
	      (loop (cdr ls) (gcd2 (car ls) g)))))))

(define (lcm . ls)
  (let ((lcm2 (lambda (a b)
		(abs (/ (* a b) (gcd a b))))))
    (if (= (length ls) 2)
	(apply lcm2 ls)
	(let loop ((ls ls) (r 1))
	  (if (null? ls)
	      r
	      (loop (cdr ls) (lcm2 (car ls) r)))))))

(define magnitude abs)

(define (exp num)
  (expt E num))

(define (char-ci=? . chars) (apply char=? (map char-downcase chars)))
(define (char-ci<? . chars) (apply char<? (map char-downcase chars)))
(define (char-ci<=? . chars) (apply char<=? (map char-downcase chars)))
(define (char-ci>? . chars) (apply char>? (map char-downcase chars)))
(define (char-ci>=? . chars) (apply char>=? (map char-downcase chars)))

(define (string->list s)
  (do ((i (- (string-length s) 1) (- i 1))
       (ls '() (cons (string-ref s i) ls)))
      ((< i 0) ls)))

(define (list->string ls)
  (let ((s (make-string (length ls))))
    (do ((ls ls (cdr ls)) (i 0 (+ i 1)))
	((null? ls) s)
      (string-set! s i (car ls)))))

(define (string-downcase s)
  (let ((r (string-copy s))
	(n (string-length s)))
    (do ((i 0 (+ i 1)))
	((= i n) r)
      (string-set! r i (char-downcase (string-ref s i))))))
	 
(define (string-upcase s)
  (let ((r (string-copy s))
	(n (string-length s)))
    (do ((i 0 (+ i 1)))
	((= i n) r)
      (string-set! r i (char-downcase (string-ref s i))))))

(define (string-ci=? . strs) (apply string=? (map string-downcase strs)))
(define (string-ci<? . strs) (apply string<? (map string-downcase strs)))
(define (string-ci<=? . strs) (apply string<=? (map string-downcase strs)))
(define (string-ci>? . strs) (apply string>? (map string-downcase strs)))
(define (string-ci>=? . strs) (apply string>=? (map string-downcase strs)))

(define (string-fill! s c)
  (let ((n (string-length s)))
    (do ((i 0 (+ i 1)))
	((= i n))
      (string-set! s i c))))


(define (vector-fill! v x)
  (let ((n (vector-length v)))
    (do ((i 0 (+ i 1)))
	((= i n))
      (vector-set! v i x))))

(define (list->vector ls)
  (let ((s (make-vector (length ls))))
    (do ((ls ls (cdr ls)) (i 0 (+ i 1)))
	((null? ls) s)
      (vector-set! s i (car ls)))))

(define (vector-copy v)
  (let ((r (make-vector (vector-length v))) (n (vector-length v)))
    (do ((i 0 (+ i 1)))
	((= i n) r)
      (vector-set! r i (vector-ref v i)))))

(define (find proc lst)
  (cond ((null? lst) #f)
	((proc (car lst)) (car lst))
	(else (find proc (cdr lst)))))

(define (for-all proc . lsts)
  (cond ((null? (car lsts)) #f)
	((null? (cdar lsts)) (apply proc (map car lsts)))
	((apply proc (map car lsts)) (apply for-all proc (map cdr lsts)))
	(else #f)))

(define (exists proc . lsts)
  (cond ((null? (car lsts)) #f)
	((null? (cdar lsts)) (apply proc (map car lsts)))
	(else (let ((value (apply proc (map car lsts))))
		(if value
		    value
		    (apply exists proc (map cdr lsts)))))))

(define (filter proc lst)
  (cond ((null? lst) '())
	((proc (car lst)) (cons (car lst) (filter proc (cdr lst))))
	(else (filter proc (cdr lst)))))

(define (partition proc lst)
  (let loop ((ls lst) (a '()) (b '()))
    (cond ((null? ls) (values (reverse a) (reverse b)))
	  ((proc (car ls)) (loop (cdr ls) (cons (car ls) a) b))
	  (else (loop (cdr ls) a (cons (car ls) b))))))

(define (fold-left combine nil . lsts)
  (let loop ((r nil) (lsts lsts))
    (if (null? (car lsts))
	r
	(loop (apply combine r (map car lsts)) (map cdr lsts)))))

(define (fold-right combine nil . lsts)
  (let loop ((r nil) (lsts (map reverse lsts)))
    (if (null? (car lsts))
	r
	(loop (apply combine (append (map car lsts) (list r))) (map cdr lsts)))))

(define (string-join string-list . maybe-args)
  (let ((delimiter " ")
	(grammar 'infix))
    (if (> (length maybe-args) 0) (set! delimiter (first maybe-args)))
    (if (> (length maybe-args) 1) (set! grammar (second maybe-args)))
    (let loop ((out (open-output-string)) (string-list string-list))
      (if (null? string-list)
	  (begin
	    (close-output-port out)
	    (get-output-string out))
	  (case grammar
	    ((infix)
	     (display (car string-list) out)
	     (unless (null? (cdr string-list)) (display delimiter out))
	     (loop out (cdr string-list)))
	    ((suffix)
	     (display (car string-list) out)
	     (display delimiter out)
	     (loop out (cdr string-list)))
	    ((prefix)
	     (display delimiter out)
	     (display (car string-list) out)
	     (loop out (cdr string-list))))))))

(define (string-take s n) (substring s 0 n))
(define (string-drop s n) (substring s n))
(define (string-take-right s n) (substring s (- n)))
(define (string-drop-right s n) (substring s 0 (- n)))

(define (string-index s . maybe-what+start+end)
  (let ((what '(#\space #\tab #\newline #\return))
	(start 0)
	(end (string-length s))
	(pred #f))
    (unless (< (length maybe-what+start+end) 1) (set! what (first maybe-what+start+end)))
    (unless (< (length maybe-what+start+end) 2) (set! start (second maybe-what+start+end)))
    (unless (< (length maybe-what+start+end) 3) (set! end (third maybe-what+start+end)))
    (cond ((list? what) (set! pred (lambda (ch) (memv ch what))))
	  ((char? what) (set! pred (lambda (ch) (eqv? ch what)))))
    (call/cc (lambda (k)
	       (do ((i start (+ i 1)))
		   ((= i end) #f)
		 (if (pred (string-ref s i)) (k i)))))))
      
(define (string-index-right s . maybe-what+start+end)
  (let ((what '(#\space #\tab #\newline #\return))
	(start 0)
	(end (string-length s))
	(pred #f))
    (unless (< (length maybe-what+start+end) 1) (set! what (first maybe-what+start+end)))
    (unless (< (length maybe-what+start+end) 2) (set! start (second maybe-what+start+end)))
    (unless (< (length maybe-what+start+end) 3) (set! end (third maybe-what+start+end)))
    (cond ((list? what) (set! pred (lambda (ch) (memv ch what))))
	  ((char? what) (set! pred (lambda (ch) (eqv? ch what)))))
    (call/cc (lambda (k)
	       (do ((i (- end 1) (- i 1)))
		   ((< i start) #f)
		 (if (pred (string-ref s i)) (k i)))))))
      
(define (string-skip s . maybe-what+start+end)
  (let ((what '(#\space #\tab #\newline #\return))
	(start 0)
	(end (string-length s))
	(pred #f))
    (unless (< (length maybe-what+start+end) 1) (set! what (first maybe-what+start+end)))
    (unless (< (length maybe-what+start+end) 2) (set! start (second maybe-what+start+end)))
    (unless (< (length maybe-what+start+end) 3) (set! end (third maybe-what+start+end)))
    (cond ((list? what) (set! pred (lambda (ch) (memv ch what))))
	  ((char? what) (set! pred (lambda (ch) (eqv? ch what)))))
    (call/cc (lambda (k)
	       (do ((i start (+ i 1)))
		   ((= i end) #f)
		 (if (not (pred (string-ref s i))) (k i)))))))
      
(define (string-skip-right s . maybe-what+start+end)
  (let ((what '(#\space #\tab #\newline #\return))
	(start 0)
	(end (string-length s))
	(pred #f))
    (unless (< (length maybe-what+start+end) 1) (set! what (first maybe-what+start+end)))
    (unless (< (length maybe-what+start+end) 2) (set! start (second maybe-what+start+end)))
    (unless (< (length maybe-what+start+end) 3) (set! end (third maybe-what+start+end)))
    (cond ((list? what) (set! pred (lambda (ch) (memv ch what))))
	  ((char? what) (set! pred (lambda (ch) (eqv? ch what)))))
    (call/cc (lambda (k)
	       (do ((i (- end 1) (- i 1)))
		   ((< i start) #f)
		 (if (not (pred (string-ref s i))) (k i)))))))
      
(define (string-trim s . maybe-what+start+end)
  (let ((what '(#\space #\tab #\newline #\return))
	(start 0)
	(end (string-length s))
	(pred #f))
    (unless (< (length maybe-what+start+end) 1) (set! what (first maybe-what+start+end)))
    (unless (< (length maybe-what+start+end) 2) (set! start (second maybe-what+start+end)))
    (unless (< (length maybe-what+start+end) 3) (set! end (third maybe-what+start+end)))
    (cond ((list? what) (set! pred (lambda (ch) (memv ch what))))
	  ((char? what) (set! pred (lambda (ch) (eqv? ch what)))))
    (let ((i (string-skip s what start end pred)))
      (if i
	  (substring s i)
	  s))))

(define (string-trim-right s . maybe-what+start+end)
  (let ((what '(#\space #\tab #\newline #\return))
	(start 0)
	(end (string-length s))
	(pred #f))
    (unless (< (length maybe-what+start+end) 1) (set! what (first maybe-what+start+end)))
    (unless (< (length maybe-what+start+end) 2) (set! start (second maybe-what+start+end)))
    (unless (< (length maybe-what+start+end) 3) (set! end (third maybe-what+start+end)))
    (cond ((list? what) (set! pred (lambda (ch) (memv ch what))))
	  ((char? what) (set! pred (lambda (ch) (eqv? ch what)))))
    (let ((i (string-skip-right s what start end pred)))
      (if i
	  (substring s 0 (+ i 1))
	  s))))

(define (string-trim-both s . maybe-what+start+end)
  (let ((what '(#\space #\tab #\newline #\return))
	(start 0)
	(end (string-length s))
	(pred #f))
    (unless (< (length maybe-what+start+end) 1) (set! what (first maybe-what+start+end)))
    (unless (< (length maybe-what+start+end) 2) (set! start (second maybe-what+start+end)))
    (unless (< (length maybe-what+start+end) 3) (set! end (third maybe-what+start+end)))
    (cond ((list? what) (set! pred (lambda (ch) (memv ch what))))
	  ((char? what) (set! pred (lambda (ch) (eqv? ch what)))))
    (let ((i1 (string-skip s what start end pred))
	  (i2 (string-skip-right s what start end pred)))
      (cond ((and i1 i2) (substring s i1 (+ i2 1)))
	    (i1 (substring s i1))
	    (i2 (substring s 0 (+ i2 1)))
	    (else s)))))

(define (zip a b)
  (cond ((null? a)
	 (if (null? b)
	     '()
	     (error 'zip "lists have different lengths")))
	((null? b)
	 (error 'zip "lists have different lengths"))
	(else
	 (cons (cons (car a) (car b)) (zip (cdr a) (cdr b))))))
