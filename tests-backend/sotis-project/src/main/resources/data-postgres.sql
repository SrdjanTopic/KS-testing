-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator

insert into role (name) values ('ROLE_TEACHER');
insert into role (name) values ('ROLE_STUDENT');

-- username:teacher password:123
insert into app_user (username, password, first_name, last_name) values ('teacher', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Darko', 'Vrbaski');
insert into app_user_roles (user_id, role_id) values (1, 1);
insert into teacher (id) values (1);

-- username:student password:123
insert into app_user (username, password, first_name, last_name) values ('student', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Keko', 'Kekic');
insert into app_user_roles (user_id, role_id) values (2, 2);
insert into student (id) values (2);

-- CONCEPT INSERT
insert into concept (concept) values ('HTML'); --concept_id:1
insert into concept (concept) values ('CSS'); --concept_id:2
insert into concept (concept) values ('Javascript'); --concept_id:3
insert into concept (concept) values ('Tailwind'); --concept_id:4
insert into concept (concept) values ('React.js'); --concept_id:5
insert into concept (concept) values ('Angular.js'); --concept_id:6
insert into concept (concept) values ('SASS/SCSS'); --concept_id:7
insert into concept (concept) values ('Typescript'); --concept_id:8
-- RELATIONS INSERT
insert into relation (source_id, destination_id) values (1,2);
insert into relation (source_id, destination_id) values (1,3);
insert into relation (source_id, destination_id) values (2,4);
insert into relation (source_id, destination_id) values (2,7);
insert into relation (source_id, destination_id) values (3,5);
insert into relation (source_id, destination_id) values (8,6);
insert into relation (source_id, destination_id) values (3,8);
-- TEST INSERT
insert into test (name) values ('Test for the insane ones'); --test_id:1
-- QUESTION INSERT
insert into question (test_id, concept_id, question) values (1, 1, 'What/who is KEKEKE?'); --question_id:1
insert into question (test_id, concept_id, points, question) values (1, 2, 25, 'Question???'); --question_id:2
insert into question (test_id, concept_id, points, question) values (1, 2, 55, 'Question???'); --question_id:2
-- ANSWER INSERT
insert into answer (question_id, answer) values (1, 'what!?'); --answer_id:1
insert into answer (question_id, answer, is_correct) values (1, 'HE IS GOD', true); --answer_id:2
-- STUDENT ANSWERS
insert into student_answers (students_id, answers_id) values (2, 1);