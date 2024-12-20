Projet CAPI - Planning Poker

📝 Introduction
Le projet CAPI est une application de Planning Poker, développée pour simplifier les estimations de complexité des tâches dans un backlog selon les méthodologies agiles. L'outil permet de :

Voter sur la complexité des tâches.
Enregistrer les résultats pour analyse ou reprise.
Faciliter la collaboration entre les membres d'une équipe.

🚀 Technologies Utilisées

Backend
Langage : Java
Framework : Spring Boot
Base de données : PostgreSQL
Frontend
Langage : TypeScript
Framework : Angular

🏗️ Architecture

Le projet suit une architecture client-serveur organisée en trois couches :

Frontend : Gestion de l'interface utilisateur.
Backend : Logique métier et gestion des requêtes.
Base de données : Stockage des entités de l'application.

📂 Fonctionnalités

Backend
API RESTful pour la gestion des parties, des joueurs, des votes et des règles.
Documentation des endpoints et modèles via Doxygen.
Frontend
Interface intuitive pour créer des parties, voter et visualiser les résultats.
Consommation des APIs backend via HttpClient.

🔧 Intégration Continue (CI)

Tests automatisés avec JUnit 5 et Mockito.
Pipelines CI/CD via GitHub Actions pour :
Vérification de la qualité du code.
Déploiement automatisé.

📊 Tests et Qualité

Couverture des tests unitaires sur :
Les services métier.
Les contrôleurs backend.
Les interactions avec la base de données.

🌟 Perspectives
Améliorations futures :
Déploiement sur une plateforme cloud (AWS, Heroku, Netlify).
Optimisation de la gestion en temps réel des joueurs.


🤝 Contributeurs
Projet réalisé en binôme suivant les principes du pair programming

📜 Licence
MIT
