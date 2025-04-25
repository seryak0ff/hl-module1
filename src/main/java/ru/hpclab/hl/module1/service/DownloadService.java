package ru.hpclab.hl.module1.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.model.Download;
import ru.hpclab.hl.module1.model.User;
import ru.hpclab.hl.module1.repository.DownloadRepository;
import ru.hpclab.hl.module1.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class DownloadService {
    private final DownloadRepository repository;
    private final UserRepository userRepository;

    public DownloadService(DownloadRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
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

    public void clearAllDownloads() {
        repository.deleteAll();
    }
}
