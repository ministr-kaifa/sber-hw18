package ru.zubkoff.sber.hw18.persistence;

import ru.zubkoff.sber.hw18.domain.Recipe;

public interface MergeRecipeRepository {
  Recipe merge(Recipe recipe);
}
