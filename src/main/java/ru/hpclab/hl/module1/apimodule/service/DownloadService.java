package ru.hpclab.hl.module1.apimodule.service;

import org.springframework.stereotype.Service;
import ru.hpclab.hl.module1.apimodule.model.Download;
import ru.hpclab.hl.module1.apimodule.repository.DownloadRepository;
import ru.hpclab.hl.module1.apimodule.repository.UserRepository;
import ru.hpclab.hl.module1.apimodule.service.statistics.ObservabilityService;


import java.util.List;
import java.util.UUID;

@Service
public class DownloadService {
    private final DownloadRepository repository;
    private final UserRepository userRepository;
    private final ObservabilityService observabilityService;


    public DownloadService(ObservabilityService observabilityService, DownloadRepository repository, UserRepository userRepository) {
        this.observabilityService = observabilityService;
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public Download addDownload(Download download) {
        this.observabilityService.start(getClass().getSimpleName() + ":addDownload");
        Download temp = repository.save(download);
        this.observabilityService.stop(getClass().getSimpleName() + ":addDownload");
        return temp;
    }

    public Download getDownload(String id) {
        this.observabilityService.start(getClass().getSimpleName() + ":getDownload");
        Download temp = repository.findById(UUID.fromString(id)).orElse(null);
        this.observabilityService.stop(getClass().getSimpleName() + ":getDownload");
        return temp;
    }

    public List<Download> getAllDownloads() {
        this.observabilityService.start(getClass().getSimpleName() + ":getAllDownloads");
        List<Download> temp = repository.findAll();
        this.observabilityService.stop(getClass().getSimpleName() + ":getAllDownloads");
        return temp;
    }

    public void deleteDownload(String id) {
        this.observabilityService.start(getClass().getSimpleName() + ":deleteDownload");
        repository.deleteById(UUID.fromString(id));
        this.observabilityService.stop(getClass().getSimpleName() + ":deleteDownload");
    }

    public void clearAllDownloads() {
        this.observabilityService.start(getClass().getSimpleName() + ":clearAllDownloads");
        repository.deleteAll();
        this.observabilityService.stop(getClass().getSimpleName() + ":clearAllDownloads");
    }
}
