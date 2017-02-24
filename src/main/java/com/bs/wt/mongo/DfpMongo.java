package com.bs.wt.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DfpMongo extends MongoRepository<AndroidDeviceFingerprint, String> {

}
