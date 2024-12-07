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

--itemtype: -1=undefined, 0=meat, 1=vegetarian, 2=vegan 
--measurment: -1=undefined, 0=grams, 1=kg, 2=ml, 3=l, 4=quantity 

INSERT INTO items (itemType, name, measurment) (
VALUES (1, 'eggs', 4),
(1, 'milk', 3),
(2, 'water', 3),
(2, 'olive oil', 3),
(2, 'sunflower oil', 3),
(2, 'vegetable oil', 3),
(2, 'vinegar', 2),
(2, 'tomato puree', 1),
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
(2, 'potatoes', 1),
(2, 'sweet potatoes', 1),
(2, 'tomatos', 4),
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
(2, 'olives', 0),
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
(2, 'dragonfruit', 4),
(1, 'bread', 4),
(0, 'beef', 1),
(0, 'pork', 1),
(0, 'turkey', 1),
(0, 'chicken', 1),
(0, 'chicken breast', 1),
(0, 'lamb', 1),
(0, 'rabbit', 1),
(0, 'shrimp', 1),
(0, 'fish', 1),
(2, 'thyme', 0),
(1, 'sour cream', 2),
(2, 'mustard', 2),
(2, 'natural yoghurt', 2),
(2, 'mayo', 2),
(2, 'ketchup', 2),
(2, 'vanilla pods', 4),
(2, 'sesame seeds', 0),
(2, 'coriander leaves', 4),
(2, 'vegetable stock cube', 4),
(1, 'cheese', 0),
(1, 'honey', 2),
(2, 'raspberries', 4),
(2, 'cinnamon', 0)
);


