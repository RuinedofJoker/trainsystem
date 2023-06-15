package org.hnxxxy.rg1b.domain;

import lombok.Data;

import java.util.Date;

@Data
public class StorageFile {
    private String fileId;
    private Long userId;
    private String filename;
    private String content;
    private String contentType;
    private Date date;
}
