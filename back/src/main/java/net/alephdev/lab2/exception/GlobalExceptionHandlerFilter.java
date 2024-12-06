package net.alephdev.lab2.exception;

import net.alephdev.lab2.dto.MessageDto;
import net.alephdev.lab2.enums.ImportStatus;
import net.alephdev.lab2.models.ImportHistory;
import net.alephdev.lab2.repository.ImportHistoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandlerFilter {
    private final ImportHistoryRepository importHistoryRepository;

    public GlobalExceptionHandlerFilter(ImportHistoryRepository importHistoryRepository) {
        this.importHistoryRepository = importHistoryRepository;
    }

    @ExceptionHandler(ImportErrorException.class)
    public ResponseEntity<MessageDto> handleImportErrorException(ImportErrorException ex) {
        try {
            importHistoryRepository.save(
                ImportHistory.builder()
                    .status(ImportStatus.ERROR)
                    .createdBy(ex.getUser())
                    .count(0)
                    .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body(new MessageDto(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MessageDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(new MessageDto(ex.getMessage()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNoResourceFoundException(NoResourceFoundException ex) {
        return "forward:/";
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageDto> handleException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.badRequest().body(new MessageDto("Предоставлены неверные данные"));
    }
}
