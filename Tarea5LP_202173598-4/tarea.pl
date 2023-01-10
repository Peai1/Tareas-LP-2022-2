% Pregunta 1.

% Predicado que recibe tres listas L, P, I donde P es una lista que tiene
% todos los elementos en las posiciones pares de L e I una lista que
% contiene todos los elementos en posiciones impares de L.

sepparimpar([], [], []).
sepparimpar([Impar], [Impar], []).
sepparimpar([Impar,Par|Resto], [Impar|ImparResto], [Par|ParResto]) :-
    sepparimpar(Resto,ImparResto,ParResto).

% Pregunta 2.

% Predicado que recibe una lista L, un entero Min y un entero Max,
% donde la lista L contiene todos los numeros enteros entre Min y Max (sin contar Max)
% y la lista L puede contener mas numeros

todosrango(_,Min,Max):-
    Min = Max.
todosrango(L,Min,Max):-
    member(Min,L),
    Min1 is Min + 1,
    todosrango(L,Min1,Max).

% Pregunta 3.

% Predicado auxiliar que elimina un elemento de la lista y retorna la misma
% lista pero sin el elemento a eliminar la cual es utilizada para hacer el predicado rangomax

eliminar(_,[],[]).
eliminar(H1,[H1|T1],T2):-
    eliminar(H1,T1,T2).
eliminar(H1,[H2|T1],[H2|T2]):-
    H2 \= H1,
    eliminar(H1,T1,T2).

% Predicado que recibe una lista L, un entero Min y un entero Max, donde L es una lista
% donde todos los enteros de la lista L, estan en el intervalo [Min, Max)

rangomax([],Min,Max):-
    Min = Max.
rangomax(L,Min,Max):-
    member(Min,L),
    eliminar(Min,L,L2),
    Min1 is Min + 1,
    rangomax(L2,Min1,Max).

% Pregunta 4.

% Predicado que recibe una lista L, un entero Min y un entero Max, donde L
% es una lista de largo Max-Min y todos sus elementos en posicones pares se encuentran entre
% [Min, (Max+Min)/2) y todos sus elementos en posiciones impares se encuentran entre [(Max+Min)/2,Max)

chicograndechico(L,Min,Max):-
    X1 is Max-Min,
    length(L,X1),
    MaxPares is round((Max + Min)/2),
    MinImpares is round((Max + Min)/2),
    sepparimpar(L,P,I),
    todosrango(P,Min,MaxPares),
    todosrango(I,MinImpares,Max).













