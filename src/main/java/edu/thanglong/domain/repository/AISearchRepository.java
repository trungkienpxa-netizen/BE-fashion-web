package edu.thanglong.domain.repository;

import java.util.List;

public interface AISearchRepository {
    List<String> findSimilarProductIds(String queryText, int topK);
    void upsertEmbedding(String productId, String textToEmbed);
    void deleteEmbedding(String productId);
}