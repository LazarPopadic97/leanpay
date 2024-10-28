CREATE TABLE IF NOT EXISTS installment_plan(
    id                              BIGSERIAL,
    amount                          DECIMAL     NOT NULL,
    annual_interest_rate            DECIMAL     NOT NULL,
    number_of_months                INTEGER     NOT NULL,
    total_payment                   JSONB       NOT NULL
);

