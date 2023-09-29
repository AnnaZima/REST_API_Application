package net.annakat.restapp.service;

import net.annakat.restapp.model.File;
import net.annakat.restapp.repository.FileRepository;
import net.annakat.restapp.repository.impl.FileRepositoryImpl;

public class FileService {
    FileRepository fileRepository = new FileRepositoryImpl();

    public File createFile (File file) {
       return fileRepository.create(file);
    }

    public File getFile (Integer id) {
        return fileRepository.get(id);
    }

}
