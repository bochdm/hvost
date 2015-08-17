package com.hvost.support;

import com.hvost.activepeople.Question;
import com.hvost.blog.Post;
import com.hvost.images.Image;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by kseniaselezneva on 15/08/15.
 */
public class ImageUtils {

  public static Image getUploadImageInfo (MultipartFile file, Object entity){

    Image imageInfo =  new Image();
    imageInfo.setSize(file.getSize());
    imageInfo.setType(file.getContentType());

    if (entity instanceof Post){
      Post post = (Post) entity;
      imageInfo.setName(post.getId() + "_" + file.getOriginalFilename());
      imageInfo.setCategory(1);
      imageInfo.setIdEntity(post.getId());
    }
    if (entity instanceof Question){
      Question q = (Question) entity;
      imageInfo.setName(q.getId() + "_" + file.getOriginalFilename());
      imageInfo.setCategory(2);
      imageInfo.setIdEntity(q.getId());
    }

    return imageInfo;
  }

  public static void saveFileToLocalDisk(MultipartFile file, Image imageInfo){
    try {
      StringBuilder path = new StringBuilder(System.getProperty("catalina.home"));
      path.append(File.separator)
          .append(System.getProperty("image.path"))
          .append("images")
          .append(File.separator)
          .append("activepeople")
          .append(File.separator);
          //.append(imageInfo.getName());
//      File imagesDir = new File(rootPath + File.separator + "domain" + File.separator + "tkhostov.com" + File.separator + "images" + File.separator + "blog");
      File imagesDir = new File(path.toString());

      FileCopyUtils.copy(file.getBytes(), new FileOutputStream(imagesDir + File.separator + imageInfo.getName()));
    } catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }


  }
}
