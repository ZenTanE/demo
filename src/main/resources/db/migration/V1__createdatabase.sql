CREATE TABLE anime (
    animeid UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    name text,
    description text,
    type text,
    year int,
    image text);

INSERT INTO anime(name, description, type, year, image) VALUES
	('Anime I','Anime I description','TV',2021,'anime1.jpg'),
	('Anime II','Anime II description','TV',2021,'anime2.jpg'),
	('Anime III','Anime III description','TV',2021,'anime3.jpg'),
	('Anime IV','Anime IV description','TV',2021,'anime4.jpg');

CREATE TABLE file (
    fileid UUID NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    contenttype TEXT,
    data bytea);

INSERT INTO file(contenttype, data) VALUES
    ('image/png', 'sample text');

CREATE TABLE usser (
    userid uuid NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
    username varchar(24) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    role varchar(10),
    enabled boolean DEFAULT true
  );

-- afegim un usuari de prova
CREATE EXTENSION IF NOT EXISTS pgcrypto;
INSERT INTO usser (username, password) VALUES ('user', crypt('pass', gen_salt('bf')));

