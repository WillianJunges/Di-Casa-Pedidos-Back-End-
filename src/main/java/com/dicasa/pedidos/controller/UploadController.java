package com.dicasa.pedidos.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
public class UploadController {

    private static final String IMAGES_DIR = "./public/imagens/";

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("imagem") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Nenhum arquivo enviado"));
        }
        try {
            Path dir = Paths.get(IMAGES_DIR);
            Files.createDirectories(dir);

            String ext = getExtension(file.getOriginalFilename());
            String filename = "img-" + System.currentTimeMillis() + ext;
            Path dest = dir.resolve(filename);
            Files.copy(file.getInputStream(), dest);

            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String url = baseUrl + "/imagens/" + filename;
            return ResponseEntity.ok(Map.of("url", url));
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("erro", "Erro ao salvar imagem: " + e.getMessage()));
        }
    }

    private String getExtension(String filename) {
        if (filename == null) return "";
        int idx = filename.lastIndexOf('.');
        return idx >= 0 ? filename.substring(idx) : "";
    }
}
