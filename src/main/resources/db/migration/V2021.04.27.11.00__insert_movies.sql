INSERT INTO director values(director_id_seq.nextval, 'James', 'Cameron');
INSERT INTO movie values (movie_id_seq.nextval, 'Avatar', 2009, director_id_seq.currval );
INSERT INTO movie values (movie_id_seq.nextval, 'Terminator', 1984, director_id_seq.currval );
INSERT INTO movie values (movie_id_seq.nextval, 'Terminator 2', 1991, director_id_seq.currval );
INSERT INTO movie values (movie_id_seq.nextval, 'Titanic', 1997, director_id_seq.currval );

INSERT INTO director values(director_id_seq.nextval, 'Steven', 'Spielberg');
INSERT INTO movie values (movie_id_seq.currval, 'Raiders of the Lost Ark', 1981, director_id_seq.currval );
INSERT INTO movie values (movie_id_seq.nextval, 'ET', 1984, director_id_seq.currval );
INSERT INTO movie values (movie_id_seq.nextval, 'Jurassic Park', 1993, director_id_seq.currval );

INSERT INTO director values(director_id_seq.nextval, 'Quentin', 'Tarantino');
INSERT INTO movie values (movie_id_seq.nextval, 'Pulp Fiction', 1994, director_id_seq.currval );
INSERT INTO movie values (movie_id_seq.nextval, 'Kill Bill', 2003, director_id_seq.currval );
INSERT INTO movie values (movie_id_seq.nextval, 'Inglourious Basterds', 2009, director_id_seq.currval );