INSERT INTO anime(name, description, type, year, image) VALUES
	('Anime I','Anime I description','TV',2021,'anime1.jpg'),
	('Anime II','Anime II description','TV',2021,'anime2.jpg'),
	('Anime III','Anime III description','TV',2021,'anime3.jpg'),
	('Anime IV','Anime IV description','TV',2021,'anime4.jpg');

INSERT INTO author(name, imageurl) VALUES
    ('Author I', 'author1.jpg'),
    ('Author II', 'author2.jpg');

INSERT INTO genre(label, image) VALUES
    ('Genre I', 'genre1.jpg'),
    ('Genre II', 'genre2.jpg');

INSERT INTO anime_author(animeid, authorid) VALUES
    ((SELECT animeid FROM anime WHERE name='Anime I'), (SELECT authorid FROM author WHERE name='Author I')),
    ((SELECT animeid FROM anime WHERE name='Anime II'), (SELECT authorid FROM author WHERE name='Author II'));

INSERT INTO anime_genre(animeid, genreid) VALUES
    ((SELECT animeid FROM anime WHERE name='Anime I'), (SELECT genreid FROM genre WHERE label='Genre I')),
    ((SELECT animeid FROM anime WHERE name='Anime II'), (SELECT genreid FROM genre WHERE label='Genre II'));

INSERT INTO file(contenttype, data) VALUES
    ('image/png', 'sample text');

CREATE EXTENSION IF NOT EXISTS pgcrypto;
INSERT INTO usser (username, password) VALUES ('user', crypt('pass', gen_salt('bf')));

