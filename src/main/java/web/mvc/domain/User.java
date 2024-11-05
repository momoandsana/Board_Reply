package web.mvc.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "users") //테이블이름 변경, 오라클에서 user 는 예약어임
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id //pk
   private String userId;
	
   private String pwd;
   private String name;
}
