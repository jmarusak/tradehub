CREATE OR REPLACE TABLE `tradehub.supply` (
  supply_id STRING,
  party_id STRING,
  title STRING,
  price FLOAT64,
  description STRING,
  embedding ARRAY<FLOAT64>
);
