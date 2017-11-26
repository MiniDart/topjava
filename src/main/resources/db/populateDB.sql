DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (description, user_id, date_time, calories) VALUES
  ('Завтрак', 100000, '2017-08-01 08:10:00', 500),
  ('Обед', 100000, '2017-08-01 12:30:00', 1000),
  ('Ужин', 100000, '2017-08-01 18:20:00', 500),
  ('Завтрак', 100000, '2017-08-02 08:10:00', 500),
  ('Обед', 100000, '2017-08-02 12:30:00', 1000),
  ('Ужин', 100000, '2017-08-02 18:20:00', 510),
  ('Завтрак', 100001, '2017-08-02 08:30:00', 500),
  ('Обед', 100001, '2017-08-02 12:10:00', 1000),
  ('Ужин', 100001, '2017-08-02 18:50:00', 510),
  ('Завтрак', 100001, '2017-08-01 08:00:00', 500),
  ('Обед', 100001, '2017-08-01 12:20:00', 1000),
  ('Ужин', 100001, '2017-08-01 18:10:00', 500);