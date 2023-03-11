% Sa se scrie un predicat care adauga intr-o lista dupa fiecare element
% par valoarea 1.

% adauga(L1: lista, L2: lista)
% L1 - lista de numere in care adaugam
% L2 - rezultatul
% model de flux (i,o)

adauga([],[]). %daca lista este vida, lista rezultata e vida
adauga([H|T],[H|[1|T1]]):-
    H mod 2=:=0,!, %daca primul element e par,
                   %se ignora restul clauzelor
    adauga(T,T1).  %se adauga elementul par, si 1
adauga([H|T],[H|T1]):-
    adauga(T,T1).  %se adauga doar elementul impar, fara 1

% Exemple:
% adauga([],L). => L=[]
% adauga([1],L). => L=[1]
% adauga([2],L). => L=[2,1]
% adauga([1,3,5,7],L). => L=[1,3,5,7]
% adauga([1,2,5,8],L). => L=[1,2,1,5,8,1]
% adauga([2,4,8],L). => L=[2,1,4,1,8,1]

