INSERT INTO `tradehub.supply` (supply_id, party_id, title, price, description) 
SELECT GENERATE_UUID(), GENERATE_UUID(), title, 10.50, description FROM `tradehub.funiture`;
