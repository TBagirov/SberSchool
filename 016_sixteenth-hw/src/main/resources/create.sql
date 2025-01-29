CREATE TABLE recipes
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE ingredients
(
    id        SERIAL PRIMARY KEY,
    recipe_id INT REFERENCES recipes (id) ON DELETE CASCADE,
    name      VARCHAR(255) NOT NULL,
    amount    VARCHAR(100) NOT NULL
);
