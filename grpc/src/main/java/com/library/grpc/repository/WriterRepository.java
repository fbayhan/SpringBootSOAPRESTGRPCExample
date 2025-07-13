package com.library.grpc.repository;

 import com.library.grpc.model.Writer;
 import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriterRepository extends JpaRepository<Writer, Long> {

}
