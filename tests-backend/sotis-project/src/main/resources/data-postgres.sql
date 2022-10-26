-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator

insert into role (name) values ('ROLE_TEACHER');
insert into role (name) values ('ROLE_STUDENT');

-- username:admin password:123
-- insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date, deleted) values ('admin', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Darko', 'Vrbaski', 'semestar2021+1@gmail.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00', false);
-- insert into app_user_roles (user_id, role_id) values (1, 1);
-- insert into admin (id, first_time_logged_in) values (1, false);

