package ru.zubkoff.sber.hw18.persistence;

import java.util.List;

import ru.zubkoff.sber.hw18.domain.Recipe;

public interface RecipeRepository {

  List<Recipe> findReducedRecipeByNameLike(String name);

  Recipe findRecipeByIdWithIngredients(long id);

  void persistRecipe(Recipe recipe);

  void deleteById(long id);

}