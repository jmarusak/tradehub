curl -i -X POST http://localhost:8080/api/supplies \
  -H "Content-Type: application/json" \
  -d '{"supplyId":"", "partyId":"3", "title":"wrap", "price":11.22, "description":"corn wrap", "embedding":[1.2,22.2,0.4]}'
