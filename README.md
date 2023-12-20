# Pokemon Java

Ce projet resulte d'un TP fait dans le cadre du cours de Concepts de programmation oriente objet lors de la Licence 3 Informatique a l'Universite de Corse. C'est est un jeu qui reprend les grand principe du celebre jeu Pokemon.

## Installation

Pour lancer le jeu, suivez les etapes suivantes:

1. Clonez le depot git sur votre machine : `git clone git@github.com:antocreadev/pokemon_java.git`
2. Assurez vous d'avoir Java 8 ou superieur d'installe sur votre machine

## Utilisation

1. Compiler le projet : `make build`
2. Lancer le jeu : `make run`
3. Pour lancer le serveur : `java server.Server`
4. Pour supprimer les fichiers generes par la compilation : `make clean`

## Structure de Fichiers

- `assets/` : Contient les assets du jeu (images, sons, etc.)
- `cli/` : Contient les classes du jeu en mode console
- `lib/` : Contient les classes d'utilitaires
- `saves/` : Contient les fichiers de sauvegarde du jeu (GUI)
- `saves_cli/` : Contient les fichiers de sauvegarde du jeu (CLI)
- `server/` : Contient les classes du serveur
- `*.java` : Les classes pour lancer le jeu
- `Makefile` : Le fichier pour compiler, lancer et nettoyer le projet
- `tp2_pokemon.pdf` : Le sujet du TP

## Exemple de gameplay
[![IMAGE ALT TEXT HERE](https://gelk.fr/static/tp_pokemon/img-yb.png)](https://youtu.be/uJnxcFCI8nQ?si=z6xV97J4tOltYWOz)

## UML

![UML](https://gelk.fr/static/tp_pokemon/uml-cli.png)

## Auteurs

- Anthony Menghi : [antocreadev](https://github.com/antocreadev)
- Mehdi Ghoulam : [gmed2b](https://github.com/gmed2b)
