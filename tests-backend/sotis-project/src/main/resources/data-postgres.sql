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
insert into app_user (username, password, first_name, last_name) values ('student2', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'adafas', 'afsafsasf');
insert into app_user_roles (user_id, role_id) values (3, 2);
insert into student (id) values (3);

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
insert into test (name, teacher_id) values ('Web developer fundamentals',1); --test_id:1
insert into test (name, teacher_id) values ('Some kind of test',1); --test_id:2
insert into test (name, teacher_id) values ('Test test',1); --test_id:3
-- QUESTION INSERT
insert into question (test_id, concept_id, question) values (1, 1, 'Which tag do we use in HTML for inserting a line-break?'); --question_id:1
insert into question (test_id, concept_id, points, question) values (1, 2, 15, 'How can we change the background color of an element in CSS?'); --question_id:2
insert into question (test_id, concept_id, points, question) values (1, 3, 20, ' Which of the following keywords is used to define a variable in Javascript?'); --question_id:3
insert into question (test_id, concept_id, points, question) values (1, 4, 5, 'How can you make text bold in Tailwind CSS?'); --question_id:4
insert into question (test_id, concept_id, points, question) values (1, 5, 15, 'React.js is written in which of the following languages?'); --question_id:5
insert into question (test_id, concept_id, points, question) values (1, 6, 10, 'Which of the following components can be injected as a dependency in AngularJS?'); --question_id:6
insert into question (test_id, concept_id, points, question) values (1, 7, 15, 'Which of the following directive is used to share rules and relationships between selectors in SASS?'); --question_id:7
insert into question (test_id, concept_id, points, question) values (1, 8, 15, 'Which object-oriented terms are supported by TypeScript?'); --question_id:8

insert into question (test_id, concept_id, points, question) values (2, 6, 10, 'Which of the following components can be injected as a dependency in AngularJS?'); --question_id:9
insert into question (test_id, concept_id, points, question) values (2, 7, 15, 'Which of the following directive is used to share rules and relationships between selectors in SASS?'); --question_id:10
insert into question (test_id, concept_id, points, question) values (2, 8, 15, 'Which object-oriented terms are supported by TypeScript?'); --question_id:11

insert into question (test_id, concept_id, question) values (3, 1, 'Which tag do we use in HTML for inserting a line-break?'); --question_id:1
insert into question (test_id, concept_id, points, question) values (3, 3, 20, ' Which of the following keywords is used to define a variable in Javascript?'); --question_id:3
insert into question (test_id, concept_id, points, question) values (3, 8, 15, 'Which object-oriented terms are supported by TypeScript?'); --question_id:8

-- ANSWER INSERT
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
insert into answer (question_id, answer, is_correct) values (9, 'All answers', true); --answer_id:30
insert into answer (question_id, answer) values (9, 'factory'); --answer_id:31
insert into answer (question_id, answer) values (9, 'service'); --answer_id:32
insert into answer (question_id, answer) values (9, 'value'); --answer_id:33
insert into answer (question_id, answer, is_correct) values (10, '@extend', true); --answer_id:34
insert into answer (question_id, answer) values (10, '@media'); --answer_id:35
insert into answer (question_id, answer) values (10, 'None'); --answer_id:36
insert into answer (question_id, answer, is_correct) values (11, 'All answers', true); --answer_id:37
insert into answer (question_id, answer) values (11, 'Interfaces'); --answer_id:38
insert into answer (question_id, answer) values (11, 'Data Types'); --answer_id:39
insert into answer (question_id, answer) values (11, 'Member functions'); --answer_id:40

-- STUDENT ANSWERS
insert into student_answers (students_id, answers_id) values (2, 3);
insert into student_answers (students_id, answers_id) values (2, 5);
insert into student_answers (students_id, answers_id) values (2, 10);
insert into student_answers (students_id, answers_id) values (2, 14);
insert into student_answers (students_id, answers_id) values (2, 16);
insert into student_answers (students_id, answers_id) values (2, 19);
insert into student_answers (students_id, answers_id) values (2, 23);
insert into student_answers (students_id, answers_id) values (2, 28);
insert into student_answers (students_id, answers_id) values (3, 1);
insert into student_answers (students_id, answers_id) values (3, 4);
insert into student_answers (students_id, answers_id) values (3, 7);
insert into student_answers (students_id, answers_id) values (3, 13);
insert into student_answers (students_id, answers_id) values (3, 17);
insert into student_answers (students_id, answers_id) values (3, 2);
insert into student_answers (students_id, answers_id) values (3, 8);
insert into student_answers (students_id, answers_id) values (3, 9);
insert into student_answers (students_id, answers_id) values (3, 11);
insert into student_answers (students_id, answers_id) values (3, 15);