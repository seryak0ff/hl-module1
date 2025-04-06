package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.model.Download;
import ru.hpclab.hl.module1.repository.DownloadRepository;

import java.util.List;
import java.util.UUID;

@Service
public class DownloadService {
    private final DownloadRepository repository;

    public DownloadService(DownloadRepository repository) {
        this.repository = repository;
    }

    public Download addDownload(Download download) {
        return repository.save(download);
    }

    public Download getDownload(String id) {
        return repository.findById(UUID.fromString(id)).orElse(null);
    }

    public List<Download> getAllDownloads() {
        return repository.findAll();
    }

    public void deleteDownload(String id) {
        repository.deleteById(UUID.fromString(id));
    }
}
