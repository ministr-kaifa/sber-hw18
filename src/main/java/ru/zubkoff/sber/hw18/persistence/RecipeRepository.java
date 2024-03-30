package ru.zubkoff.sber.hw18.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.zubkoff.sber.hw18.domain.Recipe;

@Repository
public interface RecipeRepository
    extends JpaRepository<Recipe, Long>, EntriesFetchRecipeRepository, MergeRecipeRepository {
  List<Recipe> findByNameContainingIgnoreCase(String name);
}
