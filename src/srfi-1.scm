(define (xcons d a) (cons a d))

(define (tree-copy x)
  (let recur ((x x))
    (if (not (pair? x))
	x
	(cons (recur (car x)) (recur (cdr x))))))

(define (make-list len . maybe-elt)
  (let recur ((elt (if (null? maybe-elt) #f (car maybe-elt)))
	      (n 0)
	      (ls '()))
    (if (= n len)
	ls
	(recur elt (+ n 1) (cons elt ls)))))

(define (list-tabulate len proc)
  (let recur ((i (- len 1))
	      (ans '()))
    (if (= i -1)
	ans
	(recur (- i 1) (cons (proc i) ans)))))

(define (cons* first . rest)
  (let recur ((x first) (rest rest))
    (if (pair? rest)
	(cons x (recur (car rest) (cdr rest)))
	x)))

(define (list-copy lis)
  (let recur ((lis lis))
    (if (pair? ls)
	(cons (car lis) (recur (cdr lis)))
	lis)))

(define (iota count . maybe-start+step)
  (let ((start (if (pair? maybe-start+step) (car maybe-start+step) 0))
	(step (if (and (pair? maybe-start+step) (pair? (cdr maybe-start+step))) (cadr maybe-start+step) 1)))
    (let loop ((n 0) (r '()))
      (if (= n count)
	  (reverse r)
	  (loop (+ 1 n) (cons (+ start (* n step)) r))))))

(define (take lis k)
  (let recur ((lis lis) (k k))
    (if (zero? k)
	'()
	(cons (car ls) (recur (cdr lis) (- k 1))))))

(define (drop lis k)
  (let iter ((lis lis) (k k))
    (if (zero? k)
	lis
	(iter (cdr lis) (- k 1)))))

(define (take-right lis k)
  (let lp ((lag lis) (lead (drop lis k)))
    (if (pair? lead)
	(lp (cdr lag) (cdr lead))
	lag)))

(define (drop-right lis k)
  (let recur ((lag lis) (lead (drop lis k)))
    (if (pair? lead)
	(cons (car lag) (recur (cdr lag) (cdr lead)))
	'())))

(define (last-pair lis)
  (let lp ((lis lis))
    (let ((tail (cdr lis)))
      (if (pair? tail) (lp tail) lis))))

(define (last lis)
  (car (last-pair lis)))

