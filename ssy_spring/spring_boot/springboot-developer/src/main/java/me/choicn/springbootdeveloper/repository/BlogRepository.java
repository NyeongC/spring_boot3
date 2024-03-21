package me.choicn.springbootdeveloper.repository;

import me.choicn.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Long, Article> {
}
