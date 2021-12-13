CREATE TABLE IF NOT EXISTS document
(
    id UUID DEFAULT random_uuid() PRIMARY KEY,
    type VARCHAR NOT NULL,
    year INTEGER NOT NULL,
    template BLOB NOT NULL
)