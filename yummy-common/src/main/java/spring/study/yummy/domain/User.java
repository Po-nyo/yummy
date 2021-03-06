package spring.study.yummy.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String email;

    @NotEmpty
    private String name;

    @NotNull
    @Setter
    private Long level;

    public boolean isAdmin() {
        return level >= 100L;
    }

    public void updateInformation(String name, String email, Long level) {
        this.name = name;
        this.email = email;
        this.level = level;
    }

    public boolean isActive() {
        return level != 0;
    }

    public void deactivate() {
        this.level = 0L;
    }
}
