package com.tdmapper.application.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Document extends AbstractEntity{
     private String title;
     private String content;
     private String created;
     private String modified;
     private String correspondent_id;
     private String added;
     private String storage_type;
     private String filename;
     private String archive_serial_number;
     private String document_type_id;
     private String mime_type;
     private String archive_checksum;
     private String archive_filename;
     private String storage_path_id;
     private String original_filename;
     private String owner_id ;
}
