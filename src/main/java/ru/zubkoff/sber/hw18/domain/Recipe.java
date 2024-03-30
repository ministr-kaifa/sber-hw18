package ru.zubkoff.sber.hw18.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;

@Entity
@NamedEntityGraph(name = Recipe.RECIPE_ENTRIES_FETCHED_GRAPH, attributeNodes = {
    @NamedAttributeNode(value = "entries", subgraph = "entries-subgraph")
}, subgraphs = {
    @NamedSubgraph(name = "entries-subgraph", attributeNodes = {
        @NamedAttributeNode("ingredient"),
        @NamedAttributeNode("measurement")
    })
})
public class Recipe {

  public static final String RECIPE_ENTRIES_FETCHED_GRAPH = "recipe-entries-fetched-graph";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String name;

  @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
  private List<RecipeEntry> entries;

  public Recipe() {
  }

  public Recipe(String name) {
    this.name = name;
  }

  public Recipe(String name, List<RecipeEntry> entries) {
    this.name = name;
    this.entries = entries;
  }

  public Recipe(Long id, String name, List<RecipeEntry> entries) {
    this.id = id;
    this.name = name;
    this.entries = entries;
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

  public List<RecipeEntry> getEntries() {
    return entries;
  }

  public void setEntries(List<RecipeEntry> entries) {
    this.entries = entries;
  }
}
