select
  query.title as demand_title,
  base.title as supply_title,
  query.description as demand_description,
  base.description as supply_description,
  distance  
from
  vector_search(
    table `tradehub.supply`,
    'embedding',
    table `tradehub.demand`,
    top_k => 1,
    distance_type => 'COSINE',
    options => '{"use_brute_force":true}'
  )
  order by query.title, distance;
