package com.hvost.images.support;

import com.hvost.images.Image;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Created by kseniaselezneva on 15/08/15.
 */
@Service
public class ImageService {

  @PersistenceContext
  EntityManager em;

  @Transactional
  public Image uploadFileInfo(Image uploadImage){
    return em.merge(uploadImage);
  }
}
