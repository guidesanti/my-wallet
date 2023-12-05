try {
   result = db.getCollection('saga-transactions').createIndex({ idempotenceId : 1 }, { unique: true, expireAfterSeconds: 86400 });
   console.log(result);
   result = db.getCollection('saga-responses').createIndex({ idempotenceId : 1 }, { unique: true, expireAfterSeconds: 86400 });
   console.log(result);
   result = db.getCollection('saga-events').createIndex({ idempotenceId : 1 }, { unique: true, expireAfterSeconds: 86400 });
   console.log(result);
} catch (e) {
    console.log("Failed to create index: " + e);
    throw e;
}
