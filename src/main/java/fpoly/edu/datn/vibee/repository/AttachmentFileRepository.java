package fpoly.edu.datn.vibee.repository;

import fpoly.edu.datn.vibee.entity.AttachmentFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentFileRepository extends JpaRepository<AttachmentFile, Integer> {
    @Query("SELECT a.url FROM AttachmentFile a WHERE a.id = :fileId")
    String findUrlById(@Param("fileId") Integer id);
}
