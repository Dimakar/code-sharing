package com.dimakar.codesharing.service;

import com.dimakar.codesharing.dto.CodeSnippetDto;
import com.dimakar.codesharing.dto.GetSnippetsResponse;
import com.dimakar.codesharing.dto.NewCodeResponse;
import com.dimakar.codesharing.model.CodeSnippetEntity;
import com.dimakar.codesharing.repository.CodeSnippetRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class CodeSnippetService {

    private final CodeSnippetRepository codeSnippetRepository;
    public ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        modelMapper = new ModelMapper();
        modelMapper
                .addConverter(src -> src.getSource()
                                .format(DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy")),
                        LocalDateTime.class, String.class);
    }


    @Autowired
    public CodeSnippetService(CodeSnippetRepository codeSnippetRepository) {
        this.codeSnippetRepository = codeSnippetRepository;
    }

    public List<GetSnippetsResponse> getLastSnippets() {
        return codeSnippetRepository.findAllByHasRestriction(PageRequest.of(0, 10, Sort.Direction.DESC, "id"), false)
                .stream()
                .map(sn -> modelMapper.map(sn, GetSnippetsResponse.class))
                .toList();
    }

    public GetSnippetsResponse getSnippetById(UUID id) {

        CodeSnippetEntity codeSnippetEntity = codeSnippetRepository.findByUid(id);
        if (codeSnippetEntity == null) return null;
        long availableTime = 0;
        if (codeSnippetEntity.getHasTimeRestriction()) {
            availableTime = codeSnippetEntity.getTime() - Duration.between(codeSnippetEntity.getDate(), LocalDateTime.now(ZoneId.of("Europe/Moscow")))
                    .getSeconds();
            if (availableTime <= 0) {
                codeSnippetRepository.delete(codeSnippetEntity);
                return null;
            }
        }
        if (codeSnippetEntity.getHasViewsRestriction()) {
            codeSnippetEntity = codeSnippetRepository.save(codeSnippetEntity.withViews(codeSnippetEntity.getViews() - 1));
            if (codeSnippetEntity.getViews() == 0) {
                codeSnippetRepository.delete(codeSnippetEntity);
            }
        }
        return modelMapper.map(codeSnippetEntity, GetSnippetsResponse.class)
                .withTime(availableTime);
    }

    public NewCodeResponse addCodeSnippet(CodeSnippetDto codeSnippetDto) {
        CodeSnippetEntity codeSnippetEntity = modelMapper.map(codeSnippetDto, CodeSnippetEntity.class);
        Boolean hasTimeRestriction = codeSnippetEntity.getTime() > 0;
        Boolean hasViewsRestriction = codeSnippetEntity.getViews() > 0;
        codeSnippetEntity = codeSnippetEntity
                .withUid(UUID.randomUUID())
                .withHasTimeRestriction(hasTimeRestriction)
                .withHasViewsRestriction(hasViewsRestriction)
                .withHasRestriction(hasTimeRestriction || hasViewsRestriction)
                .withDate(LocalDateTime.now(ZoneId.of("Europe/Moscow")))
                .withTime(hasTimeRestriction ? codeSnippetEntity.getTime() : 0)
                .withViews(hasViewsRestriction ? codeSnippetEntity.getViews() : 0);
        return modelMapper.map(codeSnippetRepository.save(codeSnippetEntity), NewCodeResponse.class);
    }
}
