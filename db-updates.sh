#!/usr/bin/env bash

docker exec -it db psql -U postgres cdc -c "update customer set name = 'Customer 1' where id = 1;"
docker exec -it db psql -U postgres cdc -c "update customer set name = 'customer 2' where id = 2;"
docker exec -it db psql -U postgres cdc -c "insert into customer (name) values ('Insert Customer');"

docker exec -it db psql -U postgres cdc -c "update sale set total = 1550 where id = 1;"
docker exec -it db psql -U postgres cdc -c "update sale set total = 3000 where id = 2;"

docker exec -it db psql -U postgres cdc -c "delete from customer where id = 4;"

docker exec -it db psql -U postgres cdc -c "INSERT INTO sale (customer_id, identifier, product, total) VALUES ((select id from customer where name = 'Customer 3'), '1010', 'Product Z', 200);"
docker exec -it db psql -U postgres cdc -c "INSERT INTO sale (customer_id, identifier, product, total) VALUES ((select id from customer where name = 'Customer 3'), '1011', 'Product Y', 100.15);"
docker exec -it db psql -U postgres cdc -c "INSERT INTO sale (customer_id, identifier, product, total) VALUES ((select id from customer where name = 'Customer 3'), '1012', 'Product Z', 110);"
docker exec -it db psql -U postgres cdc -c "INSERT INTO sale (customer_id, identifier, product, total) VALUES ((select id from customer where name = 'Insert Customer'), '1092', 'Product Y', 2000);"
docker exec -it db psql -U postgres cdc -c "INSERT INTO sale (customer_id, identifier, product, total) VALUES ((select id from customer where name = 'Insert Customer'), '1092', 'Product Y', 3000);"