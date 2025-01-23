create or replace table `tradehub.demand` (
  demand_id string,
  party_id string,
  title string,
  price float64,
  description string,
  embedding array<float64>
);
