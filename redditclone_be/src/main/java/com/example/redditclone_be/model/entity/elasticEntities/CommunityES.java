package com.example.redditclone_be.model.entity.elasticEntities;

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
@NoArgsConstructor
@Document(indexName = "communities")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class CommunityES {
    @Id
    private String id;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Text)
    private String description;
    @Field(type = FieldType.Date)
    private LocalDate creationDate;
    @Field(type = FieldType.Boolean)
    private boolean isSuspended;
    @Field(type = FieldType.Text)
    private String suspendedReason;

    @Field(type = FieldType.Text)
    private String filePath;
    @Field(type = FieldType.Text)
    private String fileText;


}
