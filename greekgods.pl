/*
  Family tree of the Greek gods in Prolog
  as according to Hesiod's Theogony
 
  Nathan Anderle
  Carlton Bennett
 */

%Ancient gods representing ideas and natural phenomenon.
primordial(chaos).
primordial(tartarus).
primordial(gaia).
primordial(eros).
primordial(erebus).
primordial(nyx).
primordial(uranus).
primordial(pontus).
primordial(aether).
primordial(hemera).

%Children of Uranus and Gaia. The old gods the Titans.
titan(oceanus).
titan(tethys).
titan(mnemosyne).
titan(cronus).
titan(rhea).
titan(themis).
titan(coeus).
titan(phoebe).
titan(hyperion).
titan(theia).
titan(iapetus).
titan(clymene).
titan(atlas).
titan(leto).
titan(eos).
titan(helios).
titan(selene).
titan(prometheus).
titan(epimetheus).
titan(dione).

%A human.
human(semele).

%Lesser spirits that represent different domains.
nymph(pleione).
nymph(maia).
nymph(muses).

%Most significant 12 Olympians.
olympian(zeus).
olympian(aphrodite).
olympian(hera).
olympian(poseidon).
olympian(hestia).
olympian(hades).
olympian(demeter).
olympian(dionysus).
olympian(hermes).
olympian(apollo).
olympian(artemis).
olympian(athena).
olympian(ares).
olympian(hephaestus).
olympian(persephone).
olympian(hebe).

%Genderless deities
neuter(chaos).

%Male identified deities
male(helios).
male(prometheus).
male(epimetheus).
male(aether).

male(tartarus).
male(uranus).
male(pontus).
male(oceanus).
male(iapetus).
male(coeus).
male(cronus).
male(hyperion).
male(atlas).
male(zeus).
male(dionysus).
male(hermes).
male(apollo).
male(poseidon).
male(hades).
male(ares).
male(haephaestus).

%Female identified deities
female(eos).
female(hemera).
female(selene).
female(dione).
female(gaia).
female(tethys).
female(phoebe).
female(rhea).
female(themis).
female(mnemosyne).
female(theia).
female(pleione).
female(semele).
female(maia).
female(leto).
female(artemis).
female(athena).
female(hera).
female(hestia).
female(demeter).
female(persephone).
female(hebe).

%Primordial mess
parent(chaos, nyx).
parent(chaos, erebus).
parent(chaos, aether).
parent(chaos, hemera).
parent(nyx, erebus).
parent(nyx, aether).
parent(nyx, hemera).
parent(erebus, aether).
parent(erebus, hemera).

parent(aether, gaia).
parent(aether, tartarus).
parent(aether, eros).
parent(aether, pontus).
parent(hemera, gaia).
parent(hemera, tartarus).
parent(hemera, eros).
parent(hemera, pontus).

%Gaia mess/Titans
parent(gaia, uranus).
parent(gaia, oceanus).
parent(gaia, tethys).
parent(gaia, mnemosyne).
parent(gaia, cronus).
parent(gaia, rhea).
parent(gaia, themis).
parent(gaia, coeus).
parent(gaia, phoebe).
parent(gaia, hyperion).
parent(gaia, theia).
parent(gaia, iapetus).

parent(uranus, oceanus).
parent(uranus, tethys).
parent(uranus, mnemosyne).
parent(uranus, cronus).
parent(uranus, rhea).
parent(uranus, themis).
parent(uranus, coeus).
parent(uranus, phoebe).
parent(uranus, hyperion).
parent(uranus, theia).
parent(uranus, iapetus).

%Cronus Rhea Mess
parent(cronus, zeus).
parent(cronus, hera).
parent(cronus, hades).
parent(cronus, poseidon).
parent(cronus, demeter).
parent(cronus, hestia).

parent(rhea, zeus).
parent(rhea, hera).
parent(rhea, hades).
parent(rhea, poseidon).
parent(rhea, demeter).
parent(rhea, hestia).

%Clymene pleione
parent(oceanus, clymene).
parent(tethys, clymene).
parent(oceanus, pleione).
parent(tethys, pleione).

%Zeus mess
parent(zeus, athena).

parent(zeus, muses).
parent(mnemosyne, muses).

parent(zeus, hebe).
parent(zeus, ares).
parent(zeus, hephaestus).
parent(hera, hebe).
parent(hera, ares).
parent(hera, hephaestus).

parent(zeus, dionysus).
parent(semele, dionysus).

parent(zeus, artemis).
parent(zeus, apollo).
parent(leto, artemis).
parent(leto, apollo).

parent(zeus, hermes).
parent(maia, hermes).

parent(zeus, aphrodite).
parent(dione, aphrodite).

parent(zeus, persephone).
parent(demeter, persephone).

%Leto
parent(coeus, leto).
parent(phoebe, leto).

%Hyperion Theia
parent(hyperion, eos).
parent(hyperion, selene).
parent(hyperion, helios).
parent(theia, eos).
parent(theia, selene).
parent(theia, helios).

%Iapetus Clymene
parent(iapetus, atlas).
parent(iapetus, prometheus).
parent(iapetus, epimetheus).
parent(clymene, atlas).
parent(clymene, prometheus).
parent(clymene, epimetheus).

%Maia
parent(pleione, maia).
parent(atlas, maia).

%Dione
parent(epimetheus, dione).

%Rules
mother(X,Y) :- female(X), parent(X,Y).
father(X,Y) :- male(X), parent(X,Y).

ancestor(A,B) :- parent(A,B).
ancestor(A,C) :- parent(A,B), ancestor(B,C).

descendant(A,B) :- parent(B,A).
descendant(A,C) :- parent(B,A), descendant(B,C).
descendant(A,B,C) :- descendant(A,B), descendant(A,C).

sibling(A,B) :- parent(C,A), parent(C,B), A \== B.

aunt_or_uncle(A,B) :- parent(C,B), sibling(C,A).

cousin(A,B) :- aunt_or_uncle(C,A), parent(C,B).

second_cousin(A,B) :- parent(C,A), cousin(C,D), parent(D,B).



print_solution :-

write('Who are the descendants of Zeus and Hera?'), nl,
setof(X, descendant(X, hera, zeus), Q1), write(Q1), nl, nl,
%
write('Who are Apollo\'s parents?'), nl,
setof(X, parent(X, apollo), Q2), write(Q2), nl, nl,
%
write('Who is Ares\' father?'), nl,
setof(X, father(X, ares), Q3), write(Q3), nl, nl,
%
write('Who are Aphrodite\'s aunts and uncles?'), nl,
setof(X, aunt_or_uncle(X, aphrodite), Q4), write(Q4), nl, nl,
%
write('List all of The Muses\' ancestors.'), nl,
setof(X, ancestor(X, muses), Q5), write(Q5), nl, nl,
%
write('Is Athena a descendant of Poseidon?'), nl,
Q6 = parent(poseidon, athena), write(Q6), nl, nl,
%
write('List all pairs of second-cousins.'), nl,
setof((X,Y), sibling(X, Y), Q7), write(Q7), nl, nl.
%
%write('List all first-cousins once removed.'), nl,

?- print_solution.


/*
child(babyGeorge,william).

child(william,charles).
child(charles,elisabeth).
child(elisabeth,georgeVI).
descend(X,Y) :- child(X,Y).
descend(A,C) :- child(A,B), descend(B,C).

print_solution :-
nl, write('who are descends?'), nl,
bagof(X,descend(X,_), Query1),
write(Query1), nl,
write('Who are descend pairss?'), nl,
setof((C,P),descend(C,P), Query2),
write(Query2), nl.
?- print_solution.
*/
