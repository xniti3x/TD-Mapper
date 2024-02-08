package com.tdmapper.application.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdmapper.application.models.CorrespondentDto;
import com.tdmapper.application.models.DocumentDto;

@Service
public class RestService {

    public List<CorrespondentDto> getCorrespondence() {

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<CorrespondentDto> correspondenceList = new ArrayList<>();
        try {
            JsonNode results = mapper
                    .readTree(new File("/workspaces/TD-Mapper/src/main/resources/mock-api/correspondence.json"))
                    .get("results");

            Iterator<JsonNode> elements = results.elements();

            while (elements.hasNext()) {
                JsonNode node = elements.next();//id
                correspondenceList.add(
                    new CorrespondentDto(
                        node.get("id").asText(),
                        node.get("name").asText()
                    ));
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return correspondenceList;
    }

    public List<DocumentDto> getDocumentsByCorrespondenceId(String id) {

        ObjectMapper mapper = new ObjectMapper();
        ArrayList<DocumentDto> documentList = new ArrayList<>();
        try {
            JsonNode results = mapper
                    .readTree(new File("/workspaces/TD-Mapper/src/main/resources/mock-api/documents.json"))
                    .get("results");

            Iterator<JsonNode> elements = results.elements();

            while (elements.hasNext()) {
                JsonNode node = elements.next();
                if(node.get("correspondent").asText().equals(id)){
                    documentList.add( new DocumentDto(node.get("id").asText(),node.get("archived_file_name").asText()));
                }
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return documentList;
    }
}
