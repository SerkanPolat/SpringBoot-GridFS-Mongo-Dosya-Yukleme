package com.sp.gridfs.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileUploadService {
    @Autowired
    GridFsTemplate gridFsTemplate;
    public void UploadFile(MultipartFile file){
        try {
            DBObject dbObject = new BasicDBObject();

            dbObject.put("userId","sp");
            dbObject.put("fileName",file.getName());
            dbObject.put("contentType",file.getContentType());
            dbObject.put("size",file.getSize());
            Object id = gridFsTemplate.store(file.getInputStream(),"sp",dbObject);
            System.out.println(id.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    5f47b8849b359b630640b0de
5f47b96c9b359b630640b0e8
5f47b96d9b359b630640b0f2
     */
    public ResponseEntity<Resource> getFileFor(String fileName) throws IOException {

        GridFSFile gridFSFiles = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(fileName)));
        GridFsResource gridFsResource = gridFsTemplate.getResource(gridFSFiles);
        return ResponseEntity.ok().contentType(
                MediaType.parseMediaType(gridFsResource.getGridFSFile().getMetadata().getString("contentType"))).body(new ByteArrayResource(
                        gridFsResource.getInputStream().readAllBytes()
        ));
    }
}
