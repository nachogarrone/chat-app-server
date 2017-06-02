package com.sd.chat.services;

import com.mongodb.gridfs.GridFSDBFile;
import com.sd.chat.MongoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * Created by nachogarrone on 6/1/17.
 */
@Service
public class FileService {
    @Autowired
    private MongoConfig mongoConfig;

    public boolean saveFile(String name, InputStream file) throws Exception {
        mongoConfig.gridFsTemplate().store(file, name, "image/png", null).getId().toString();
        return true;
    }

    public GridFSDBFile getFile(String id) throws Exception {
        return mongoConfig.gridFsTemplate().findOne(new Query(Criteria.where("_id").is(id)));
    }

    public List<GridFSDBFile> getFiles() throws Exception {
        return mongoConfig.gridFsTemplate().find(null);
    }
}
