import { Component, OnInit } from '@angular/core';
import { RecipeService } from '../recipe.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pending-recipes',
  templateUrl: './pending-recipes.component.html',
  styleUrls: ['./pending-recipes.component.scss']
})
export class PendingRecipesComponent implements OnInit {

  constructor(private recipeService: RecipeService,
    private router: Router) { }
  recipes: any;
  nurseId: any;
  helper: any;

  ngOnInit() {
    this.nurseId = JSON.parse(localStorage.getItem('user')).id;
    this.getAllPending();
  }
  getAllPending() {
    this.recipeService.getAllPendingRecipes().subscribe(
      (data) => {
        this.recipes = data;
        console.log(this.recipes)
      }
    )
  }
  approveRecipe(id: any) {
    this.recipeService.approveRecipe(id, this.nurseId).subscribe(
      (data) => {
        console.log('overio hehe');
        this.getAllPending();
      }
    )
  }


}
