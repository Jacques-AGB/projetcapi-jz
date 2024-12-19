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
  maxPlayers: number = 5; // Nombre maximum de joueurs (par défaut)
  cards: string[] = ['1', '2', '3', '5', '8', '13', '21', '?', '☕'];

  selectedCard:string = '';  // Carte sélectionnée par le joueur
  votes: { player: string; card: string }[] = [];
  pseudo: string | null = '';  // Pseudo de l'utilisateur
  playerId: number=1; // ID de l'utilisateur

  constructor(private route: ActivatedRoute, private gameService: GameService) {}

  ngOnInit(): void {
    this.gameCode = this.route.snapshot.paramMap.get('code');
    this.maxPlayers = +this.route.snapshot.queryParamMap.get('maxPlayers')! || 5;
    this.pseudo = this.route.snapshot.queryParamMap.get('pseudo') || '';

    console.log('Code de la partie :', this.gameCode);
    console.log('Nombre maximum de joueurs :', this.maxPlayers);
    console.log('Pseudo du joueur:', this.pseudo); // Afficher le pseudo du joueur

    // Récupérer l'ID de l'utilisateur via le pseudo
    if (this.pseudo) {
      this.gameService.getUserId(this.pseudo).subscribe(
        (user) => {
          this.playerId = user.id;  // Récupérer l'ID de l'utilisateur
          console.log('L\'ID de l\'utilisateur est :', this.playerId);
        },
        (error) => {
          console.error('Erreur lors de la récupération de l\'ID utilisateur :', error);
        }
      );
    }
  }

 // Sélectionner une carte et soumettre le vote automatiquement
 selectCard(card:string) {
  this.selectedCard = card;
  console.log('Carte sélectionnée:', this.selectedCard);

  // Soumettre automatiquement le vote dès qu'une carte est sélectionnée
  this.submitVote();
}

submitVote() {
  if (this.selectedCard === '') {
    alert('Veuillez sélectionner une carte pour voter.');
    return;
  }

  const voteData = {
    playerId: this.playerId,
    assignmentId: 2,
    value: String(this.selectedCard), // Convertir en string si nécessaire
  };



  this.gameService.vote(voteData).subscribe(
    response => {
        console.log('Réponse du serveur :', response);
        // Assurez-vous que la réponse contient le message attendu
        this.loadVotes()
    },
    error => {
        console.error('Erreur lors de la soumission du vote:', error);
        
    }
);

}
loadVotes() {
  if (this.gameCode) {
    this.gameService.getVotes().subscribe(
      (votes: any) => {
        // Vous devez vous assurer de récupérer 'card' au lieu de 'value'
        this.votes = votes.map((vote: any) => ({
          player: vote.player.pseudo, // pseudo du joueur
          card: vote.value            // Assurez-vous que la bonne propriété est utilisée
        }));
        console.log('Votes récupérés :', this.votes);
      },
      (error) => {
        console.error('Erreur lors de la récupération des votes:', error);
      }
    );
  }
}
}
