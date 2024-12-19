import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateGameComponent } from './create-game/create-game.component';
import { PlanningGameComponent } from './planning-game/planning-game.component';

const routes: Routes = [
  { path: '', component: CreateGameComponent }, // Page d'accueil
  { path: 'planning-game/:code', component: PlanningGameComponent }, // Route dynamique
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
