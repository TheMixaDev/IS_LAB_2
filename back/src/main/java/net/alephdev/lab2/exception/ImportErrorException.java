package net.alephdev.lab2.exception;

import lombok.Getter;
import net.alephdev.lab2.models.User;

@Getter
public class ImportErrorException extends RuntimeException {
    User user;

    public ImportErrorException(String message, User user) {
        super(message);
        this.user = user;
    }
}
