INSERT INTO LocationType (type_name) VALUES('Museum'),('Private Collection');

INSERT INTO EventType (event_name) VALUES('Exhibition'),('Wrongdoor');

INSERT INTO Location (location_name, location_description, location, location_type_id) VALUES
('National Museum', 'Largest museum in the country', '123 Main Street', 1),
('Art Object Gallery', 'Public art gallery', '456 Art Avenue', 2);

INSERT INTO Owner (owner_name, owner_description) VALUES
('Art Collector', 'Private collector with a diverse art collection'),
('National Museum', 'Largest museum in the country');

INSERT INTO Author (name, description, date_of_birth ) VALUES
('John Artist', 'Renowned painter', '1990-05-15'),
('Jane Sculptor', 'Famous sculptor', '1985-12-10');

INSERT INTO Event (event_type_id, event_description, event_date, event_location_id, event_price) VALUES
(1, 'Impressionist Art Exhibition', '2024-03-01', 1, 20),
(2, 'Wrongdoor happened...', '2023-12-01', 1, 20),
(1, 'Abstract Art Workshop', '2024-04-15', 2, 10);

INSERT INTO Purchase (purchase_date, purchase_price, seller_id, buyer_id) VALUES
('2024-02-10', 5000, 1, 2),
('2024-03-20', 300, 2, 1);

INSERT INTO ArtObject (author_id, art_object_name, art_object_description, current_owner_id, current_location_id, date) VALUES
(1, 'Art #1', 'this is description of Art #1', 1, 1, '2023-06-22'),
(2, 'Art #2', 'this is description of Art #2', 2, 2, '2024-01-08');