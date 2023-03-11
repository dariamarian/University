% 2a.Sa se sorteze o lista cu pastrarea dublurilor
% b. Se da o lista eterogena, formata din numere intregi si liste de
% numere. Sa se sorteze fiecare sublista cu pastrarea dublurilor. De ex:

% inserare(E:element,L1:lista,L2:lista)
% E-elementul pe care il inseram
% L1-lista in care inseram
% L2-lista rezultata
% model de flux(i,i,o)
% determinist

% sortare(L1:lista, L2:lista)
% L1-lista de numere pe care o sortam
% L2-rezultatul
% model de flux(i,o)
% determinist

% sortare_lista(LE1:lista eterogena, LE2:lista eterogena)
% LE1-lista eterogena pe care lucram
% LE2-lista eterogena rezultata
% model de flux(i,o)
% determinist

inserare(E,[],[E]):-!. % daca L1 e vida, L2 va contine doar pe                              E, se ignora restul clauzelor
inserare(E,[H|T],[H|L]):-
    E>=H,!,            % daca elementul e mai mare sau egal decat primul element din lista, se ignora restul clauzelor
    inserare(E,T,L).   % se insereaza elementul E in lista fara H
inserare(E,[H|T],[E|[H|T]]):-!.


sortare([],[]):-!. % daca L1 e vida, L2 va fi tot vida, se ignora restul clauzelor
sortare([H|T],L):- % H|T - H e primul el, T e restul listei
    sortare(T,L1), % sorteaza lista fara primul element
    inserare(H,L1,L). % insereaza primul element

sortare_lista([],[]):-!. % daca LE1 e vida, LE2 va fi tot vida
sortare_lista([H|T],L) :-  % daca primul element e numar, pune elementul H in lista finala
    number(H), !,L=[H|L1],
    sortare_lista(T,L1).
sortare_lista([H|T],L) :- % daca primul element e lista, sorteaza sublista si pune rezultatul in S, ulterior pune lista rezultata S in lista finala
   is_list(H), !, sortare(H,S), L=[S|L1],
   sortare_lista(T,L1).


% Exemple:
% sortare([],L). => L=[]
% sortare([1,2,3,3,5],L). => L=[1,2,3,3,5]
% sortare([4,2,6,2,3,4],L). => L=[2,2,3,4,4,6]
%
% sortare_lista([],L). => L=[]
% sortare_lista([1,9,3,3,4],L). => L=[1,9,3,3,4]
% sortare_lista([1,[2,4,0],9],L). => L=[1,[0,2,4],9]
% sortare_lista([1, 2,[4, 1, 4], 3, 6, [7, 10, 1, 3, 9], 5, [1, 1, 1],7],L).
% => L=[1,2,[1, 4, 4], 3, 6, [1, 3, 7, 9, 10], 5, [1, 1, 1], 7].





