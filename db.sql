DROP DATABASE IF EXISTS "SSH";
CREATE DATABASE "SSH";
\c SSH;

CREATE TABLE items
(
itemID serial PRIMARY KEY,
itemType int NOT NULL,
name varchar NOT NULL,
measurment int NOT NULL
)

CREATE TABLE recipes
(
recipeID serial PRIMARY KEY,
name varchar NOT NULL,
pictureLink varchar,
instructions text NOT NULL,
timeToMake time Not NULL
)

CREATE TABLE fridge
(
itemID int REFERENCES items(itemID) NOT NULL,
userID int NOT NULL,
quantity float NOT NULL
)

Create TABLE ingredients
(
itemID int REFERENCES items(itemID) NOT NULL,
recipeID int REFERENCES recipes(recipeID) NOT NULL,
quantity float NOT NULL
)