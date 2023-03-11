; (load 'lab3)
; (if (test-clause) (action1) (action2))

; 13. Definiti o functie care substituie un element prin altul la toate nivelurile unei liste date.

; inlocuieste(lista-lista, element_cautat-numar, element_nou-numar)
; lista = lista in care se substituie elementele
; element_cautat = elementul care trebuie substituit
; element_nou = elementul cu care se substituie
(defun inlocuieste(lista element_cautat element_nou)
    (cond
        ((and (numberp lista) (if (= lista element_cautat) element_nou lista)))
        ((listp lista) (mapcar #'(lambda (a) (inlocuieste a element_cautat element_nou)) lista))
    )
)

(print (inlocuieste '(1 2 (1 2 (3 1) 2)) 1 0))