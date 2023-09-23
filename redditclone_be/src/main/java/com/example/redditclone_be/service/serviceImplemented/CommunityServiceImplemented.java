package com.example.redditclone_be.service.serviceImplemented;

import com.example.redditclone_be.lucene.indexing.handlers.PDFHandler;
import com.example.redditclone_be.lucene.search.QueryBuilderCustom;
import com.example.redditclone_be.model.dto.CommunityDTO;
import com.example.redditclone_be.model.dto.CommunityPdfDTO;
import com.example.redditclone_be.model.entity.Community;
import com.example.redditclone_be.model.entity.EReactionType;
import com.example.redditclone_be.model.entity.Reaction;
import com.example.redditclone_be.model.entity.elasticEntities.CommunityES;
import com.example.redditclone_be.model.entity.elasticEntities.PostES;
import com.example.redditclone_be.repository.CommunityESRepository;
import com.example.redditclone_be.repository.CommunityRepository;
import com.example.redditclone_be.service.CommunityService;
import org.elasticsearch.index.query.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.common.UUIDs.base64UUID;

@Service
public class CommunityServiceImplemented implements CommunityService {

    final CommunityESRepository communityRepository;

    final ElasticsearchRestTemplate elasticsearchRestTemplate;

    final ElasticsearchOperations elasticsearchOperations;

    @Value("${files.path}")
    private String filesPath;

    public CommunityServiceImplemented(CommunityESRepository communityRepository,
                                       ElasticsearchRestTemplate elasticsearchRestTemplate, ElasticsearchOperations elasticsearchOperations) {
        this.communityRepository = communityRepository;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @Override
    public CommunityES createCommunity(CommunityDTO communityDTO) {
        Optional<CommunityES> community = communityRepository.findFirstByName(communityDTO.getName());

        if (community.isPresent()){
            return null;
        }
        CommunityES newCommunity = new CommunityES();
        newCommunity.setId(base64UUID());
        newCommunity.setName(communityDTO.getName());
        newCommunity.setDescription((communityDTO.getDescription()));
        newCommunity.setCreationDate(LocalDate.now());
        newCommunity.setSuspended(false);
        newCommunity.setPostsNum(0);

        newCommunity = communityRepository.save(newCommunity);

        return newCommunity;
    }

    @Override
    public void indexUploadedFileCommunity(CommunityPdfDTO communityPdfDTO) throws IOException {
        for (MultipartFile file : communityPdfDTO.getFiles()) {
            if (file.isEmpty()) {
                continue;
            }
            String fileName = saveUploadedFile(file);
            if (fileName != null){
                CommunityES postIndexUnit = new PDFHandler().getIndexUnitCommunity(new File(fileName));
                postIndexUnit.setId(communityPdfDTO.getId());
                postIndexUnit.setName(communityPdfDTO.getName());
                postIndexUnit.setDescription(communityPdfDTO.getDescription());
                postIndexUnit.setSuspended(communityPdfDTO.isSuspended());
                postIndexUnit.setPostsNum(communityPdfDTO.getPostsNum());
                communityRepository.save(postIndexUnit);
            }
        }
    }

    private String saveUploadedFile(MultipartFile file) throws IOException {
        String retVal = null;
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(new File(filesPath).getAbsolutePath() + File.separator + file.getOriginalFilename());
            Files.write(path, bytes);
            retVal = path.toString();
        }
        return retVal;
    }

    @Override
    public List<CommunityES> allCommunities() {
        List<CommunityES> communitiesList = communityRepository.findAll();
        return communitiesList;
    }

    @Override
    public CommunityES findById(Long id) {
        Optional<CommunityES> community = communityRepository.findById(id);
        if(community.isPresent()){
            return community.get();
        }
        return null;
    }

    @Override
    public CommunityES saveComm(CommunityES community) {
        return communityRepository.save(community);
    }

    @Override
    public List<CommunityES> allCommunitiesAvailable() {
        return communityRepository.findAllBySuspendedIsFalse();
    }

    @Override
    public List<CommunityES> communitySearch(String input, long from, long to) {
        MultiMatchQueryBuilder multimatchQuery = QueryBuilders.multiMatchQuery(input, "name", "description", "fileText");
        WildcardQueryBuilder titleWildcard = QueryBuilders.wildcardQuery("name", input);
        WildcardQueryBuilder textWildcard = QueryBuilders.wildcardQuery("description", input);
        WildcardQueryBuilder filetextWildcard = QueryBuilders.wildcardQuery("fileText", input);
        RangeQueryBuilder postnumQuery = QueryBuilders.rangeQuery("postsNum").gte(from).lte(to);

        BoolQueryBuilder finalQuery = QueryBuilders.boolQuery()
                .should(multimatchQuery)
                .should(titleWildcard)
                .should(textWildcard)
                .should(filetextWildcard)
                .must(postnumQuery);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(finalQuery)
                .build();

        SearchHits<CommunityES> communityHits = elasticsearchOperations.search(searchQuery, CommunityES.class, IndexCoordinates.of("communities"));
        List<CommunityES> communities = new ArrayList<>();
        communityHits.forEach(hit -> {
            communities.add(hit.getContent());
        });
        return communities;
    }

    @Override
    public boolean editPostNum(PostES postES) {
        Optional<CommunityES> existingCommunity = communityRepository.findById(postES.getCommunity());
        if (existingCommunity.isPresent()) {
            CommunityES community = existingCommunity.get();
            int postnum = community.getPostsNum();
            postnum = postnum + 1;
            community.setPostsNum(postnum);
            communityRepository.save(community);
            return true;
        }
        return false;
    }
}
