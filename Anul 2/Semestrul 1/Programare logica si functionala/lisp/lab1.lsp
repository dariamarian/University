; (load 'lab1)
; (car'(A B C))=A
; (cdr'(A B C))=(B C)

; a)Definiti o functie care intoarce produsul a doi vectori.

; produs (a: lista, b: lista)
; a - primul vector
; b - al doilea vector

; Exemple:
; (produs '(1 2 3) '(4 5 6)) => 32
; (produs '(1 2 3 4) '(4 5 6)) => 32
; (produs '(1 2 3 4) '()) => 0
(defun produs (a b)
    (if (or (null a) (null b))
        0
        (+ (* (car a) (car b)) (produs (cdr a) (cdr b)))
    )
)

; b)Sa se construiasca o functie care intoarce adancimea unei liste.

; my_max(a: numar, b: numar)
; a - primul numar care se compara
; b - al doilea numar care se compara 

; adancime(a: lista, l: adancimea)
; a - lista pentru care se calculeaza adancimea
; l - adancimea listei care este calculata

; Exemple:
; (adancime '(5 (6 (7))) 0) => 2
; (adancime '(6 (2 3 (4) (5) (6 (7)))) 0) => 3
; (adancime '(7 (6 (2 3 (4) (5) (6 (7))))) 0) => 4

(defun my_max (a b)
    (if (> a b) a b)
)

(defun adancime (a l)
    (cond
        ((null a) l)
        ((listp (car a)) (my_max (adancime (car a) (+ l 1)) (adancime (cdr a) l)))
        (T (adancime (cdr a) l))
    )
)

; c)Definiti  o  functie  care  sorteaza  fara  pastrarea  dublurilor  o  lista liniara.

; insert(l: lista, e: element)
; l - lista in care trebuie inserat elementul
; e - elementul care trebuie inserat

; sortare(l: lista)
; l - lista care trebuie sortata

; Exemple: 
; (sortare '(1 3 2 5 6 7 7 3 9)) => (1 2 3 5 6 7 9)
; (sortare '(11 5 7 7 11)) => (5 7 11)

(defun insert (l e)
    (cond
        ((null l) (list e))
        ((equal (car l) e) l)
        ((< e (car l)) (cons e l))
        (T (cons (car l) (insert (cdr l) e)))
    )
)

(defun sortare (l)
    (cond
        ((null l) nil)
        (T (insert (sortare (cdr l)) (car l)))
    )
)

; d)Sa se scrie o functie care intoarce intersectia a doua multimi.

; remove_first(l: lista, e: element)
; l - lista din care elimina elementul 
; e - elementul pe care il elimina

; contains(l: lista, e: element)
; l - lista in care se cauta elementul 
; e - elementul care se cauta

; intersectie(l: lista, k: lista)
; l - prima multime
; e - a doua multime

; Exemple:
; (intersectie '(1 2 3 4 5) '(1 5 6 7 9)) => (1 5)
; (intersectie '(1 2 3 4 5) '(6 7 8)) => NIL

(defun remove_first (l e)
    (cond 
        ((null l) nil)
        ((equal (car l) e) (cdr l))
        (T (cons (car l) (remove_first (cdr l) e)))
    )
)

(defun contains (l e)
    (cond
        ((null l) nil)
        ((equal (car l) e) T)
        (T (contains (cdr l) e))
     )
)

(defun intersectie (l k)
    (cond
        ((or (null l) (null k)) nil)
        ((contains k (car l)) (cons (car l) (intersectie (cdr l) (remove_first (cdr k) (car l)))))
        (T (intersectie (cdr l) k))
    )
)