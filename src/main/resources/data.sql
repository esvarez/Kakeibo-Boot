INSERT INTO categories(ID, NAME, CREATED_AT, UPDATED_AT) VALUES (1, 'Java', '2020-04-28', '2020-04-28');
INSERT INTO categories(ID, NAME, CREATED_AT, UPDATED_AT) VALUES (2, 'Tech', '2020-04-28', '2020-04-28');

INSERT INTO posts(ID, CATEGORY_ID, USER_ID, TITLE, CONTENT, URL, CREATED_AT, UPDATED_AT)
VALUES (1, 1, 'user-random','Titulo con espacios', 'Contenido', 'titulo-con-espacios', '2020-04-28', '2020-04-28');
INSERT INTO posts(ID, CATEGORY_ID, USER_ID, TITLE, CONTENT, URL, CREATED_AT, UPDATED_AT)
VALUES (2, 2, 'user-random','Otro titulo', 'Contenido', 'otro-titulo', '2020-04-28', '2020-04-28');
INSERT INTO posts(ID, CATEGORY_ID, USER_ID, TITLE, CONTENT, URL, CREATED_AT, UPDATED_AT)
VALUES (3, 2, 'other-id','nuevo titulo', 'Contenido', 'nuevo-titulo', '2020-04-28', '2020-04-28');
INSERT INTO posts(ID, CATEGORY_ID, USER_ID, TITLE, CONTENT, URL, CREATED_AT, UPDATED_AT)
VALUES (4, 1, 'other-id','nuevo titulo mas', 'Contenido', 'nuevo-titulo-mas', '2020-04-28', '2020-04-28');
INSERT INTO posts(ID, CATEGORY_ID, USER_ID, TITLE, CONTENT, URL, CREATED_AT, UPDATED_AT)
VALUES (5, 1, 'other-id','nuevo titulo mas', 'Contenido', 'nuevo-titulo-mas2', '2020-04-28', '2020-04-28');