db.test.drop();
db.test.insertMany([
    {
        _id: {
            $oid: "61548622392f306383f16448"
        },
        bucket: "song",
        storageType: "CLOUD_SYSTEM",
        URL: "https://play.min.io",
        accessKey: "Q3AM3UQ867SPQQA43P2F",
        secretKey: "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG",
        _class: "MS"
    },
    {
        _id: {
            $oid: "61548623392f306383f16449"
        },
        path: "C:\\Users\\Admin\\Desktop\\Song",
        storageType: "DISK_FILE_SYSTEM",
        _class: "FS"
    }
])