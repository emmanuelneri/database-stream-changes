#!/usr/bin/env bash

docker exec -it db psql -U postgres cdc -c "update customer set name = 'Customer 1' where id = 1;"
docker exec -it db psql -U postgres cdc -c "update customer set name = 'customer 2' where id = 2;"

docker exec -it db psql -U postgres cdc -c "update sale set total = 1550 where id = 1;"
docker exec -it db psql -U postgres cdc -c "update sale set total = 3000 where id = 2;"

docker exec -it db psql -U postgres cdc -c "delete from customer where id = 4;"