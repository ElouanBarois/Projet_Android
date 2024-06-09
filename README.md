# Mode d'emploi de l'application "Flags"



## Précisions préalables quant aux objectifs:

Les fonctionalités demandées sont toutes implémentées avec en plus un onglet permettant de dessiner son propre drapeau.

- Liste défilante de tous les pays indépendants avec nom + drapeau
- Possibilité de rechercher un pays dans la barre de navigation par nom de pays où capitale
- Possibilité de cliquer sur un pays de la liste pour voir ses informations
- Possibilité de "Liker" un pays et de le retrouver ensuite dans l'onglet "Pays Likés"
- Possibilité de "Unliker"
- Onglet pour dessiner son propre drapeau

Cependant dans la liste de pays "Likés" je n'ai pas réussi à récupérer les drapeaux des pays.

## Utilisation de l'application

Quant on lance l'application on arrive (après le temps de chargement qui peut etre très long à cause de l'API) directement sur la liste de tous les pays avec une barre de recherche en bas de l'appareil. Pour voir les détails d'un pays il suffit de cliquer sur celui-ci. Un fois sur la page (Détails pays) on peut soit revenir en arrière avec le bouton "Back" soit Liker le pays avec l'icone de coeur qui se remplit une fois le pays liké. 
Une fois revenus sur la page principale on peut voir 2 icones en haut à droite. 
Le coeur permet de voir tous les pays likés. 
Le pinceau sert à se diriger vers l'outil "Création d'un drapeau", où des outils de dessin permettent à l'utilisateur d'etre créatif. On peut choisir l'épaisseur du trait, la couleur, le bouton ERASE agit comme une gomme et le bouton CLEAR efface tout le drapeau. 

## Problème d'affichage de drapeaux

Lorsque j'affiche la liste de pays likés, le drapeau ne s'affiche pas. Toutes les autres informations sont transmises correctement mais le FlagUrl est null/empty. Je n'ai pas réussi à régler ce problème. 
