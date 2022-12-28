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
-- username:student2 password:123
insert into app_user (username, password, first_name, last_name) values ('student2', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Srdjan', 'Topic');
insert into app_user_roles (user_id, role_id) values (3, 2);
insert into student (id) values (3);
-- username:student3 password:123
insert into app_user (username, password, first_name, last_name) values ('student3', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Natalija', 'Krsmanovic');
insert into app_user_roles (user_id, role_id) values (4, 2);
insert into student (id) values (4);
-- username:student4 password:123
insert into app_user (username, password, first_name, last_name) values ('student4', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Dusan', 'Lekic');
insert into app_user_roles (user_id, role_id) values (5, 2);
insert into student (id) values (5);
-- username:student5 password:123
insert into app_user (username, password, first_name, last_name) values ('student5', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Nemanja', 'Vajagic');
insert into app_user_roles (user_id, role_id) values (6, 2);
insert into student (id) values (6);
-- username:student6 password:123
insert into app_user (username, password, first_name, last_name) values ('student6', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marko', 'Suljak');
insert into app_user_roles (user_id, role_id) values (7, 2);
insert into student (id) values (7);
-- username:student7 password:123
insert into app_user (username, password, first_name, last_name) values ('student7', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Nikola', 'Milev');
insert into app_user_roles (user_id, role_id) values (8, 2);
insert into student (id) values (8);
-- username:student8 password:123
insert into app_user (username, password, first_name, last_name) values ('student8', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Nemanja', 'Markovic');
insert into app_user_roles (user_id, role_id) values (9, 2);
insert into student (id) values (9);

-- CONCEPT INSERT
insert into concept (concept) values ('HTML'); --concept_id:1
insert into concept (concept) values ('CSS'); --concept_id:2
insert into concept (concept) values ('Javascript'); --concept_id:3
insert into concept (concept) values ('Tailwind'); --concept_id:4
insert into concept (concept) values ('React.js'); --concept_id:5
insert into concept (concept) values ('Angular.js'); --concept_id:6
insert into concept (concept) values ('SASS/SCSS'); --concept_id:7
insert into concept (concept) values ('Typescript'); --concept_id:8

insert into concept (concept) values ('Python'); --concept_id:9
insert into concept (concept) values ('Java'); --concept_id:10
insert into concept (concept) values ('OOP'); --concept_id:11
insert into concept (concept) values ('Databases'); --concept_id:12
insert into concept (concept) values ('API'); --concept_id:13
insert into concept (concept) values ('Functional programming'); --concept_id:14

-- RELATIONS INSERT
INSERT INTO relation (destination_id, source_id) VALUES (2, 1);
INSERT INTO relation (destination_id, source_id) VALUES (3, 1);
INSERT INTO relation (destination_id, source_id) VALUES (4, 2);
INSERT INTO relation (destination_id, source_id) VALUES (7, 2);
INSERT INTO relation (destination_id, source_id) VALUES (5, 3);
INSERT INTO relation (destination_id, source_id) VALUES (6, 8);
INSERT INTO relation (destination_id, source_id) VALUES (8, 3);
INSERT INTO relation (destination_id, source_id) VALUES (9, 11);
INSERT INTO relation (destination_id, source_id) VALUES (10, 11);
INSERT INTO relation (destination_id, source_id) VALUES (10, 13);
INSERT INTO relation (destination_id, source_id) VALUES (3, 11);
INSERT INTO relation (destination_id, source_id) VALUES (10, 12);
INSERT INTO relation (destination_id, source_id) VALUES (9, 14);
INSERT INTO relation (destination_id, source_id) VALUES (10, 14);
INSERT INTO relation (destination_id, source_id) VALUES (9, 13);
INSERT INTO relation (destination_id, source_id) VALUES (3, 13);

-- PROFESSION INSERT
insert into profession(name) values ('Full stack developer'); --profession_id:1
insert into profession(name) values ('Front end developer'); --profession_id:2
insert into profession(name) values ('Back end developer'); --profession_id:3
insert into profession(name) values ('Devops engineer'); --profession_id:4
insert into profession(name) values ('Data engineer'); --profession_id:5
insert into profession(name) values ('Data scientist'); --profession_id:6
insert into profession(name) values ('Embedded systems engineer'); --profession_id:7

-- PROFESSION CONCEPTS INSERT
-- Front end developer
insert into profession_required_concepts(required_for_professions_id, required_concepts_id) values (2, 1);
insert into profession_required_concepts(required_for_professions_id, required_concepts_id) values (2, 2);
insert into profession_required_concepts(required_for_professions_id, required_concepts_id) values (2, 3);
insert into profession_required_concepts(required_for_professions_id, required_concepts_id) values (2, 4);
insert into profession_required_concepts(required_for_professions_id, required_concepts_id) values (2, 5);
insert into profession_required_concepts(required_for_professions_id, required_concepts_id) values (2, 6);
insert into profession_required_concepts(required_for_professions_id, required_concepts_id) values (2, 7);
insert into profession_required_concepts(required_for_professions_id, required_concepts_id) values (2, 8);
-- Back end developer
insert into profession_required_concepts(required_for_professions_id, required_concepts_id) values (3, 3);
insert into profession_required_concepts(required_for_professions_id, required_concepts_id) values (3, 8);
insert into profession_required_concepts(required_for_professions_id, required_concepts_id) values (3, 9);
insert into profession_required_concepts(required_for_professions_id, required_concepts_id) values (3, 10);
insert into profession_required_concepts(required_for_professions_id, required_concepts_id) values (3, 11);
insert into profession_required_concepts(required_for_professions_id, required_concepts_id) values (3, 12);
insert into profession_required_concepts(required_for_professions_id, required_concepts_id) values (3, 13);
insert into profession_required_concepts(required_for_professions_id, required_concepts_id) values (3, 14);

-- STUDENT LEARNED CONCEPTS INSERT
-- student 1
insert into student_learned_concepts(learned_by_students_id, learned_concepts_id) values (2, 1);
insert into student_learned_concepts(learned_by_students_id, learned_concepts_id) values (2, 2);
insert into student_learned_concepts(learned_by_students_id, learned_concepts_id) values (2, 3);
insert into student_learned_concepts(learned_by_students_id, learned_concepts_id) values (2, 8);
-- student 2
insert into student_learned_concepts(learned_by_students_id, learned_concepts_id) values (3, 1);
insert into student_learned_concepts(learned_by_students_id, learned_concepts_id) values (3, 2);
insert into student_learned_concepts(learned_by_students_id, learned_concepts_id) values (3, 10);
insert into student_learned_concepts(learned_by_students_id, learned_concepts_id) values (3, 11);
insert into student_learned_concepts(learned_by_students_id, learned_concepts_id) values (3, 12);

-- TEST INSERT
insert into test (name, teacher_id) values ('Front end developer fundamentals',1); --test_id:1
insert into test (name, teacher_id) values ('Back end developer fundamentals',1); --test_id:2
-- insert into test (name, teacher_id) values ('Full stack developer questions',1); --test_id:3

-- QUESTION INSERT
--test 1
insert into question (test_id, concept_id, question) values (1, 1, 'Which tag do we use in HTML for inserting a line-break?'); --question_id:1
insert into question (test_id, concept_id, points, question) values (1, 2, 15, 'How can we change the background color of an element in CSS?'); --question_id:2
insert into question (test_id, concept_id, points, question) values (1, 3, 20, ' Which of the following keywords is used to define a variable in Javascript?'); --question_id:3
insert into question (test_id, concept_id, points, question) values (1, 4, 5, 'How can you make text bold in Tailwind CSS?'); --question_id:4
insert into question (test_id, concept_id, points, question) values (1, 5, 15, 'React.js is written in which of the following languages?'); --question_id:5
insert into question (test_id, concept_id, points, question) values (1, 6, 10, 'Which of the following components can be injected as a dependency in AngularJS?'); --question_id:6
insert into question (test_id, concept_id, points, question) values (1, 7, 15, 'Which of the following directive is used to share rules and relationships between selectors in SASS?'); --question_id:7
insert into question (test_id, concept_id, points, question) values (1, 8, 15, 'Which object-oriented terms are supported by TypeScript?'); --question_id:8
--test 2
insert into question (test_id, concept_id, points, question) values (2, 9, 10, 'Is Python case sensitive when dealing with identifiers?'); --question_id:9
insert into question (test_id, concept_id, points, question) values (2, 9, 15, 'Is Python code compiled or interpreted?'); --question_id:10
insert into question (test_id, concept_id, points, question) values (2, 11, 15, 'Which of the following is not an OOP concept?'); --question_id:11
insert into question (test_id, concept_id, points, question) values (2, 12, 10, 'Which type of data can be stored in the database?'); --question_id:12
insert into question (test_id, concept_id, points, question) values (2, 10, 15, 'Which one of the following is not a Java feature?'); --question_id:13
insert into question (test_id, concept_id, points, question) values (2, 13, 15, 'What is the HTTP status code 201 indicate?'); --question_id:14
--test 3
-- insert into question (test_id, concept_id, question) values (3, 1, 'Which tag do we use in HTML for inserting a line-break?'); --question_id:18
-- insert into question (test_id, concept_id, points, question) values (3, 3, 20, ' Which of the following keywords is used to define a variable in Javascript?'); --question_id:19
-- insert into question (test_id, concept_id, points, question) values (3, 8, 15, 'Which object-oriented terms are supported by TypeScript?'); --question_id:20

-- ANSWER INSERT
--test 1
insert into answer (question_id, answer) values (1, '<a>'); --answer_id:1
insert into answer (question_id, answer) values (1, '<p>'); --answer_id:2
insert into answer (question_id, answer) values (1, '<b>'); --answer_id:3
insert into answer (question_id, answer, is_correct) values (1, '<br>', true); --answer_id:4
insert into answer (question_id, answer, is_correct) values (2, 'background-color', true); --answer_id:5
insert into answer (question_id, answer) values (2, 'color'); --answer_id:6
insert into answer (question_id, answer) values (2, 'both'); --answer_id:7
insert into answer (question_id, answer) values (2, 'none'); --answer_id:8
insert into answer (question_id, answer, is_correct) values (3, 'both', true); --answer_id:9
insert into answer (question_id, answer) values (3, 'var'); --answer_id:10
insert into answer (question_id, answer) values (3, 'let'); --answer_id:11
insert into answer (question_id, answer) values (3, 'none'); --answer_id:12
insert into answer (question_id, answer, is_correct) values (4, 'add to class "font-bold"', true); --answer_id:13
insert into answer (question_id, answer) values (4, 'add to class "bold"'); --answer_id:14
insert into answer (question_id, answer, is_correct) values (5, 'Javascript', true); --answer_id:15
insert into answer (question_id, answer) values (5, 'C#'); --answer_id:16
insert into answer (question_id, answer) values (5, 'Java'); --answer_id:17
insert into answer (question_id, answer) values (5, 'Python'); --answer_id:18
insert into answer (question_id, answer, is_correct) values (6, 'All answers', true); --answer_id:19
insert into answer (question_id, answer) values (6, 'factory'); --answer_id:20
insert into answer (question_id, answer) values (6, 'service'); --answer_id:21
insert into answer (question_id, answer) values (6, 'value'); --answer_id:22
insert into answer (question_id, answer, is_correct) values (7, '@extend', true); --answer_id:23
insert into answer (question_id, answer) values (7, '@media'); --answer_id:24
insert into answer (question_id, answer) values (7, 'None'); --answer_id:25
insert into answer (question_id, answer, is_correct) values (8, 'All answers', true); --answer_id:26
insert into answer (question_id, answer) values (8, 'Interfaces'); --answer_id:27
insert into answer (question_id, answer) values (8, 'Data Types'); --answer_id:28
insert into answer (question_id, answer) values (8, 'Member functions'); --answer_id:29
--test2
insert into answer (question_id, answer, is_correct) values (9, 'yes', true); --answer_id:30
insert into answer (question_id, answer) values (9, 'no'); --answer_id:31
insert into answer (question_id, answer) values (9, 'machine dependent'); --answer_id:32
insert into answer (question_id, answer) values (9, 'none of the mentioned'); --answer_id:33
insert into answer (question_id, answer, is_correct) values (10, 'Python code is both compiled and interpreted', true); --answer_id:34
insert into answer (question_id, answer) values (10, 'Python code is neither compiled nor interpreted'); --answer_id:35
insert into answer (question_id, answer) values (10, 'Python code is only compiled'); --answer_id:36
insert into answer (question_id, answer) values (10, 'Python code is only interpreted'); --answer_id:37
insert into answer (question_id, answer, is_correct) values (11, 'Exception', true); --answer_id:38
insert into answer (question_id, answer) values (11, 'Encapsulation'); --answer_id:38
insert into answer (question_id, answer) values (11, 'Polymorphism'); --answer_id:39
insert into answer (question_id, answer) values (11, 'Abstraction'); --answer_id:40
insert into answer (question_id, answer, is_correct) values (12, 'All mentioned answers', true); --answer_id:41
insert into answer (question_id, answer) values (12, 'Image oriented data'); --answer_id:42
insert into answer (question_id, answer) values (12, 'Text, files containing data'); --answer_id:43
insert into answer (question_id, answer) values (12, 'Data in the form of audio or video'); --answer_id:44
insert into answer (question_id, answer, is_correct) values (13, 'Use of pointers', true); --answer_id:41
insert into answer (question_id, answer) values (13, 'Object-oriented'); --answer_id:42
insert into answer (question_id, answer) values (13, 'Portable'); --answer_id:43
insert into answer (question_id, answer) values (13, 'Dynamic and Extensible'); --answer_id:44
insert into answer (question_id, answer, is_correct) values (14, 'Created', true); --answer_id:41
insert into answer (question_id, answer) values (14, 'OK'); --answer_id:42
insert into answer (question_id, answer) values (14, 'Accepted'); --answer_id:43
insert into answer (question_id, answer) values (14, 'No Content'); --answer_id:44

-- STUDENT ANSWERS
INSERT INTO student_answers (students_id, answers_id) VALUES (2, 3);
INSERT INTO student_answers (students_id, answers_id) VALUES (2, 5);
INSERT INTO student_answers (students_id, answers_id) VALUES (2, 10);
INSERT INTO student_answers (students_id, answers_id) VALUES (2, 14);
INSERT INTO student_answers (students_id, answers_id) VALUES (2, 16);
INSERT INTO student_answers (students_id, answers_id) VALUES (2, 19);
INSERT INTO student_answers (students_id, answers_id) VALUES (2, 23);
INSERT INTO student_answers (students_id, answers_id) VALUES (2, 28);
INSERT INTO student_answers (students_id, answers_id) VALUES (3, 1);
INSERT INTO student_answers (students_id, answers_id) VALUES (3, 4);
INSERT INTO student_answers (students_id, answers_id) VALUES (3, 7);
INSERT INTO student_answers (students_id, answers_id) VALUES (3, 13);
INSERT INTO student_answers (students_id, answers_id) VALUES (3, 17);
INSERT INTO student_answers (students_id, answers_id) VALUES (3, 2);
INSERT INTO student_answers (students_id, answers_id) VALUES (3, 8);
INSERT INTO student_answers (students_id, answers_id) VALUES (3, 9);
INSERT INTO student_answers (students_id, answers_id) VALUES (3, 11);
INSERT INTO student_answers (students_id, answers_id) VALUES (3, 15);
INSERT INTO student_answers (students_id, answers_id) VALUES (4, 52);
INSERT INTO student_answers (students_id, answers_id) VALUES (4, 31);
INSERT INTO student_answers (students_id, answers_id) VALUES (4, 45);
INSERT INTO student_answers (students_id, answers_id) VALUES (4, 41);
INSERT INTO student_answers (students_id, answers_id) VALUES (4, 36);
INSERT INTO student_answers (students_id, answers_id) VALUES (4, 47);
INSERT INTO student_answers (students_id, answers_id) VALUES (5, 12);
INSERT INTO student_answers (students_id, answers_id) VALUES (5, 28);
INSERT INTO student_answers (students_id, answers_id) VALUES (5, 5);
INSERT INTO student_answers (students_id, answers_id) VALUES (5, 19);
INSERT INTO student_answers (students_id, answers_id) VALUES (5, 4);
INSERT INTO student_answers (students_id, answers_id) VALUES (5, 13);
INSERT INTO student_answers (students_id, answers_id) VALUES (5, 23);