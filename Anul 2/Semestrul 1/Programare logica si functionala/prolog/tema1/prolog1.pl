% Sa se scrie un predicat care intoarce diferenta a doua multimi

% diferenta(L1: lista, L2: lista, L3:lista)
% L1, L2 - listele de numere pentru care calculam diferenta
% L3 - rezultatul
% model de flux (i,i,o)

membru(E,[E|_]):-!.
membru(E,[_|T]):-
    membru(E,T).

diferenta(L,[],L):-!. %daca lista 2 e vida, se ignora restul clauzelor
                      %(L3 va fi egal cu L1)
diferenta([],_,[]). %daca lista 1 e vida, indiferent de continutul
                    %listei 2, lista 3 va fi vida
diferenta([H|T],L,[H|T1]):- %H|T - H e primul el, T e restul listei
    not(membru(H,L)),!,     %daca H(primul el) nu e in L2,
                            %se ignora restul clauzelor
    diferenta(T,L,T1).      %daca nu e membru, face asta
diferenta([_|T],L,L1):- %impartim lista in doua, dar nu ne intereseaza
                        %primul element
    diferenta(T,L,L1).  %daca e membru face asta(ignora primul element)

% Exemple:
% diferenta([2,3],[],L). => L=[2,3]
% diferenta([],[],L). => L=[]
% diferenta([],[1,5],L). => L=[]
% diferenta([1,3,5,8],[1,5],L). => L=[3,8]
% diferenta([1,5],[1,5],L). => L=[]










