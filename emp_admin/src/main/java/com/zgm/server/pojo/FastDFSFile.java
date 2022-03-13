package com.zgm.server.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FastDFSFile implements Serializable {
    // 文件名字
    private String name;
    // 文件内容
    private byte[] content;
    // 文件扩展名
    private String ext;
    // 文件MD5摘要
    private String md5;
    // 文件创建作者
    private String author;
}
