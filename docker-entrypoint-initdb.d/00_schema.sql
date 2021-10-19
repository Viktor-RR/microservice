CREATE TABLE payment (
  id BIGINT PRIMARY KEY,
  sender_id BIGINT NOT NULL,
  amount BIGINT NOT NULL,
  comment VARCHAR (128),
  card_number VARCHAR (128) NOT NULL
);