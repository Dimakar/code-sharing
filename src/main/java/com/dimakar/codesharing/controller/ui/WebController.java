package com.dimakar.codesharing.controller.ui;

import com.dimakar.codesharing.controller.api.CodeController;
import com.dimakar.codesharing.dto.GetSnippetsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class WebController {

    @Autowired
    private CodeController codeController;

    @GetMapping("/code/{id}")
    public String getCurrentCode(Model model, @PathVariable UUID id, HttpServletResponse response) {
        ResponseEntity<GetSnippetsResponse> code = codeController.getCode(id);
        if (code.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            response.setStatus(404);
        } else {
            response.setStatus(200);
            GetSnippetsResponse codSnippet = code.getBody();
            model.addAttribute("date", codSnippet.getDate());
            model.addAttribute("code", codSnippet.getCode());
            if (codSnippet.getHasTimeRestriction()) {
                model.addAttribute("time", codSnippet.getTime());
            }
            if (codSnippet.getHasViewsRestriction()) {
                model.addAttribute("views", codSnippet.getViews());
            }
        }
        return "currentcode";
    }

    @GetMapping("/code/new")
    public String addNewCode() {
        return "newcode";
    }


    @GetMapping("/code/latest")
    public String getLatestCodeSnippets(Model model) {
        model.addAttribute("snippets", codeController.getLatestSnippetsCode());
        return "latest";
    }
}
