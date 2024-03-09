INSERT INTO application_user(username, password)
VALUES ('noah', '$2a$10$Ym.DBt/FfM8jW9jBm9rrpeqsEqZXiKi3R5hm626nzvuQnxIJVIIN6');
-- password: noah

INSERT INTO restaurant (name, date_established, seating_capacity)
VALUES ('San Remo', '1989-01-14', 20),
       ('Zeus', '2001-04-13', 23);

INSERT INTO chef (first_name, last_name, date_of_birth, restaurant_id)
VALUES ('Tom', 'Hanks', '1981-04-22', 2),
       ('Gordon', 'Ramsey', '1978-09-08', 2),
       ('Mary', 'Poppins', '1971-12-20', 1),
       ('Billie', 'Eilish', '2004-02-11', 1);

INSERT INTO menu_item (name, price, course, vegetarian, spice_lvl, restaurant_id)
VALUES ('Ceasar Salad', 3.5, 'Main', FALSE, 0, 1),
       ('Grilled Salmon', 5, 'Main', FALSE, 2, 1),
       ('Spaghetti Carbonara', 5, 'Main', FALSE, 0, 2),
       ('Vanilla Ice Cream', 1.5, 'Dessert', TRUE, 0, 1);

INSERT INTO menu_item_chef (menu_item_id, chef_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 3),
       (3, 4),
       (4, 1),
       (4, 2),
       (4, 4);
