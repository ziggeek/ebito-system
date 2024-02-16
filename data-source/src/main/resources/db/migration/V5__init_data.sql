TRUNCATE t_clients RESTART IDENTITY CASCADE;
TRUNCATE t_accounts RESTART IDENTITY CASCADE;
TRUNCATE t_agreements RESTART IDENTITY CASCADE;
TRUNCATE t_operations RESTART IDENTITY CASCADE;

INSERT INTO t_clients (t_firstname, t_lastname, t_middlename)
VALUES ('John', 'Doe', 'Smith'),
       ('Jane', 'Smith', 'Doe');

INSERT INTO t_accounts (t_client_id, t_account_number, t_account_currency)
VALUES (1, 123456789, 'USD'),
       (2, 987654321, 'EURO');

INSERT INTO t_agreements (t_agreement_number, t_agreement_date, t_client_id)
VALUES (123, '2022-01-01', 1),
       (456, '2022-02-01', 2);

INSERT INTO t_operations (t_timestamp, t_payment_method, t_sum, t_account_id)
VALUES ('2021-03-15T12:30:00', 'CARD', 100, 1),
       ('2022-02-01T12:00:00', 'CASH', 200, 2);