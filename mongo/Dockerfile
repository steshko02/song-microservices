FROM mongo-seed
COPY ./init-db.d/seed.js /docker-entrypoint-initdb.d
CMD mongoimport --host mongo --db resources --collection song_res --drop --file /init-db.d/seed.js /docker-entrypoint-initdb.d --jsonArray