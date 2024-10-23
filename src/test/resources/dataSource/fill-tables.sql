INSERT INTO departures (id, package_type, owner_index, owner_address, owner_name, status) VALUES
    ('ea901f00-ecfe-4bfc-9b35-b9e0356d3e21', 'LETTER', '196656', 'Pushkino', 'Pavel', 'ARRIVED'),
    ('ea901f00-ecfe-4bfc-9b35-b9e0356d3e22', 'PACKAGE', '196655', 'Kolpino', 'notPavel', 'WAITING'),
    ('ea901f00-ecfe-4bfc-9b35-b9e0356d3e23', 'LETTER', '196656', 'Kolpino', 'notPavel', 'WAITING'),
    ('ea901f00-ecfe-4bfc-9b35-b9e0356d3e24', 'LETTER', '196655', 'Pushkino', 'Pavel', 'SENT');

INSERT INTO posts (id, index, name, address) VALUES
    (2, '196655', 'Ivanovskiy', 'Kolpino Tverskaya 41'),
    (3, '196656', 'notIvanovskiy', 'Pushkino Pivnaya 32');

INSERT INTO departures_posts (id, departure_id, post_id, when_arrived, new_status) VALUES
    (1, 'ea901f00-ecfe-4bfc-9b35-b9e0356d3e21', 2, NOW(), 'ARRIVED'),
    (2, 'ea901f00-ecfe-4bfc-9b35-b9e0356d3e22', 2, NOW(), 'ARRIVED');