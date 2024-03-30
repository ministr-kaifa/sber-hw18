package ru.zubkoff.sber.hw18;

import java.io.Console;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ru.zubkoff.sber.hw18.domain.Ingredient;
import ru.zubkoff.sber.hw18.domain.Measurement;
import ru.zubkoff.sber.hw18.domain.Recipe;
import ru.zubkoff.sber.hw18.domain.RecipeEntry;
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
              .ifPresent(recipe -> System.out.println(recipeConsoleFormat(recipe)));
        }
        case "2" -> {
          var name = console.readLine("Введите название блюда: ");
          var recipeEntries = new ArrayList<RecipeEntry>();
          var recipe = new Recipe(name, recipeEntries);
          System.out
              .println(
                  "Вводите в одной строке через запятую: ингредиент, количество, измерение. В конце оставьте строку пустой");
          do {
            var recipeEntryString = console.readLine();
            if (recipeEntryString.isBlank()) {
              break;
            }
            var recipeEntryData = recipeEntryString.split(",\\s");
            var newEntry = new RecipeEntry(recipe, new Ingredient(recipeEntryData[0]),
                new Measurement(recipeEntryData[2]),
                Double.parseDouble(recipeEntryData[1]));
            recipeEntries.add(newEntry);
          } while (true);
          service.mergeRecipe(recipe);
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
    var recipeNamePart = console.readLine("Введите часть названия блюда, или пустую строку для вывода всех блюд: ");
    System.out.println(recipeNamePart);
    var foundRecipes = service.findByNameContainingIgnoreCase(recipeNamePart);
    if (foundRecipes.isEmpty()) {
      System.out.println("Рецептов не найдено");
      return Optional.empty();
    }
    IntStream.range(0, foundRecipes.size())
        .mapToObj(i -> i + ". " + foundRecipes.get(i).getName())
        .forEach(System.out::println);
    var selectedRecipeIndex = Integer.parseInt(console.readLine("Выберите номер искомого блюда: "));
    while (selectedRecipeIndex >= foundRecipes.size() || selectedRecipeIndex < 0) {
      System.out.println("Неверный номер блюда");
      selectedRecipeIndex = Integer.parseInt(console.readLine("Выберите номер искомого блюда: "));
    }
    return service.findWithEntriesFetchedById(foundRecipes.get(selectedRecipeIndex).getId());
  }

  private static String recipeConsoleFormat(Recipe recipe) {
    return recipe.getEntries().stream()
        .map(entry -> String.format("%s - %s %s", entry.getIngredient().getName(), entry.getAmount(),
            entry.getMeasurement().getName()))
        .collect(Collectors.joining("\n", recipe.getName() + "\n", ""));
  }

}
