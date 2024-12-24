INSERT INTO account (name, currency, balance, owner_id)
VALUES ('Account 01', 'USD', 1, UUID '95a5d05f-f432-41e2-bfac-f14c7b5ab507'),
       ('Account 02', 'USD', 2, UUID '95a5d05f-f432-41e2-bfac-f14c7b5ab507'),
       ('Account 03', 'USD', 3, UUID '95a5d05f-f432-41e2-bfac-f14c7b5ab507'),
       ('Account 04', 'USD', 4, UUID '95a5d05f-f432-41e2-bfac-f14c7b5ab507'),
       ('Account 05', 'USD', 5, UUID '95a5d05f-f432-41e2-bfac-f14c7b5ab507'),
       ('Account 06', 'USD', 6, UUID '95a5d05f-f432-41e2-bfac-f14c7b5ab507'),
       ('Account 07', 'USD', 7, UUID '95a5d05f-f432-41e2-bfac-f14c7b5ab507'),
       ('Account 08', 'USD', 8, UUID '95a5d05f-f432-41e2-bfac-f14c7b5ab507'),
       ('Account 09', 'USD', 9, UUID '95a5d05f-f432-41e2-bfac-f14c7b5ab507'),
       ('Account 10', 'USD', 10, UUID '95a5d05f-f432-41e2-bfac-f14c7b5ab507'),
       ('Account 11', 'USD', 11, UUID '95a5d05f-f432-41e2-bfac-f14c7b5ab507'),
       ('Account 12', 'USD', 12, UUID '95a5d05f-f432-41e2-bfac-f14c7b5ab507'),
       ('Account 13', 'USD', 13, UUID '95a5d05f-f432-41e2-bfac-f14c7b5ab507');

INSERT INTO card (name_on_card, card_number, expiry_date, cvv, account_id)
VALUES ('Anatoliy Pylypchuk', '5119016999393526', DATE '2025-01-01', '123', 1),
       ('Anatoliy Pylypchuk', '5517174638379972', DATE '2025-02-01', '456', 2),
       ('Anatoliy Pylypchuk', '5474368905759129', DATE '2025-03-01', '123', 3),
       ('Anatoliy Pylypchuk', '5151190891680071', DATE '2025-04-01', '456', 4),
       ('Anatoliy Pylypchuk', '5134572929221501', DATE '2025-05-01', '123', 5),
       ('Anatoliy Pylypchuk', '5524170017232718', DATE '2025-06-01', '456', 6),
       ('Anatoliy Pylypchuk', '5205941439157535', DATE '2025-08-01', '123', 7),
       ('Anatoliy Pylypchuk', '5453169491518357', DATE '2025-10-01', '456', 8);
