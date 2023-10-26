CREATE TABLE order_events
(
    event_id        BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    order_id        BIGINT       NOT NULL,
    event_type      TEXT         NOT NULL,
    event_data      JSONB        NOT NULL,
    event_timestamp TIMESTAMP    NOT NULL
);
