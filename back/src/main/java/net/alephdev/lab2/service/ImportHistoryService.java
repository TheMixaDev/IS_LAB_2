package net.alephdev.lab2.service;

import lombok.RequiredArgsConstructor;
import net.alephdev.lab2.enums.Role;
import net.alephdev.lab2.models.ImportHistory;
import net.alephdev.lab2.models.User;
import net.alephdev.lab2.repository.ImportHistoryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportHistoryService {
    private final ImportHistoryRepository importHistoryRepository;
    private final UserService userService;

    public ImportHistory createImportRecord(ImportHistory importHistory) {
        return importHistoryRepository.save(importHistory);
    }

    public List<ImportHistory> getImportHistory(User user) {
        if(user.getRole() == Role.ADMIN) {
            return importHistoryRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        }
        return importHistoryRepository.findByCreatedBy(user);
    }
}