--all recipes are sourced from https://www.jamieoliver.com/
INSERT INTO recipes (name, instructions, pictureLink, timeToMake) (
VALUES ('ratatouille', 'Prep your ingredients before you start - peel and cut the onions into wedges, then peel and finely slice the garlic. Trim the aubergines and courgettes, deseed the peppers and chop into 2.5cm chunks. Roughly chop the tomatoes. Pick the basil leaves and set aside, then finely slice the stalks.
Heat 2 tablespoons of oil in a large casserole pan or saucepan over a medium heat, add the chopped aubergines, courgettes and peppers (you may need to do this in batches) and fry for around 5 minutes, or until golden and softened, but not cooked through. Spoon the cooked veg into a large bowl.
To the pan, add the onion, garlic, basil stalks and thyme leaves with another drizzle of oil, if needed. Fry for 10 to 15 minutes, or until softened and golden.
Return the cooked veg to the pan and stir in the fresh and tinned tomatoes, the balsamic and a good pinch of sea salt and black pepper.
Mix well, breaking up the tomatoes with the back of a spoon. Cover the pan and simmer over a low heat for 30 to 35 minutes, or until reduced, sticky and sweet.
Tear in the basil leaves, finely grate in the lemon zest and adjust the seasoning, if needed. Serve with a hunk of bread or steamed rice.', 'https://asset.jamieoliver.com/images/cq7w2e71/production/4cf0726436ec7fb974e0afe1a96e9405c9aa4fa6-973x1300.jpg/4cf0726436ec7fb974e0afe1a96e9405c9aa4fa6-973x1300.jpg', '01:15'),
('crispy chicken', 'Preheat the oven to 200C. Tear the bread into a food processor, peel and add the garlic, then whiz until fine.
Place the chicken breasts between two sheets of greaseproof paper, then use a rolling pin or the base of a heavy pan to bash and flatten them out to around 1cm thick.
Lift up the bashed chicken breasts, pour half the breadcrumbs on to the paper, put the chicken back on top, and scatter over the rest of the crumbs. Roughly pat the breadcrumbs on to the chicken, then re-cover with the paper and bash again to help them stick.
Scrub the potatoes, then slice into wedges (use a crinkle-cut knife, if you have one) and place in a large mixing bowl. Season with a pinch of sea salt and black pepper and drizzle in 1 tablespoon of olive oil, then toss together to coat.
Spread out in a single layer on a large baking tray and bake for 35 minutes, or until golden and cooked through, giving the tray a shake and adding the corn to the oven for the last 10 minutes.
Peel the onion and coarsely grate on a box grater or in a food processor (or slice by hand) with the carrot, apple (discarding the core) and cabbage. Tip into a mixing bowl, mix with the mustard, extra virgin olive oil, vinegar and yoghurt, then season to taste.
When the wedges have 10 minutes to go, place a large non-stick frying pan on a medium heat with 1 tablespoon of olive oil and fry the chicken for 3 minutes on each side, or until golden and cooked through, adding an extra drizzle of oil, if needed.
Remove the chicken to a board, slice 1cm thick, and serve with the wedges, corn and slaw. Delicious with tomato ketchup for dipping.', 'https://asset.jamieoliver.com/images/cq7w2e71/production/e02b7f87e1653a32a1e5ee0a543c39b081dad9b9-958x1279.jpg/e02b7f87e1653a32a1e5ee0a543c39b081dad9b9-958x1279.jpg', '01:00'),
('apple crumble', 'Preheat the oven to 200C/400F/gas 6.
Peel and core the apples, then quarter and chop into 3cm chunks.
Place in a saucepan on a medium heat with 100g of sugar and a few fine gratings of lemon zest.
Pop the lid on and cook for 5 minutes, or until the apples have softened. Remove the heat and leave to cool a little.
Meanwhile, cube the butter and place in a mixing bowl with the flour. Rub together with your fingertips until it resembles breadcrumbs, then scrunch in the remaining sugar to add a little texture.
Transfer the apples to a 25cm x 30cm baking dish and sprinkle over the crumble topping.
Bake in the oven for 25 to 30 minutes, or until golden and bubbling. Delicious served with vanilla custard.', 'https://asset.jamieoliver.com/images/cq7w2e71/production/89864ff5b1fc78b5eafe15eb5cd9a1749606e6c6-958x1280.jpg/89248401', '00:50'),
('avocado ice cream', 'Halve the vanilla pods lengthways, scrape out the seeds, then add to a saucepan with the pods. Add the sugar, and the zest and juice from your lemon and lime.
Bring to the boil, then simmer for a couple of minutes until all the sugar dissolves.
Take off the heat, pour into a bowl and leave to cool. Do not put your finger in for a taste, though, as it could burn you.
Once the syrup is cool, remove the vanilla pods.
Peel, destone and scoop the avocado flesh into a liquidiser with the syrup and milk. Blitz to a smooth, light consistency - like a smoothie.
If you’ve got an ice-cream maker, put your mixture into it and churn till smooth and frozen. If not, pour it into a large baking dish or deep tray and pop it in the freezer, giving it a whisk every half hour or so until frozen and smooth.
You can eat it right away or transfer it to little containers, cover and pop in the freezer for later.', 'https://asset.jamieoliver.com/images/cq7w2e71/production/208c79314837425aad9f0e173b71c470a878c4e7-958x1280.png/46260596', '01:50'),
('beetroot, carrot & orange salad', 'Preheat the oven to 200C/400F/gas 6.
Trim, peel and halve the carrots, then scrub the beets clean, and chop into wedges.
Parboil the carrots in a large pan of boiling salted water for 5 minutes, then transfer them to a colander using a slotted spoon.
Carefully lower in the beets and parboil for 5 minutes, then drain (parboiling separately will stop the carrots from turning purple).
Transfer the carrots and beets to a large roasting tin, drizzle with olive oil and season with sea salt and black pepper. Roast for 30 to 40 minutes, or until sticky and shiny, jiggling the tray occasionally for even cooking.
Meanwhile, finely grate the orange zest. Trim off the skin and pith, then cut the orange into segments.
Toast the sesame seeds in a dry pan on a low heat for a couple of minutes or until golden, tossing regularly. Pick and roughly chop the coriander leaves.
When the time is up, let the roasted veg cool a little, then toss with the orange zest and segments, a good lug of extra virgin olive oil and a little extra seasoning, if needed.
Arrange over a large platter, scatter over the toasted sesame seeds and coriander leaves, then tuck in.', 'https://asset.jamieoliver.com/images/cq7w2e71/production/7825ada591205426ae0ecd034b1ce787d1144e02-958x1280.png/66676214?rect=0,2,958,1277&w=1920&h=2560&fm=webp&q=80&fit=crop&auto=format', '01:15'),
('tomato soup', 'Peel and chop the carrot, onion and garlic, then trim and finely slice the celery. Place a large saucepan on a medium heat, drizzle in 1 tablespoon of olive oil, then scrape in the chopped vegetables.
Cook for 10 minutes with the lid on (leaving a little gap), or until softened, stirring occasionally. Crumble in the stock cube, carefully top up with 500ml of boiling water and stir to dissolve.
Scrunch in the tomatoes (or tip in and break up with a spoon as you go), then turn the heat up to high and bring to the boil. Reduce the heat to low, pop the lid back on and simmer for 10 minutes, or until thickened slightly, stirring occasionally.
Toast one side of the bread slices under the grill until golden, turn over, then coarsely grate over the Cheddar and place back under the grill until oozy and melty.
Remove the pan from the heat, pick in most of the basil leaves and pour in the milk and balsamic, then carefully blitz with a stick blender until smooth (use a tea towel to protect your hands from little splashes).
Taste and season, if needed, then ladle the soup into serving bowls or mugs. Slice the toast into soldiers and serve on the side for dunking.', 'https://asset.jamieoliver.com/images/cq7w2e71/production/aafb59fed716d7bb45ec0077fb3cf94a335beb70-957x1280.jpg/aafb59fed716d7bb45ec0077fb3cf94a335beb70-957x1280.jpg', '00:35'),
('eggy bread sandwich', 'Place the slices of bread onto a board and spread over the peanut butter.
Peel and slice the banana, then arrange over one of the slices, leaving a small gap around the edges.
Squash over the raspberries, then place the remaining slice of bread on top, peanut butter-side down. Push down lightly to secure.
Crack the egg into a shallow bowl, add the cinnamon, then whisk with a fork to combine. Dunk the sandwich in the beaten egg, turning to make sure it is well coated.
Drizzle 1 teaspoon of oil into a medium non-stick frying pan over a medium-low heat. Once hot, carefully add the eggy sandwich and cook for 3 to 5 minutes, or until golden and crisp, turning every minute.
Remove to a board and cut into triangles. Serve with the yoghurt, a drizzle of honey and any seasonal fresh fruit you have in your fruit bowl.', 'https://asset.jamieoliver.com/images/cq7w2e71/production/e2ffb07bbcc946363e6a47714b8669f413dea125-958x1280.png/70064535', '00:10')
);

INSERT INTO ingredients (recipeID, itemID, quantity) (
VALUES (1, 35, 2),
(1, 42, 4),
(1, 30, 2),
(1, 28, 3),
(1, 27, 3),
(1, 25, 6),
(1, 12, 5),
(1, 4, 0.2),
(1, 82, 5),
(1, 8, 0.4),
(1, 7, 80),
(1, 52, 0.5),
(2, 72, 0.2),
(2, 42, 0.5),
(2, 77, 0.24),
(2, 23, 0.4),
(2, 4, 0.1),
(2, 46, 300),
(2, 35, 0.5),
(2, 34, 0.5),
(2, 49, 0.5),
(2, 32, 0.5),
(2, 84, 15),
(2, 7, 30),
(2, 85, 100),
(3, 49, 16),
(3, 9, 150),
(3, 52, 1),
(3, 20, 50),
(3, 16, 0.1),
(4, 88, 2),
(4, 9, 200),
(4, 52, 1),
(4, 53, 1),
(4, 68, 4),
(4, 2, 0.5),
(5, 34, 9),
(5, 37, 5),
(5, 4, 0.3),
(5, 51, 2),
(5, 2, 25),
(5, 90, 3),
(6, 34, 1),
(6, 35, 1),
(6, 42, 1),
(6, 38, 1),
(6, 4, 0.1),
(6, 91, 1),
(6, 8, 1.5),
(6, 72, 0.25),
(6, 92, 85),
(6, 12, 5),
(6, 2, 0.2),
(6, 7, 15),
(7, 72, 0.2),
(7, 22, 15),
(7, 67, 0.5),
(7, 94, 10),
(7, 1, 1),
(7, 95, 5),
(7, 4, 0.1),
(7, 85, 200),
(7, 93, 30),
(7, 61, 50)
);

INSERT INTO fridge (userID, itemID, quantity) (
VALUES (1, 35, 12),
(1, 42, 8),
(1, 15, 3),
(1, 12, 7),
(1, 28, 4),
(1, 30, 2),
(1, 4, 1),
(1, 27, 4),
(1, 7, 200),
(1, 8, 1),
(1, 82, 1),
(1, 25, 6),
(1, 52, 1.2),
(1, 90, 2),
(1, 93, 29),
(1, 46, 10),
(1, 88, 3),
(1, 9, 300),
(1, 53, 2),
(1, 68, 5),
(1, 2, 0.5)
);
