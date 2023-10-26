CREATE TABLE orders
(
    order_id    BIGINT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    employee_id BIGINT NOT NULL,
    order_date  TIMESTAMP NOT NULL
);