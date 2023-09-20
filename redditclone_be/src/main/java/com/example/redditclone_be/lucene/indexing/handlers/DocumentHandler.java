package com.example.redditclone_be.lucene.indexing.handlers;

import com.example.redditclone_be.model.entity.Post;
import com.example.redditclone_be.model.entity.elasticEntities.PostES;

import java.io.File;

public abstract class DocumentHandler {

    /**
     * Od prosledjene datoteke se konstruise Lucene Document
     *
     * @param file
     *            datoteka u kojoj se nalaze informacije
     * @return Lucene Document
     */
    public abstract PostES getIndexUnit(File file);
    public abstract String getText(File file);
}
