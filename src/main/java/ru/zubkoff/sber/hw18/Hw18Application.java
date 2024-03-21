package ru.zubkoff.sber.hw18;

import java.io.Console;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ru.zubkoff.sber.hw18.domain.Recipe;
import ru.zubkoff.sber.hw18.service.RecipeService;

@SpringBootApplication
public class Hw18Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Hw18Application.class, args);
	}

  private static final String ACTION_MENU = """
    1. Поиск блюд по названию
    2. Добавить блюдо
    3. Удалить блюдо    
    """;

  @Autowired
  RecipeService service;

  @Override
  public void run(String... args) throws Exception {
    var console = System.console();
    while (true) {
      switch (console.readLine(ACTION_MENU)) {
        case "1" -> {
          findRecipe(console)
            .ifPresent(System.out::println);
        }
        case "2" -> {
          var name = console.readLine("Введите название блюда: ");
          System.out.println("Вводите в одной строке ингредиент во второй количество, в конце оставьте строку пустой");
          var ingredients = new HashMap<String, String>();
          do {
            var ingredientName = console.readLine();
            if(ingredientName.isBlank()) {
              break;
            }
            var ingredientAmount = console.readLine();
            ingredients.put(ingredientName, ingredientAmount);
          } while (true);
          var newRecipe = new Recipe(name, ingredients);
          service.createRecipe(newRecipe);
          System.out.println("Рецепт добавлен");
        }
        case "3" -> {
          findRecipe(console)
            .map(Recipe::getId)
            .ifPresent(service::deleteById);
        }
        default -> {
          System.out.println("Неверный номер команды");
        }
      }
    }
  }

  private Optional<Recipe> findRecipe(Console console) {
    var recipeNamePart = console.readLine("Введите часть названия блюда: ");
    System.out.println(recipeNamePart);
    var foundRecipes = service.findReducedRecipeByNameLike(recipeNamePart);
    if(foundRecipes.isEmpty()) {
      System.out.println("Рецептов не найдено");
      return Optional.empty();
    }
    IntStream.range(0, foundRecipes.size())
      .mapToObj(i -> i + ". " + foundRecipes.get(i).getName())
      .forEach(System.out::println);
    var selectedRecipeIndex = Integer.parseInt(console.readLine("Выберите номер искомого блюда: "));
    while(selectedRecipeIndex >= foundRecipes.size() || selectedRecipeIndex < 0) {
      System.out.println("Неверный номер блюда");
      selectedRecipeIndex = Integer.parseInt(console.readLine("Выберите номер искомого блюда: "));
    }
    return Optional.of(service.findRecipeByIdWithIngredients(foundRecipes.get(selectedRecipeIndex).getId()));
  }

}
