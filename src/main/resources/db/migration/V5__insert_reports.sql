INSERT INTO report (name, start_date, end_date, developer_id)
SELECT
        'Project ' || s.i,
        TIMESTAMP '2023-03-16 09:00:00' + INTERVAL '1 hour' * (random() * 500 + i),
        TIMESTAMP '2023-03-16 09:00:00' + INTERVAL '1 hour' * (random() * 500 + i + 1),
        1 + mod(i, 3)
FROM generate_series(1, 50) AS s(i);
