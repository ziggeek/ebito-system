CREATE TABLE IF NOT EXISTS t_agreements
(
    t_agreement_id     BIGINT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,
    t_client_id        BIGINT REFERENCES t_clients (t_client_id),
    t_agreement_number INT UNIQUE NOT NULL,
    t_agreement_date   DATE       NOT NULL

);
