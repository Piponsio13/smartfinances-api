package entity;

import enums.roleEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role {
    private roleEnum name;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
