
db = db.getSiblingDB('resource');

db.createCollection('storages');
db.createCollection('song_res');
db.storages.insertMany([
    {
        bucket: "song",
        storageType: "CLOUD_SYSTEM",
        URL: "https://play.min.io",
        accessKey: "Q3AM3UQ867SPQQA43P2F",
        secretKey: "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG",
        _class: "MS"
    },
    {
        path: "C:\\Users\\Admin\\Desktop\\Song",
        storageType: "DISK_FILE_SYSTEM",
        _class: "FS"
    }
]);
