package ru.practicum.explorewithme.user.model;

import lombok.*;

import javax.persistence.*;

/**
 * Класс пользователя обладающий свойствами: <b>id</b>,
 * <b>name</b>, <b>email</b>.
 */
@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
}
