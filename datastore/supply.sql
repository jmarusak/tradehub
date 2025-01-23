create or replace table `tradehub.supply` (
  supply_id string,
  party_id string,
  title string,
  price float64,
  description string,
  embedding array<float64>
);
