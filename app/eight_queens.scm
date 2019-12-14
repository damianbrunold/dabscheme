;;; Calculates the solutions to the eight queens problem
;;; which consists in placing eight queens on a chess board
;;; with no queen able to take any other queen.

(define empty-board #(". . . . "
		      " . . . ."
		      ". . . . "
		      " . . . ."
		      ". . . . "
		      " . . . ."
		      ". . . . "
		      " . . . ."))

(define (print-board board)
  (do ((y 8 (- y 1)))
      ((= y 0) (display "  abcdefgh") (newline) (values))
    (display y)
    (display #\space)
    (display (vector-ref board (- y 1)))
    (newline)))

(define (copy-board board)
  (list->vector (map string-copy (vector->list board))))

(define (set-queen board x y)
  (let ((c (copy-board board)))
    (string-set! (vector-ref c y) x #\*)
    c))

(define (count-queens line)
  (let ((n (string-length line)) (count 0))
    (do ((i 0 (+ i 1)))
	((= i n) count)
      (if (char=? (string-ref line i) #\*)
	  (set! count (+ count 1))))))

(define (get-row board row)
  (vector-ref board row))

(define (get-col board col)
  (let ((c (make-string 8)))
    (do ((row 0 (+ row 1)))
	((= row 8) c)
      (string-set! c row (string-ref (vector-ref board row) col)))))

(define (get-diag1 board index)
  )

(define (get-diag2 board index)
  )

(define (valid-lines? board fetch-line max-line)
  (call/cc (lambda (k)
	     (do ((n 0 (+ n 1)))
		 ((= n max-line) #t)
	       (if (> (count-queens (fetch-line board n)) 1)
		   (k #f))))))

(define (valid-board? board)
  (and (valid-lines? board get-row 8)
       (valid-lines? board get-col 8)
       (valid-lines? board get-diag1 15)
       (valid-lines? board get-diag2 15)))

(define (find-first-solution board)
  (define (find-first-solution* board x)
    (define (iterate-rows board x y)
      (let ((solution (find-first-solution* (set-queen board x y))))
	(cond ((= y 8) solution)
	      (else (iterate-rows board x (+ y 1))))))
    (cond ((not (valid-board board)) #f)
	  ((= x 8) board)
	  (else (iterate-rows board x 0))))
  (find-first-solution* board 0))

