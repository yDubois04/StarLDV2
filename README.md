# StarLDV2

Notre application permet de :

- Choisir une date et une heure en cliquant sur le textView prévu à cet effet
- Choisir une ligne de bus en cliquant sur le premier spinner
- Choisir un terminal correspondant à la ligne de bus précedemment choisie, en cliquant sur le deuxième spinner
- En cliquant sur le bouton, on accède aux arrêts de ce bus dans la direction choisie
- En cliquant sur un arrêt, on accède aux horaires pour lesquels le bus passe à partir de l'horaire choisi
- En cliquant sur un horaire, on accède aux horaires de passages pour les arrêts suivants
- Chercher un arrêt et savoir quelles lignes de bus passent par cet arrêt. Pour sortir de la recherche, il faut cliquer sur la flèche de retour.

Pour les bus qui ne sont pas de nuit, soit pour les bus autres que le N1 et le N2, on obtient les horaires du jour pour une heure choisie supérieure ou égale à 3 heures du matin. Cela est du au fait qu'un bus de jour peut passer jusqu'à 3 heures du matin.

Pour fonctionner cette application nécessite l'installation de l'application StarLD (V1 de l'application)
