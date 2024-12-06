package net.alephdev.lab2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.alephdev.lab2.enums.MusicGenre;
import net.alephdev.lab2.models.Coordinates;
import net.alephdev.lab2.models.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MusicBandShortDto {
    private Long id;
    private String name;
    private MusicGenre genre;
    private Coordinates coordinates;
    private User createdBy;
}
