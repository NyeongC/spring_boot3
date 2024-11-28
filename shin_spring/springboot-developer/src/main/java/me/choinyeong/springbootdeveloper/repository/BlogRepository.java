package me.choinyeong.springbootdeveloper.repository;

import me.choinyeong.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
