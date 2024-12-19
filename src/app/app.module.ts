import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'; // Import pour [(ngModel)]
import { HttpClientModule } from '@angular/common/http';  // Ajoutez cet import
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreateGameComponent } from './create-game/create-game.component';
import { PlanningGameComponent } from './planning-game/planning-game.component';

@NgModule({
  declarations: [
    AppComponent,
    CreateGameComponent,
    PlanningGameComponent // Déclarer le composant ici
  ],
  imports: [
    BrowserModule,
    FormsModule, // Ajouter FormsModule pour les formulaires
    HttpClientModule,  // Ajoutez HttpClientModule ici
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
