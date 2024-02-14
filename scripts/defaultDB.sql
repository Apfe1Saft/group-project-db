CREATE TABLE IF NOT EXISTS LocationType (
  location_type_id INTEGER PRIMARY KEY,
  type_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS EventType (
  event_type_id INTEGER PRIMARY KEY,
  event_name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Location (
  location_id INTEGER PRIMARY KEY,
  location_name VARCHAR(255),
  location_description VARCHAR(255),
  location VARCHAR(255),
  location_type_id INTEGER REFERENCES LocationType(location_type_id)
);

CREATE TABLE IF NOT EXISTS Owner (
  owner_id INTEGER PRIMARY KEY,
  owner_name VARCHAR(255),
  owner_description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS Author (
  author_id INTEGER PRIMARY KEY,
  name VARCHAR(255),
  description VARCHAR(255),
  date DATE
);

CREATE TABLE IF NOT EXISTS Event (
  event_id INTEGER PRIMARY KEY,
  event_type_id INTEGER REFERENCES EventType(event_type_id),
  event_description VARCHAR(255),
  event_date DATE,
  event_location_id INTEGER REFERENCES Location(location_id),
  event_price INTEGER
);

CREATE TABLE IF NOT EXISTS Purchase (
  purchase_id INTEGER PRIMARY KEY,
  purchase_date DATE,
  purchase_price INTEGER,
  seller_id INTEGER REFERENCES Owner(owner_id),
  buyer_id INTEGER REFERENCES Owner(owner_id)
);

CREATE TABLE IF NOT EXISTS ArtObject (
  art_object_id INTEGER PRIMARY KEY,
  author_id INTEGER REFERENCES Author(author_id),
  current_owner_id INTEGER REFERENCES Owner(owner_id),
  current_location_id INTEGER REFERENCES Location(location_id),
  date DATE
);