import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-game',
  templateUrl: './create-game.component.html',
  styleUrls: ['./create-game.component.css'],
})
export class CreateGameComponent {
  maxPlayers: number = 5;
  gameMode: string = 'unanimity';
  gameCode: string | null = null;

  constructor(private router: Router) {} // Injection correcte

  createGame() {
    this.gameCode = Math.random().toString(36).substring(2, 8).toUpperCase();
    console.log('Redirection vers :', `/planning-game/${this.gameCode}`);
    this.router.navigate([`/planning-game/${this.gameCode}`]); // Redirection
  }
  joinGame(): void {
    if (this.gameCode) {
      console.log('Rejoindre le jeu avec le code :', this.gameCode);
      this.router.navigate([`/planning-game/${this.gameCode}`]);
    }
  }
}
