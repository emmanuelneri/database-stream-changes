# database-stream-changes
-----

1 - build environment
- `./build.sh`

2 - start environment
- `./init.sh`
- `docker-compose up`
- `./deploy-connector.sh`

3 - run database changes
- `./db-updates`

Topic list

``docker exec kafka kafka-topics --list --zookeeper zookeeper:2181``