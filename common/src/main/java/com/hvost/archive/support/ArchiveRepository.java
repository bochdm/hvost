package com.hvost.archive.support;

import com.hvost.archive.Archive;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

/**
 * Created by kseniaselezneva on 05/07/15.
 */
public interface ArchiveRepository extends JpaRepository<Archive, Long> {

}
