package com.hvost.archive;

import com.hvost.archive.support.ArchiveService;
import com.hvost.search.SearchResult;
import com.hvost.support.PaginationInfo;
import com.hvost.support.navigation.Navigation;
import com.hvost.support.navigation.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by kseniaselezneva on 05/07/15.
 */
@Controller
@RequestMapping("/video/archive")
@Navigation(Section.ARCHIVE)
public class ArchiveController {


  @Autowired
  private ArchiveService archiveService;

  @RequestMapping(method = {GET, POST})
  public String listArchive(HttpServletRequest request, Model model, @RequestParam(defaultValue = "1") int page){

    PageRequest pageNum  = new PageRequest(page - 1, 10, Sort.Direction.DESC, "createdAt");

    Page<Archive> result = archiveService.getAllArchives(pageNum);
    List<Archive> la = result.getContent();
    for(Archive a : la){
      System.out.println("archive ->" + a);
    }

    model.addAttribute("archiveList", result);
    model.addAttribute("paginationInfo", new PaginationInfo(result));
//    return renderListArchive(result, model);
    return "/video/archive";
  }

  @RequestMapping(value = "/{id:\\d+}", method = {GET})
  public String showArchive(Model model, @PathVariable Long id){
    Archive archive = archiveService.getArchive(id);
    model.addAttribute("archive", archive);

    System.out.println("showArchive -> " + archive);
    return "/video/archive_single";
  }

  @RequestMapping(value = "/search", method = RequestMethod.POST)
  public String search(Model model,
                       @RequestParam String q,
                       @RequestParam(defaultValue = "1") int page ){

    List<SearchResult> result = archiveService.getArchiveBySearch(q, page);
    model.addAttribute("search_results", result);
    model.addAttribute("searchCount", result.size());
    model.addAttribute("queryString", q);

    for (SearchResult sr : result){
      System.out.println("sr - > " + sr);
    }

    return "/video/search_results";
  }

  private String renderListArchive(Page<Archive> page, Model model) {

    model.addAttribute("articles", page);
    model.addAttribute("paginationInfo", new PaginationInfo(page));


    return "/blog/blog_small";
  }
}
