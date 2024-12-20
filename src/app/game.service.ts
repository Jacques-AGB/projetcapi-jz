import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class GameService {
  
  private apiUrl = 'http://localhost:8082/api/players/join'; // Back-end UR
  private voteUrl = 'http://localhost:8082/api';

  constructor(private http: HttpClient) {}

  // Méthode pour créer un jeu
  createGame(gameData: any): Observable<any> {
    return this.http.post<any>(`http://localhost:8082/api/games/addEdit`, gameData); // Appel à l'endpoint backend
  }

  joinGame(joinData: { pseudo: string, code: string ,isAdmin:boolean}): Observable<any> {
    return this.http.post<any>(this.apiUrl, joinData);  // La réponse est attendue en JSON
  }

  vote(voteData: { playerId: number | null ; assignmentId: number; value: string }) {
    console.log('Données envoyées :', voteData); // Vérifier les données envoyées
    return this.http.post('http://localhost:8082/api/votes/submit', voteData);
  }

  updateAssignment(assignmentData: { id: number; libelle: string; description: string; difficulty: number; gameId: number }): Observable<any> {
    return this.http.post(`http://localhost:8082/api/assignments/addEdit`, assignmentData);
  }

   // Méthode pour récupérer les votes
   getVotes(): Observable<any> {
    return this.http.get('http://localhost:8082/api/votes'); // Modifiez selon votre endpoint API
  }
  
  getAssignments(gameCode: string): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8082/api/assignments/game/${gameCode}`);
  }
  
  // Méthode pour récupérer l'ID de l'utilisateur par son pseudo
  getUserId(pseudo: string): Observable<any> {
    return this.http.get(`http://localhost:8082/api/players/${pseudo}`);
  }

   // **Nouvelle méthode pour charger un backlog**
   uploadBacklog(gameId: number, file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`http://localhost:8082/api/assignments/backlog/upload/${gameId}`, formData);
  }
}