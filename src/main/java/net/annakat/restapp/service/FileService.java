package net.annakat.restapp.service;

import net.annakat.restapp.model.UFile;
import net.annakat.restapp.repository.FileRepository;
import net.annakat.restapp.repository.impl.FileRepositoryImpl;

import java.util.List;

public class FileService {
    FileRepository fileRepository = new FileRepositoryImpl();

    public UFile addFile(UFile UFile) {
       return fileRepository.create(UFile);
    }

    public UFile getFile (Integer id) {
        return fileRepository.get(id);
    }

    public List<UFile> getAll() {
        return fileRepository.getAll();
    }

    public UFile updateFile(UFile uFile) {
        return fileRepository.update(uFile);
    }

    public void deleteFile(Integer id) {
        fileRepository.delete(id);
    }
}
