package ru.zubkoff.sber.hw18.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ru.zubkoff.sber.hw18.domain.Recipe;
import ru.zubkoff.sber.hw18.persistence.RecipeRepository;

@Service
public class RecipeService {
  private final RecipeRepository recipeRepository;

  public RecipeService(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  /**
   * Возвращает список рецептов с указанным именем, не содержащих ингредиенты.
   *
   * @param name Имя рецепта для поиска.
   * @return Список рецептов без ингредиентов с указанным именем.
   */
  public List<Recipe> findReducedRecipeByNameLike(String name) {
    return recipeRepository.findReducedRecipeByNameLike(name);
  }

  /**
   * Возвращает рецепт с указанным идентификатором, включая его ингредиенты.
   *
   * @param id Идентификатор рецепта.
   * @return Рецепт с ингредиентами.
   */
  public Recipe findRecipeByIdWithIngredients(long id) {
    return recipeRepository.findRecipeByIdWithIngredients(id);
  }

  /**
   * Создает новый рецепт.
   *
   * @param recipe Новый рецепт для создания.
   */
  public void createRecipe(Recipe recipe) {
    recipeRepository.persistRecipe(recipe);
  }

  /**
   * Удаляет рецепт с указанным идентификатором.
   *
   * @param id Идентификатор рецепта для удаления.
   */
  public void deleteById(long id) {
    recipeRepository.deleteById(id);
  }

}
