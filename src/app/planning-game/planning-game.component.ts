import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GameService } from '../game.service';

@Component({
  selector: 'app-planning-game',
  templateUrl: './planning-game.component.html',
  styleUrls: ['./planning-game.component.css'],
})
export class PlanningGameComponent implements OnInit {
  gameCode: string | null = null;
  maxPlayers: number = 5; // Nombre maximum de joueurs
  cards: string[] = ['1', '2', '3', '5', '8', '13', '21', '?', '☕'];
  selectedCard: string = ''; // Carte sélectionnée par le joueur
  votes: { player: string; card: string }[] = [];
  pseudo: string | null = ''; // Pseudo de l'utilisateur
  playerId: number = 0; // ID de l'utilisateur
  assignments: any[] = []; // Liste des assignations pour la partie
  currentAssignmentIndex: number = 0; // Index de l'assignation actuelle

  constructor(private route: ActivatedRoute, private gameService: GameService) {}

  ngOnInit(): void {
    this.gameCode = this.route.snapshot.paramMap.get('code');
    this.maxPlayers =
      +this.route.snapshot.queryParamMap.get('maxPlayers')! || 5;
    this.pseudo = this.route.snapshot.queryParamMap.get('pseudo') || '';

    console.log('Code de la partie :', this.gameCode);
    console.log('Nombre maximum de joueurs :', this.maxPlayers);
    console.log('Pseudo du joueur :', this.pseudo);

    if (this.pseudo) {
      this.gameService.getUserId(this.pseudo).subscribe(
        (user) => {
          this.playerId = user.id;
          console.log("L'ID de l'utilisateur est :", this.playerId);
          this.loadAssignments();
        },
        (error) => {
          console.error("Erreur lors de la récupération de l'ID utilisateur :", error);
        }
      );
    }
  }

  // Charger les assignations associées à la partie
  loadAssignments() {
    if (this.gameCode) {
      this.gameService.getAssignments(this.gameCode).subscribe(
        (assignments) => {
          this.assignments = assignments;
          console.log('Assignations récupérées :', this.assignments);
        },
        (error) => {
          console.error('Erreur lors de la récupération des assignations :', error);
        }
      );
    }
  }

  // Sélectionner une carte et soumettre le vote pour l'assignation actuelle
  selectCard(card: string) {
    this.selectedCard = card;
    console.log('Carte sélectionnée :', this.selectedCard);

    if (this.currentAssignmentIndex < this.assignments.length) {
      this.submitVote();
    } else {
      alert("Toutes les assignations ont déjà été votées.");
    }
  }

  // Soumettre le vote pour l'assignation actuelle
  submitVote() {
    if (this.selectedCard === '') {
      alert('Veuillez sélectionner une carte pour voter.');
      return;
    }

    const currentAssignment = this.assignments[this.currentAssignmentIndex];
    const voteData = {
      playerId: this.playerId,
      assignmentId: currentAssignment.id,
      value: String(this.selectedCard), // Convertir en string si nécessaire
    };

    this.gameService.vote(voteData).subscribe(
      (response: any) => {
        console.log('Réponse du serveur :', response);

        if (response.message === 'RULE_NOT_RESPECTED') {
          alert( "La règle n'est pas respectée. Tous les joueurs doivent revoter pour cette assignation.");
          this.reloadVotes(); // Recharge les votes pour montrer l'état actuel
        } else if (response.message.startsWith('The rule is respected. Final value:')) {
          // Extraire la valeur finale du message
          const finalValue = parseInt(response.message.split(':')[1].trim(), 10);
          alert(
            'Vote validé avec succès. La règle est respectée ! Résultat : ' +
              finalValue
          );

          // Mettre à jour la difficulté de l'assignation dans le backend
          const updatedAssignment = {
            id: currentAssignment.id,
            libelle: currentAssignment.libelle,
            description: currentAssignment.description,
            difficulty: finalValue,
            gameId: currentAssignment.game.id,
          };

          this.gameService.updateAssignment(updatedAssignment).subscribe(
            (updateResponse) => {
              console.log('Mise à jour de l’assignation réussie :', updateResponse);
              currentAssignment.difficulty = finalValue; // Mettre à jour localement
              this.nextAssignment(); // Passer à l'assignation suivante
            },
            (error) => {
              console.error(
                'Erreur lors de la mise à jour de l’assignation :',
                error
              );
            }
          );
        } else if (response.message === 'Waiting for other players to vote.') {
          alert("Vote soumis ! En attente des autres joueurs...");
        }
      },
      (error) => {
        console.error('Erreur lors de la soumission du vote :', error);
      }
    );
  }

  // Passer à l'assignation suivante
  nextAssignment() {
    if (this.currentAssignmentIndex < this.assignments.length - 1) {
      this.currentAssignmentIndex++;
      this.selectedCard = ''; // Réinitialiser la sélection
      console.log(
        'Passage à l’assignation suivante :',
        this.assignments[this.currentAssignmentIndex]
      );
    } else {
      alert("Vous avez voté pour toutes les assignations !");
    }
  }

  // Recharger les votes pour l'assignation actuelle
  reloadVotes() {
    const currentAssignment = this.assignments[this.currentAssignmentIndex];

    this.gameService.getVotes().subscribe(
      (votes: any[]) => {
        this.votes = votes
          .filter((vote) => vote.assignmentId === currentAssignment.id)
          .map((vote) => ({
            player: vote.player.pseudo,
            card: vote.value,
          }));
        console.log('Votes actuels pour cette assignation :', this.votes);
      },
      (error) => {
        console.error('Erreur lors du rechargement des votes :', error);
      }
    );
  }
}
