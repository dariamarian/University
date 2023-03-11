; (load 'lab2)
; parcurgerea in postordine - SDR

; Arbore de tip 1:
; (nod nr-subarbori lista-subarbore-1 lista-subarbore-2 ...)(1)
; (A 2 B 0 C 2 D 0 E 0) (1)


; 15.Sa se construiasca lista nodurilor unui arbore de tipul (1) parcurs in postordine.

; stanga (arbore: lista, varfuri: numar, muchii: numar)
; arbore - arborele care se parcurge la stanga
; varfuri - numarul de varfuri
; muchii - numarul de muchii

(defun stanga(arbore varfuri muchii)

    (cond
        ((null arbore) nil)
        ((= varfuri (+ 1 muchii)) nil)
        (T (cons (car arbore) (cons (cadr arbore) (stanga (cddr arbore) (+ 1 varfuri) (+ (cadr arbore) muchii)))))
    )
)


; dreapta (arbore: lista, varfuri: numar, muchii: numar)
; arbore - arborele care se parcurge la dreapta
; varfuri - numarul de varfuri
; muchii - numarul de muchii

(defun dreapta(arbore varfuri muchii)

    (cond
        ((null arbore) nil)
        ((= varfuri (+ 1 muchii)) arbore)
        (T (dreapta (cddr arbore) (+ 1 varfuri) (+ (cadr arbore) muchii)))
    )
)

; postordine (arbore: lista)
; arbore - arborele care se parcurge in postordine

(defun postordine(arbore)
    (cond
        ((null arbore) nil)
        (T (append (postordine (stanga (cddr arbore) 0 0) )  (postordine (dreapta (cddr arbore) 0 0) ) (list (car arbore))   ) )
    )
)

(print (postordine '(A 2 B 0 C 2 D 0 E 0)))
;(B D E C A)
(print (postordine '(A 2 B 2 C 0 D 0 E 1 F 0)))
;(C D B F E A)
(print (postordine '(A 1 B 1 C 0)));
;(C B A)