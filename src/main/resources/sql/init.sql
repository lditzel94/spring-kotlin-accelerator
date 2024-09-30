CREATE TABLE IF NOT EXISTS domain
(
    id   BIGSERIAL PRIMARY KEY,
    data VARCHAR(100)
);

INSERT INTO domain (id, data)
VALUES (1, 'First'),
       (2, 'Second'),
       (3, 'Third'),
       (4, 'Fourth'),
       (5, 'Fifth'),
       (6, 'Sixth');