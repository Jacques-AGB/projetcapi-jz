import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-planning-game',
  templateUrl: './planning-game.component.html',
  styleUrls: ['./planning-game.component.css'],
})
export class PlanningGameComponent implements OnInit {
  gameCode: string | null = null;
  maxPlayers: number = 5; // Nombre maximum de joueurs (par défaut)
  cards: string[] = ['1', '2', '3', '5', '8', '13', '21', '?', '☕'];
  votes: { player: string; card: string }[] = [];

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.gameCode = this.route.snapshot.paramMap.get('gameCode');
    this.maxPlayers = +this.route.snapshot.queryParamMap.get('maxPlayers')! || 5;
    console.log('Code de la partie :', this.gameCode);
    console.log('Nombre maximum de joueurs :', this.maxPlayers);
  }

  vote(card: string): void {
    if (this.votes.length >= this.maxPlayers) {
      console.warn('Le nombre maximum de joueurs est atteint !');
      return;
    }

    const playerName = `Joueur ${this.votes.length + 1}`;
    this.votes.push({ player: playerName, card });
    console.log(`${playerName} a voté : ${card}`);
  }
}
