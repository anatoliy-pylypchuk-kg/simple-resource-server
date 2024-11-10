INSERT INTO account (name, balance, owner_id)
VALUES ('Checking Account', 0, UUID '1e2f393a-a8d4-4932-99ff-9de1e0c5b9e5'),
       ('Savings Account', 0, UUID '1e2f393a-a8d4-4932-99ff-9de1e0c5b9e5');

INSERT INTO card (name_on_card, card_number, expiry_date, cvv, account_id)
VALUES ('Anatoliy Pylypchuk', '5401491467514357', DATE '2024-12-31', '123', 1),
       ('Anatoliy Pylypchuk', '5529411873295745', DATE '2024-12-31', '456', 1);
