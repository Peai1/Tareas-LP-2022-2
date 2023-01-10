#lang scheme

(current-namespace (make-base-namespace))

;; Pregunta 1

;; Funcion que se le entrega una lista de numeros (lista) y un numero (n) donde debe
;; retornar una lista con todos los numeros entre 0 y n (sin incluir) que no estan en
;; lista

;; parametros:
;; lista: lista de numeros
;; n: numero que limita los numeros de la lista a retornar

(define (inverso lista n)
  (let recursion ((lista lista) (n n) (i 0))
    (if (= i n)
        '()
        (cond ((equal? (member i lista) false)
               (cons i (recursion lista n (+ i 1))))
              (else (recursion lista n (+ i 1)))
              )
        )
    )
  )


;; Pregunta 2

;; Funcion que dado una lista de numeros (lista), un numero (umbral) y un caracter (tipo),
;; segun si tipo es 'M', retorna una lista con todas las posiciones de los elemetnos de lista que sean
;; mayores a umbral y si tipo es 'm', retorna todas las posiciones de los elementos menores que umbral

;; parametros:
;; lista: lista de numeros
;; umbral: numero a ver si es mayor o menor que los de la lista
;; tipo: caracter que puede ser 'M' o 'm'

; Simple

(define (umbral_simple lista umbral tipo)
  (let recursion ((lista lista) (umbral umbral) (tipo tipo) (i 0))
    (if (null? lista)
        '()
        (cond ((and (eqv? tipo '#\M)(> (car lista) umbral))
               (cons i (recursion (cdr lista) umbral tipo (+ i 1))))
              ((and (eqv? tipo '#\m)(< (car lista) umbral))
               (cons i (recursion (cdr lista) umbral tipo (+ i 1))))
              (else (recursion (cdr lista) umbral tipo (+ i 1)))
              )
        )
    )
  )

; Cola

(define (umbral_cola lista umbral tipo)
  (let recursion ((lista lista) (umbral umbral) (tipo tipo) (aux '()) (i 0))
    (if (null? lista)
        (reverse aux)
        (cond ((and (eqv? tipo '#\M)(> (car lista) umbral))
               (recursion (cdr lista) umbral tipo (cons i aux) (+ i 1)))
              ((and (eqv? tipo '#\m)(< (car lista) umbral))
               (recursion (cdr lista) umbral tipo (cons i aux) (+ i 1)))
              (else (recursion (cdr lista) umbral tipo aux (+ i 1)))
              )
        )
    )
  )


; Pregunta 3

;; Funcion que dado dos listas de numeros (lista y seleccion) y una funcion lambda (f), por
;; cada numero en la lista, si su indice esta en seleccion, le aplica la funcion f

;; parametros:
;; lista: lista con numeros
;; seleccion: lista con los indices de la lista que se les aplica la funcion f
;; f: funcion lambda a aplicar

; Simple

(define (modsel_simple lista seleccion f)
  (let recursion ((lista lista) (seleccion seleccion) (f f) (i 0))
    (if (null? lista)
        '()
        (cond ((equal? (member i seleccion) false)
               (cons (car lista) (recursion (cdr lista) seleccion f (+ i 1))))
              (else (cons (f (car lista)) (recursion (cdr lista) seleccion f (+ i 1))))
              )
        )
    )
  )

; Cola

(define (modsel_cola lista seleccion f)
  (let recursion ((lista lista) (seleccion seleccion) (f f) (aux '()) (i 0))
    (if (null? lista)
        (reverse aux)
        (cond ((equal? (member i seleccion) false)
               (recursion (cdr lista) seleccion f (cons (car lista) aux) (+ i 1)))
              (else (recursion (cdr lista) seleccion f (cons (f (car lista)) aux) (+ i 1)))
              )
        )
    )
  )

; Pregunta 4

;; Funcion que dado una lista de enteros (lista), un numero (umbral) y dos funciones lambda (fM y fm),
;; haciendo uso de las funciones implementadas anteriormente, retorna una lista con dos numeros, donde el primero
;; es la cantidad de numeros mayores que el umbral que al aplicarles fM siguen siendo mayores que este,
;; y el segundo es la cantidad de numeros menores que el umbral que al aplicarles fm siguen siendo
;; menores que este

;; parametros:
;; lista: lista de enteros
;; umbral: numero con el cual se comparara para ver si son mayores o menores
;; fM: funcion lambda a aplicar
;; fm: funcion lambda a aplicar


(define (estables lista umbral fM fm)
  (let recursion ((listafM (modsel_simple lista (inverso '() (length lista)) fM)) (listafm (modsel_simple lista (inverso '() (length lista)) fm)) (umbral umbral) (mayor 0) (menor 0) (lista lista))
    (if (null? lista)
        (list mayor menor)
        (cond ((and (< (car lista) umbral) (< (car listafm) umbral))
               (recursion (cdr listafM) (cdr listafm) umbral mayor (+ menor 1) (cdr lista)))
              ((and (> (car lista) umbral) (> (car listafM) umbral))
               (recursion (cdr listafM) (cdr listafm) umbral (+ mayor 1) menor (cdr lista)))
              (else (recursion (cdr listafM) (cdr listafm) umbral mayor menor (cdr lista)))
              )
        )
    )
  )

; Pregunta 5

;; Funcion que dado una lista de enteros (lista), la posicion de una lista de enteros dentro de esta
;; lista de listas (pos), un numero entre 1 y 3 que indica una operacion a realizar sobre la lista (op)
;; y una lista con los parametros necesarios para esa operacion (params)
;; si op es 1, aplica umbral sobre la lista en la posicion pos
;; si op es 2, aplica modsel sobre la lista en la posicion pos
;; si op es 3, aplica estables sobre la lista en la posicion pos

;; parametros:
;; lista: lista de listas de enteros
;; pos: posicion de una lista de enteros dntro de lista
;; op: numero entre 1 y 3
;; params: lista con parametros para hacer la operacion

(define (query lista pos op params)
  (let recursion ((lista lista) (pos pos) (op op) (params params) (i 0))
    (if (null? lista)
        '()
        (cond ((and (= i pos) (= op 1))
               (umbral_simple (car lista) (car params) (car (cdr params)) ))
              ((and (= i pos) (= op 2))
               (modsel_simple (car lista) (car params) (eval (car (cdr params)))))
              ((and (= i pos) (= op 3))
               (estables (car lista) (car params) (eval (car (cdr params))) (eval (car (cdr (cdr params))))))
              (else (recursion (cdr lista) pos op params (+ i 1))))
        )
    )
  )


(inverso '(1 3 7) 10)

(umbral_simple '(15 2 1 3 27 5 10) 5 #\M)

(umbral_cola '(15 2 1 3 27 5 10) 5 #\m)

(modsel_simple '(15 2 1 3 27 5 10) '(0 4 6) (lambda (x) (modulo x 2)) )

(modsel_cola '(15 2 1 3 27 5 10) '(3 1 2) (lambda (x) (+ x 5)) )

(estables '(15 2 1 3 27 5 10) 5 (lambda (x) (/ x 2)) (lambda (x) (* x 2)) )

(query '((0 1 2 3 4) (4 3 2 1 0) (15 2 1 3 27 5 10)) 1 1 '(1 #\M))

(query '((0 1 2 3 4) (4 3 2 1 0) (15 2 1 3 27 5 10)) 0 2 '((0 4) (lambda (x) (+ x 100))))

(query '((0 1 2 3 4) (4 3 2 1 0) (15 2 1 3 27 5 10)) 2 3 '(5 (lambda (x)(/ x 2)) (lambda (x) (* x 2))) )
           
  
              
        
 
  
  
