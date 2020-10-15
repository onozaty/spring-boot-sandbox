package com.github.onozaty.springboot.upload;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class UploadController {

    @PostMapping("/api/upload")
    @ResponseStatus(HttpStatus.OK)
    public void upload(InputStream inputStream) throws IOException, URISyntaxException {

        Path saveDirPath = Paths.get("save-dir");
        Files.createDirectories(saveDirPath);

        Path saveFilePath = saveDirPath.resolve(UUID.randomUUID().toString());

        Files.copy(inputStream, saveFilePath);
    }
}
