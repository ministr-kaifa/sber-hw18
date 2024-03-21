package ru.zubkoff.sber.hw18.persistence;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.zubkoff.sber.hw18.domain.Recipe;

@Repository
public class PostgresJdbcRecipeRepository implements RecipeRepository {

  private static final String FIND_BY_NAME_QUERY = """
    SELECT id, name
    FROM recipe
    WHERE name ILIKE :nameRegex
    """;

  private static final String FIND_BY_ID_WITH_INGREDIENTS_FETCHED_QUERY = """
    SELECT name, ingredients
    FROM recipe
    WHERE id = :id
    """;

  private static final String DELETE_BY_ID_QUERY = """
    DELETE FROM recipe
    WHERE id = :id
    """;

  private static final String PERSIST_RECIPE_QUERY = """
    INSERT INTO recipe
      (name, ingredients)
    VALUES
      (:name, (to_json(:ingredients::json)))
    """;

  private final NamedParameterJdbcOperations jdbc;

  public PostgresJdbcRecipeRepository(NamedParameterJdbcOperations jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public List<Recipe> findReducedRecipeByNameLike(String name) {
    return jdbc.query(FIND_BY_NAME_QUERY, Map.of("nameRegex", "%" + name + "%"), 
      (rs, rowNum) -> new Recipe(
        rs.getLong("id"),
        rs.getString("name"))
    );
  }

  @Override
  public Recipe findRecipeByIdWithIngredients(long id) {
    return jdbc.queryForObject(FIND_BY_ID_WITH_INGREDIENTS_FETCHED_QUERY, Map.of("id", id),
      (rs, rowNum) -> new Recipe(
        id,
        rs.getString("name"),
        getJson(rs, "ingredients"))
    );
  }

  @Override
  public void persistRecipe(Recipe recipe) {
    jdbc.update(PERSIST_RECIPE_QUERY, Map.of("name", recipe.getName(), "ingredients", toJsonString(recipe.getIngredients())));
  }

  @Override
  public void deleteById(long id) {
    jdbc.update(DELETE_BY_ID_QUERY, Map.of("id", id));
  }

  private static Map<String, String> getJson(ResultSet rs, String columnName) throws SQLException {
    try {
      var objectMapper = new ObjectMapper();
      return objectMapper.readValue(rs.getString(columnName), new TypeReference<Map<String, String>>() {});
    } catch (IOException e) {
      throw new RuntimeException("Unable to deserialize json column " + columnName, e);
    }
  }

  private static String toJsonString(Map<String, String> ingredients) {
    try {
      var objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(ingredients);
    } catch (IOException e) {
      throw new RuntimeException("Unable to serialize json", e);
    }
  }
}
