import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { GameService } from '../game.service';  // Importer le service

@Component({
  selector: 'app-create-game',
  templateUrl: './create-game.component.html',
  styleUrls: ['./create-game.component.css'],
})
export class CreateGameComponent {
  maxPlayers: number = 5;  // Nombre de joueurs max, valeur par défaut
  gameMode: number = 1;  // Valeur initiale pour 'Unanimité' (ruleId = 1)
  gameCode: string = ''; // Code du jeu généré
  pseudo: string = '';  // Pseudo du joueur
  gameCodeToJoin: string = ''; // Code du jeu à rejoindre
  gameCodeJoined: string | null = null;  // Code du jeu rejoint
  isAdmin: boolean = true; // L'utilisateur est-il admin ?
  backlogFile: File | null = null; // Fichier backlog (initialement null)

  constructor(private router: Router, private gameService: GameService) {}

  // Méthode appelée lors de la sélection d'un fichier
  onFileSelected(event: Event): void {
    const target = event.target as HTMLInputElement;
    if (target.files && target.files.length > 0) {
      this.backlogFile = target.files[0]; // Stocker le fichier sélectionné
    }
  }

  // Méthode pour créer un jeu
  createGame() {
    // Vérifier que tous les champs nécessaires sont remplis
    if (!this.maxPlayers || !this.gameMode || !this.pseudo) {
      alert('Veuillez remplir tous les champs.');
      return;
    }
  
    // Données pour créer un jeu
    const gameData = {
      maxPlayers: this.maxPlayers,  // Utilisation de maxPlayers saisi par l'utilisateur
      ruleId: this.gameMode,        // Utilisation de gameMode pour le ruleId
    };
  
    // Appel du service pour créer un jeu
    this.gameService.createGame(gameData).subscribe(
      response => {
        console.log('Jeu créé avec succès', response);
        this.gameCode = response.code;  // Assurez-vous que le backend renvoie bien un `code`
  
        // Charger le backlog après la création du jeu, si un fichier est sélectionné
        if (this.backlogFile) {
          this.uploadBacklog(response.id); // ID du jeu généré
        }

        // Créer les données pour rejoindre le jeu
        const joinData = {
          isAdmin: true,
          pseudo: this.pseudo,  // Le pseudo de l'utilisateur
          code: this.gameCode,  // Le code du jeu généré
        };
  
        // Appeler la fonction joinGame avec les données créées
        this.gameService.joinGame(joinData).subscribe(
          joinResponse => {
            console.log('Réponse après avoir rejoint la partie:', joinResponse);
  
            // Vérifier la structure de la réponse et rediriger
            if (joinResponse.status === 'success') {
              // Si la réponse est réussie, rediriger vers la page du jeu
              this.router.navigate([`/planning-game/${this.gameCode}`], { queryParams: { pseudo: this.pseudo } });
            } else {
              alert('Erreur: ' + joinResponse.message);  // Afficher un message d'erreur si nécessaire
            }
          },
          joinError => {
            console.error('Erreur lors de la tentative de rejoindre la partie:', joinError);
            alert('Une erreur s\'est produite lors de la tentative de rejoindre la partie.');
          }
        );
      },
      error => {
        console.error('Erreur lors de la création du jeu', error);
        alert('Une erreur s\'est produite lors de la création du jeu. Veuillez réessayer.');
      }
    );
  }

  // Méthode pour charger un backlog
  uploadBacklog(gameId: number): void {
    if (this.backlogFile) {
      console.log('Tentative de chargement du backlog pour gameId:', gameId);
      this.gameService.uploadBacklog(gameId, this.backlogFile).subscribe(
        response => {
          console.log('Réponse du backend pour le backlog :', response);
          alert('Backlog chargé avec succès !');
        }, );
    }
  }
  // Méthode pour rejoindre un jeu
  joinGame() {
    const joinData = {
      isAdmin: false,
      pseudo: this.pseudo,  // Le pseudo du joueur
      code: this.gameCodeToJoin  // Le code du jeu
    };
  
    // Appeler le service pour rejoindre la partie
    this.gameService.joinGame(joinData).subscribe(
      response => {
        console.log('Réponse après avoir rejoint la partie:', response);
  
        // Vérifier la structure de la réponse et rediriger
        if (response.status === 'success') {
          // Si la réponse est réussie, rediriger vers la page du jeu
          this.router.navigate([`/planning-game/${response.gameCode}`], { queryParams: { pseudo: this.pseudo } });
        } else {
          alert('Erreur: ' + response.message);  // Afficher un message d'erreur si nécessaire
        }
      },
      error => {
        console.error('Erreur lors de la tentative de rejoindre la partie:', error);
        alert('Une erreur s\'est produite. Vérifiez le code du jeu et réessayez.');
      }
    );
  }
}
