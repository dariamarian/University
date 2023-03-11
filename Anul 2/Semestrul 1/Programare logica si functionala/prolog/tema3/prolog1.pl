% 15. Se da un numar n pozitiv. Se cere sa se determine toate
% descompunerile lui n ca suma de numere naturale
% consecutive.
%
% solution(N:numar,E:numar,L:lista)
% N-numarul pe care il descompunem la fiecare pas
% E-elementul de la care incepem
% L-lista rezultata
% model de flux(i,i,o)

% decompose(N:numar,E:numar,L:lista)
% N-numarul pe care il descompunem
% E-elementul de la care incepem
% L-lista rezultata
% model de flux(i,i,o)

% main(N:numar,L:lista)
% N-numarul pentru care se determina descompunerile
% L-lista rezultata cu toate descompunerile lui n
% model de flux(i,o)


solution(0, _, []). %daca N e 0, indiferent de E, rezultatul va fi lista vida
solution(N, E, [E|L]) :- N >= E,
    NN is N - E,
    NE is E + 1,
    solution(NN, NE, L). %solution(n-e,e+1,l)

decompose(N, E, L) :- E < N, %merge pana cand n=e
    solution(N, E, L).
decompose(N, E, L) :- E < N,
    E1 is E + 1,
    decompose(N, E1, L).

main(N, L) :-
    findall(LPartiala, decompose(N, 1, LPartiala), L).

% Exemple:
% main(16,L). => L = []
% main(6,L).  => L = [[1,2,3]].
% main(15,L). => L = [[1, 2, 3, 4, 5], [4, 5, 6], [7, 8]].
% main(21,L). => L = [[1, 2, 3, 4, 5, 6], [6, 7, 8], [10, 11]].
