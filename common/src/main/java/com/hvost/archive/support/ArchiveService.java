package com.hvost.archive.support;

import com.hvost.archive.Archive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by kseniaselezneva on 05/07/15.
 */
@Service
public class ArchiveService {
  private int size = 25;

  @Autowired
  ArchiveRepository archiveRepository;

  public Page<Archive> getAllArchives(Pageable pageRequest){
    return archiveRepository.findAll(pageRequest);

  }

  public Archive getArchive(Long id){
    Archive archive = archiveRepository.findOne(id);
    if (archive != null){
      return archive;
    }
    return null;
  }

}
