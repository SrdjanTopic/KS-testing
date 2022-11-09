-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator

insert into role (name) values ('ROLE_TEACHER');
insert into role (name) values ('ROLE_STUDENT');

-- username:admin password:123
insert into app_user (username, password, first_name, last_name) values ('teacher', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Darko', 'Vrbaski');
insert into app_user_roles (user_id, role_id) values (1, 1);
-- insert into admin (id, first_time_logged_in) values (1, false);

-- TEST INSERT
insert into test (name) values ('Test for the insane ones'); --test_id:1
-- QUESTION INSERT
insert into question (test_id, question) values (1, 'What/who is KEKEKE?'); --question_id:1
insert into question (test_id, points, question) values (1, 25, 'Question???'); --question_id:2
-- ANSWER INSERT
insert into answer (question_id, answer) values (1, 'what!?'); --answer_id:1
insert into answer (question_id, answer, is_correct) values (1, 'HE IS GOD', true); --answer_id:2