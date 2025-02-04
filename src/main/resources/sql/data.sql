-- Chefs
INSERT INTO chef (first_name, last_name, date_of_birth, username, password, role)
VALUES ('Noah', 'Guerin', '2002-09-12', 'noahg', '$2a$10$Ym.DBt/FfM8jW9jBm9rrpeqsEqZXiKi3R5hm626nzvuQnxIJVIIN6',
        0),
       ('Lars', 'Willemsens', '1984-09-12', 'larsw', '$2a$10$baZxBvqmulvtmHOgFsJbceeEL9bUj3jUqAoJqt1WbvXDRnkLZd2Cm',
        0),
       ('Gordon', 'Ramsay', '1966-11-08', 'gordonr', '$2a$10$gcliD3L2KyxBrCSB3Rk7deorG7lTBlJaJ4i3XML8Wmbc4v4owTrEe',
        1),
       ('Jamie', 'Oliver', '1975-05-27', 'jamieo', '$2a$10$zBR9lh77iJXaxFXZ7Vmd8.h7hRG1Tr5XEUhFlrx.YcVWN/zFaeMRm',
        1),
       ('Joan', 'Roca', '1964-02-11', 'joanr', '$2a$10$7fY.LodT6N9QT1zeILJOUO1YhpNlATdvMN2baO8DXI5.6fVYVYfmq',
        1),
       ('David', 'Chang', '1977-08-05', 'davidc', '$2a$10$xWIjXLXcCeiYbvgvO7yHyuvOa2jHro8QZVE/fDnFxvuwdVuQaIa7S',
        1);

-- Menu Items
INSERT INTO menu_item (name, price, course, vegetarian, spice_level)
VALUES ('Ceasar Salad', 3.5, 'MAIN', FALSE, 0),
       ('Grilled Salmon', 5, 'MAIN', FALSE, 2),
       ('Spaghetti Carbonara', 5, 'MAIN', FALSE, 0),
       ('Vanilla Ice Cream', 1.5, 'DESSERT', TRUE, 0);

-- Assigned Chefs
INSERT INTO menu_assignment (menu_item_id, chef_id, assigned_date_time)
VALUES (1, 1, '2024-03-03 12:00'),
       (1, 2, '2024-03-04 12:00'),
       (2, 1, '2024-03-05 12:00'),
       (2, 6, '2024-03-06 12:00'),
       (4, 1, '2024-03-07 12:00'),
       (4, 2, '2024-03-08 12:00'),
       (4, 5, '2024-03-09 12:00');

-- Recipes
INSERT INTO recipe (instructions, cooking_time, difficulty, menu_item_id)
VALUES ('1. Wash and chop lettuce. 2. Mix with Caesar dressing and croutons. 3. Serve chilled.',
        15, 1, 1),
       ('1. Preheat grill. 2. Season salmon with salt and pepper. 3. Grill for 10 minutes, flipping once. 4. Serve hot.',
        20, 2, 2),
       ('1. Cook spaghetti according to package instructions. 2. In a separate pan, cook pancetta until crispy. 3. Mix cooked spaghetti with beaten eggs and grated cheese. 4. Add pancetta and mix well. 5. Serve hot.',
        25, 2, 3),
       ('1. Scoop desired amount of ice cream into bowl. 2. Serve immediately.',
        0, 1, 4);
