package com.sd.chat.services;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.sd.chat.MongoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by nachogarrone on 6/1/17.
 */
@Service
public class FileService {
    @Autowired
    private MongoConfig mongoConfig;

    public String saveFile(String username, MultipartFile file) throws Exception {
        DBObject metaData = new BasicDBObject();
        metaData.put("user", username);
        return mongoConfig.gridFsTemplate().store(file.getInputStream(), file.getName(), "image/png", metaData).getId().toString();
    }

    public GridFSDBFile getFile(String id) throws Exception {
        return mongoConfig.gridFsTemplate().findOne(new Query(Criteria.where("_id").is(id)));
    }

    public List<GridFSDBFile> getFiles() throws Exception {
        return mongoConfig.gridFsTemplate().find(null);
    }
}
