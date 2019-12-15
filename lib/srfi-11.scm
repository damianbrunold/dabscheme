; requires with-values

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

