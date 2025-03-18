package org.beatrice.dgtuProject.init;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.beatrice.dgtuProject.model.Tag;
import org.beatrice.dgtuProject.repository.TagRepository;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class TagInit {
    private final TagRepository tagRepository;
    private final ObjectMapper objectMapper;

    public TagInit(TagRepository tagRepository, ObjectMapper objectMapper) {
        this.tagRepository = tagRepository;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        try {
            // Читаем JSON-файл
            InputStream inputStream = getClass().getResourceAsStream("/tags.json");
            JsonNode rootNode = objectMapper.readTree(inputStream);

            // Определяем порядок групп
            List<String> groupOrder = List.of("General", "Priority");

            // Вставляем теги в указанном порядке
            groupOrder.forEach(group -> addTagsByGroup(rootNode, group));

        } catch (IOException e) {
            throw new RuntimeException("Failed to load tags.json", e);
        }
    }

    private void addTagsByGroup(JsonNode rootNode, String group) {
        rootNode.get("categories").forEach(category -> {
            if (group.equals(category.get("group").asText())) {
                category.get("tags").forEach(tagNode -> {
                    tagRepository.findByName(tagNode.get("name").asText())
                            .orElseGet(() -> tagRepository.save(new Tag(tagNode.get("name").asText())));
                });
            }
        });
    }
}
