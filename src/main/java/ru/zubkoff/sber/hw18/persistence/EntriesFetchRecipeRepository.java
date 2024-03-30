package ru.zubkoff.sber.hw18.persistence;

import java.util.List;
import java.util.Optional;

import ru.zubkoff.sber.hw18.domain.Recipe;

public interface EntriesFetchRecipeRepository {
  Optional<Recipe> findWithEntriesFetchedById(Long id);

  List<Recipe> findAllWithEntriesFetched();
}
