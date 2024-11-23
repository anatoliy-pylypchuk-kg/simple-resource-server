INSERT INTO account (name, balance, owner_id)
VALUES ('Checking Account', 0, UUID '95a5d05f-f432-41e2-bfac-f14c7b5ab507'),
       ('Savings Account', 0, UUID '95a5d05f-f432-41e2-bfac-f14c7b5ab507');

INSERT INTO card (name_on_card, card_number, expiry_date, cvv, account_id)
VALUES ('Anatoliy Pylypchuk', '5119-0169-9939-3526', DATE '2024-12-31', '123', 1),
       ('Anatoliy Pylypchuk', '5517-1746-3837-9972', DATE '2024-12-31', '456', 1);
