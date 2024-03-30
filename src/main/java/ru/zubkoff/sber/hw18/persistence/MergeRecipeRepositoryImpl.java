package ru.zubkoff.sber.hw18.persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.zubkoff.sber.hw18.domain.Ingredient;
import ru.zubkoff.sber.hw18.domain.Measurement;
import ru.zubkoff.sber.hw18.domain.Recipe;

public class MergeRecipeRepositoryImpl implements MergeRecipeRepository {

  @PersistenceContext
  EntityManager entityManager;

  @Override
  public Recipe merge(Recipe recipe) {
    recipe.getEntries().forEach(entry -> {
      var existingIngredients = entityManager
          .createQuery("select i from Ingredient i where i.name = :name", Ingredient.class)
          .setParameter("name", entry.getIngredient().getName())
          .getResultList();
      if (!existingIngredients.isEmpty()) {
        entry.setIngredient(existingIngredients.get(0));
      }

      var existingMeasurements = entityManager
          .createQuery("select m from Measurement m where m.name = :name", Measurement.class)
          .setParameter("name", entry.getMeasurement().getName())
          .getResultList();
      if (!existingMeasurements.isEmpty()) {
        entry.setMeasurement(existingMeasurements.get(0));
      }
    });

    var existingRecipes = entityManager
        .createQuery("select r from Recipe r where r.name = :name", Recipe.class)
        .setParameter("name", recipe.getName())
        .getResultList();
    if (!existingRecipes.isEmpty()) {
      var existingRecipe = existingRecipes.get(0);
      entityManager.createQuery("delete from RecipeEntry e where e.recipe = :recipe")
          .setParameter("recipe", existingRecipe)
          .executeUpdate();
      recipe.setId(existingRecipe.getId());
    }
    return entityManager.merge(recipe);
  }

}
