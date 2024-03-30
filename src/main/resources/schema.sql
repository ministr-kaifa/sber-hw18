DROP TABLE IF EXISTS recipe CASCADE;
CREATE TABLE recipe (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name TEXT,
    UNIQUE(name)
);

DROP TABLE IF EXISTS ingredient CASCADE;
CREATE TABLE ingredient (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name TEXT,
    UNIQUE(name)
);

DROP TABLE IF EXISTS measurement CASCADE;
CREATE TABLE measurement (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name TEXT,
    UNIQUE(name)
);

DROP TABLE IF EXISTS recipe_entry;
CREATE TABLE recipe_entry (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    recipe_id INT REFERENCES recipe(id),
    ingredient_id INT REFERENCES ingredient(id),
    measurement_id INT REFERENCES measurement(id),
    amount DOUBLE PRECISION,
    CONSTRAINT uk_recipe_ingredient UNIQUE (recipe_id, ingredient_id)
);
