package cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.repository;

import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.models.domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository  extends JpaRepository<Player, Long> {
    Optional<Player> findByName(String name);
    boolean existsByName(String name);
}
