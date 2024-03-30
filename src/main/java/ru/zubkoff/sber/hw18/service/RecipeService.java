package ru.zubkoff.sber.hw18.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  public List<Recipe> findByNameContainingIgnoreCase(String recipeNamePart) {
    return recipeRepository.findByNameContainingIgnoreCase(recipeNamePart);
  }

  /**
   * Возвращает рецепт с указанным идентификатором, включая его ингредиенты.
   *
   * @param id Идентификатор рецепта.
   * @return Рецепт с ингредиентами.
   */
  @Transactional(readOnly = true)
  public Optional<Recipe> findWithEntriesFetchedById(Long id) {
    return recipeRepository.findWithEntriesFetchedById(id);
  }

  public void deleteById(Long id) {
    recipeRepository.deleteById(id);
  }

  /**
   * Сохраняет или изменяет существующий рецепт
   *
   * @param recipe рецепт
   */
  @Transactional
  public void mergeRecipe(Recipe recipe) {
    recipeRepository.merge(recipe);
  }
}
