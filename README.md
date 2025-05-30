# OwlLib

OwlLib est un plugin bibliothèque pour Minecraft fournissant des composants réutilisables et une architecture solide pour le développement d'autres plugins.

## Caractéristiques

- Architecture modulaire suivant les principes SOLID
- Patterns de conception courants: Registry, Observer, Service Locator, etc.
- Gestion des événements asynchrones
- Gestion de configuration extensible
- Framework de composants et services

## Installation

1. Téléchargez le fichier OwlLib.jar depuis les releases
2. Placez-le dans le dossier `plugins/` de votre serveur Minecraft
3. Redémarrez votre serveur

## Utilisation pour les développeurs

Pour utiliser OwlLib dans votre plugin, ajoutez-le en tant que dépendance dans votre fichier `plugin.yml`:

```yaml
depend: [OwlLib]
