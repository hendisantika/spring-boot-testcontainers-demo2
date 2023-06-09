DROP TABLE IF EXISTS persons;
CREATE TABLE persons
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(30),
    last_name  VARCHAR(30),
    address    VARCHAR(255),
    city       VARCHAR(80),
    telephone  VARCHAR(20)
);

INSERT INTO persons
VALUES (1, 'Uzumaki', 'Naruto', '104,The Beresford Road. Konoha', 'Japan', '0182951023'),
       (2, 'Haruno', 'Sakura', '104,The Beresford Road. Konoha', 'Japan', '0182951023'),
       (3, 'Uchiha', 'Sasuke', '104,The Beresford Road. Konoha', 'Japan', '0182951023'),
       (4, 'Hatake', 'Kakashi', '104,The Beresford Road. Konoha', 'Japan', '0182951023'),
       (5, 'Asuma', 'Sarutobi', '104,The Beresford Road. Konoha', 'Japan', '0182951023');
