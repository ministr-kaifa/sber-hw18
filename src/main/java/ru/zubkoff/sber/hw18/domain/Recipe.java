package ru.zubkoff.sber.hw18.domain;

import java.util.HashMap;
import java.util.Map;


public class Recipe {
  private Long id;
  private String name;
  private Map<String, String> ingredients;

  public Recipe(Long id, String name, Map<String, String> ingredients) {
    this.id = id;
    this.name = name;
    this.ingredients = ingredients;
  }

  public Recipe(Long id, String name) {
    this.id = id;
    this.name = name;
    this.ingredients = new HashMap<>();
  }

  public Recipe(String name, Map<String, String> ingredients) {
    this.name = name;
    this.ingredients = ingredients;
  }
  
  public Recipe() {
    ingredients = new HashMap<>();
  }
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public Map<String, String> getIngredients() {
    return ingredients;
  }
  
  public void setIngredients(Map<String, String> ingredients) {
    this.ingredients = ingredients;
  }

  @Override
  public String toString() {
    return "Recipe [id=" + id + ", name=" + name + ", ingredients=" + ingredients + "]";
  }
  
}
