package ru.practicum.explorewithme.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.user.model.User;

/**
 * Интерфейс содержащий методы для работы с таблицей базы данных пользователей.
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
