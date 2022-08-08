package com.dimakar.codesharing.controller.api;

import com.dimakar.codesharing.dto.CodeSnippetDto;
import com.dimakar.codesharing.dto.GetSnippetsResponse;
import com.dimakar.codesharing.dto.NewCodeResponse;
import com.dimakar.codesharing.service.CodeSnippetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
public class CodeController {
    @Autowired
    private CodeSnippetService codeSnippetService;

    @GetMapping("/api/code/{id}")
    public ResponseEntity<GetSnippetsResponse> getCode(@PathVariable UUID id) {
        GetSnippetsResponse snippet = codeSnippetService.getSnippetById(id);
        if (snippet == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok()
                .body(snippet);
    }

    @GetMapping("/api/code/latest")
    public List<GetSnippetsResponse> getLatestSnippetsCode() {
        return codeSnippetService.getLastSnippets();
    }

    @PostMapping("/api/code/new")
    public NewCodeResponse addNewCode(@RequestBody CodeSnippetDto codeSnippetDto) {
        return codeSnippetService.addCodeSnippet(codeSnippetDto);
    }
}
