package web.mvc.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 필수!!
public class AppConfig {
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory getJPAQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    /*
    엔티티->dto
    dto->엔티티
     */
    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
