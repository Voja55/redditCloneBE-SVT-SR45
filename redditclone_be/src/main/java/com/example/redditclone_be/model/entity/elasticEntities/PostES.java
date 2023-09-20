package com.example.redditclone_be.model.entity.elasticEntities;


import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.model.entity.Flair;
import com.example.redditclone_be.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Document(indexName = "posts")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class PostES {
    @Id
    private String id;
    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Text)
    private String text;
    @Field(type = FieldType.Text)
    private LocalDate creationDate;
    @Field(type = FieldType.Text)
    private String imgPath;
    @Field(type = FieldType.Text)
    private String community;
    @Field(type = FieldType.Text)
    private String postedBy;
    @Field(type = FieldType.Text)
    private String flair;
    @Field(type = FieldType.Text)
    private String filePath;
    @Field(type = FieldType.Text)
    private String fileText;
    @Field(type = FieldType.Integer)
    private  Integer comments;
    @Field(type = FieldType.Integer)
    private  Integer karma;

    private String keywords;
    private String filename;

    public PostES(){}
}
