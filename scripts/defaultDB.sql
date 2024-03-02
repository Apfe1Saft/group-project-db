CREATE TABLE IF NOT EXISTS LocationType (
  location_type_id SERIAL PRIMARY KEY,
  location_type_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS EventType (
  event_type_id SERIAL PRIMARY KEY,
  event_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Location (
  location_id SERIAL PRIMARY KEY,
  location_name VARCHAR(255),
  location_description VARCHAR(255),
  placement VARCHAR(255),
  date_of_opening DATE,
  location_type_id INTEGER REFERENCES LocationType(location_type_id)
);

CREATE TABLE IF NOT EXISTS Owner (
  owner_id SERIAL PRIMARY KEY,
  owner_name VARCHAR(255),
  owner_description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Author (
  author_id SERIAL PRIMARY KEY,
  author_name VARCHAR(255),
  author_description VARCHAR(255),
  author_date_of_birth VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Event (
  event_id SERIAL PRIMARY KEY,
  event_type_id INTEGER REFERENCES EventType(event_type_id),
  event_description VARCHAR(255),
  event_name VARCHAR(255),
  event_start_date DATE,
  event_end_date DATE,
  event_location_id INTEGER REFERENCES Location(location_id),
  /* event_objects INTEGER[] REFERENCES ArtObject(art_object_id), не работает*/
  event_price INTEGER
);

CREATE TABLE IF NOT EXISTS ArtObject (
  art_object_id SERIAL PRIMARY KEY,
  art_object_name VARCHAR(255),
  art_object_description VARCHAR(255),
  author_id INTEGER REFERENCES Author(author_id),
  current_owner_id INTEGER REFERENCES Owner(owner_id),
  current_location_id INTEGER REFERENCES Location(location_id),
  date_of_creation VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Purchase (
  purchase_id SERIAL PRIMARY KEY,
  purchase_date DATE,
  purchase_price INTEGER,
  art_object_id INTEGER REFERENCES ArtObject(art_object_id),
  seller_id INTEGER REFERENCES Owner(owner_id),
  buyer_id INTEGER REFERENCES Owner(owner_id)
);

CREATE INDEX idx_author_id ON Author(author_id);
CREATE INDEX idx_location_name ON Location(location_name);