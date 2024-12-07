---------------------------------------------------------
-- Database creation and connection
---------------------------------------------------------

DROP DATABASE IF EXISTS "SSH";
CREATE DATABASE "SSH";
\c SSH;

CREATE TABLE items
(
itemID serial PRIMARY KEY,
itemType int NOT NULL,
name varchar NOT NULL,
measurment int NOT NULL
);

CREATE TABLE recipes
(
recipeID serial PRIMARY KEY,
name varchar NOT NULL,
pictureLink varchar,
instructions text NOT NULL,
timeToMake time Not NULL
);

CREATE TABLE fridge
(
itemID int REFERENCES items(itemID) NOT NULL,
userID int NOT NULL,
quantity float NOT NULL check (quantity > 0)
);

Create TABLE ingredients
(
itemID int REFERENCES items(itemID) NOT NULL,
recipeID int REFERENCES recipes(recipeID) NOT NULL,
quantity float NOT NULL check (quantity > 0)
);

---------------------------------------------------------
-- Database population
---------------------------------------------------------

--itemtype: -1=undefined, 0 = meat, 1=vegetarian, 2=vegan 
--measurment: -1=undefined, 0=grams, 1=kg, 2=ml, 3=l, 4=quantity 

INSERT INTO items (itemType, name, measurment) (
VALUES (1, 'eggs', 4),
(1, 'milk', 3),
(2, 'water' 3),
(2, 'olive oil', 3),
(2, 'sunflower oil', 3),
(2, 'vegetable oil', 3),
(2, 'vinegar', 3),
(2, 'sugar', 0),
(2, 'brown sugar', 0),
(2, 'icing sugar', 0),
(2, 'basil', 0),
(2, 'oregano', 0),
(2, 'salt', 0),
(2, 'pepper', 0),
(2, 'plain flour', 1),
(2, 'potato starch', 1),
(2, 'baking soda', 0),
(2, 'vanilla extract', 0),
(1, 'butter', 0),
(2, 'margarine', 0),
(2, 'peanut butter', 0),
(2, 'potatos', 1),
(2, 'sweet potatos', 1),
(2, 'tomatoes', 4),
(2, 'cucumbers', 4),
(2, 'bell peppers', 4),
(2, 'zucchini', 4),
(2, 'pumpkin', 1),
(2, 'aubergine', 0),
(2, 'lettuce', 4),
(2, 'cabbage', 4),
(2, 'broccoli', 0),
(2, 'carrots', 4),
(2, 'onions', 4),
(2, 'leek', 4),
(2, 'beetroot', 4),
(2, 'celery', 4),
(2, 'cauliflower', 4),
(2, 'parsley', 4),
(2, 'ginger', 0),
(2, 'garlic', 4),
(2, 'radish', 4),
(2, 'peas', 0),
(2, 'beans', 0),
(2, 'corn', 0),
(2, 'mushrooms', 0),
(2, 'apples', 4),
(2, 'pears', 4),
(2, 'oranges', 4),
(2, 'lemons', 4),
(2, 'limes', 4),
(2, 'plums', 4),
(2, 'apricots', 4),
(2, 'peaches', 4),
(2, 'nectarines', 4),
(2, 'pineapples', 4),
(2, 'kiwi', 0),
(2, 'grapes', 0),
(2, 'strawberries', 0),
(2, 'blueberries', 0),
(2, 'blackberries', 0),
(2, 'cherries', 0),
(2, 'watermelon', 1),
(2, 'mangos', 4),
(2, 'bananas', 4),
(2, 'avocado', 4),
(2, 'coconut', 4),
(2, 'pomogrante', 4),
(2, 'dragonfruit', 4)
);
