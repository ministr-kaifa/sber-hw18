package ru.zubkoff.sber.hw18.persistence;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import ru.zubkoff.sber.hw18.domain.Recipe;

@Repository
public class EntriesFetchRecipeRepositoryImpl implements EntriesFetchRecipeRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Optional<Recipe> findWithEntriesFetchedById(Long id) {
    var entityGraph = entityManager.getEntityGraph(Recipe.RECIPE_ENTRIES_FETCHED_GRAPH);
    return Optional
        .ofNullable(entityManager.find(Recipe.class, id, Map.of("jakarta.persistence.fetchgraph", entityGraph)));
  }

  @Override
  public List<Recipe> findAllWithEntriesFetched() {
    var entityGraph = entityManager.getEntityGraph(Recipe.RECIPE_ENTRIES_FETCHED_GRAPH);
    return entityManager.createQuery("SELECT r FROM Recipe r", Recipe.class)
        .setHint("jakarta.persistence.fetchgraph", entityGraph)
        .getResultList();
  }

}
