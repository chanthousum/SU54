mvn -DskipTests clean install
docker build -t su54_pos_api .
docker compose up -d --build