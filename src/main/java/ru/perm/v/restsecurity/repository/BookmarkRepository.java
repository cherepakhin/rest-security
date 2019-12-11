package ru.perm.v.restsecurity.repository;

import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.perm.v.restsecurity.model.Bookmark;


public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

	Collection<Bookmark> findByAccountUsername(String username);
}
