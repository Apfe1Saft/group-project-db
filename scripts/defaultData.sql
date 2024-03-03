INSERT INTO LocationType (location_type_name) VALUES ('Museum'), ('Private Collection');

INSERT INTO EventType (event_name) VALUES ('Exhibition'), ('Wrongdoor');

INSERT INTO Location (location_name, location_description, placement, date_of_opening, location_type_id) VALUES
  ('Location1', 'Description1', 'Placement1', '2022-01-01', 1),
  ('Location2', 'Description2', 'Placement2', '2022-02-01', 2),
  ('Location3', 'Description3', 'Placement3', '2022-03-01', 1);

INSERT INTO Owner (owner_name, owner_description) VALUES
  ('Owner1', 'Description1'),
  ('Owner2', 'Description2'),
  ('Owner3', 'Description3');

INSERT INTO Author (author_name, author_description, author_date_of_birth) VALUES
  ('Author1', 'Description1', '1990-01-01'),
  ('Author2', 'Description2', '1985-02-01'),
  ('Author3', 'Description3', '1980-03-01');

INSERT INTO ArtObject (art_object_name, art_object_description, author_id, current_owner_id, current_location_id, date_of_creation) VALUES
  ('ArtObject1', 'Description1', 1, 1, 1, '2000-01-01'),
  ('ArtObject2', 'Description2', 2, 2, 2, '2005-02-01'),
  ('ArtObject3', 'Description3', 3, 3, 3, '2010-03-01');

INSERT INTO Event (event_name, event_type_id, event_description, event_start_date, event_end_date, event_location_id, event_object, event_price) VALUES
  ('Event1', 1, 'Description1', '2022-01-01', '2022-01-05', 1, 1, 100),
  ('Event2', 2, 'Description2', '2022-02-01', '2022-02-05', 2, 2, 150),
  ('Event3', 1, 'Description3', '2022-03-01', '2022-03-05', 3, 3, 200);

INSERT INTO Purchase (purchase_date, purchase_price, art_object_id, seller_id, buyer_id) VALUES
  ('2022-01-01', 1000, 1, 1, 2),
  ('2022-02-01', 1500, 2, 2, 3),
  ('2022-03-01', 2000, 3, 3, 1);

INSERT INTO EventObjects (event_id, art_object_id) VALUES
  (1, 1),
  (2, 2),
  (3, 3);