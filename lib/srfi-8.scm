(defmacro receive
  (lambda (expr)
    (let ((formals (first expr))
	  (expression (second expr))
	  (body (rest2 expr)))
      `(call-with-values (lambda () ,expression)
	 (lambda ,formals ,@body)))))

