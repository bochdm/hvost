package com.hvost.admin;

import com.hvost.aboutme.AboutMe;
import com.hvost.activepeople.Answer;
import com.hvost.activepeople.Question;
import com.hvost.archive.Archive;
import com.hvost.archive.support.ArchiveService;
import com.hvost.blog.CategoryPost;
import com.hvost.blog.Post;
import com.hvost.blog.support.PostService;
import com.hvost.commons.CommonEntity;
import com.hvost.contacts.Contact;
import com.hvost.images.Image;
import com.hvost.images.support.ImageService;
import com.hvost.search.SearchResult;
import com.hvost.startpage.Carousel;
import com.hvost.support.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Dmitry.Bochkanov
 * Date: 2/2/15
 * Time: 8:43 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value="/admin")
public class AdminController {

  @Autowired
  private AdminService adminService;
  @Autowired
  private PostService postService;

  @Autowired
  private ArchiveService archiveService;

  @Autowired
  private ImageService imageService;

  private List<String> faIcon;

  public AdminController() {
    faIcon = new ArrayList<>();
    faIcon.add("fa-icon-glass");
    faIcon.add("fa-icon-music");
    faIcon.add("fa-icon-search");
    faIcon.add("fa-icon-envelope-alt");
    faIcon.add("fa-icon-heart");
    faIcon.add("fa-icon-star");
    faIcon.add("fa-icon-star-empty");
    faIcon.add("fa-icon-user");
    faIcon.add("fa-icon-film");
    faIcon.add("fa-icon-th-large");
    faIcon.add("fa-icon-th");
    faIcon.add("fa-icon-th-list");
    faIcon.add("fa-icon-ok");
    faIcon.add("fa-icon-remove");
    faIcon.add("fa-icon-zoom-in");
    faIcon.add("fa-icon-zoom-out");
    faIcon.add("fa-icon-power-off");
    faIcon.add("fa-icon-off");
    faIcon.add("fa-icon-signal");
    faIcon.add("fa-icon-gear");
    faIcon.add("fa-icon-cog");
    faIcon.add("fa-icon-trash");
    faIcon.add("fa-icon-home");
    faIcon.add("fa-icon-file-alt");
    faIcon.add("fa-icon-time");
    faIcon.add("fa-icon-road");
    faIcon.add("fa-icon-download-alt");
    faIcon.add("fa-icon-download");
    faIcon.add("fa-icon-upload");
    faIcon.add("fa-icon-inbox");
    faIcon.add("fa-icon-play-circle");
    faIcon.add("fa-icon-rotate-right");
    faIcon.add("fa-icon-repeat");
    faIcon.add("fa-icon-refresh");
    faIcon.add("fa-icon-list-alt");
    faIcon.add("fa-icon-lock");
    faIcon.add("fa-icon-flag");
    faIcon.add("fa-icon-headphones");
    faIcon.add("fa-icon-volume-off");
    faIcon.add("fa-icon-volume-down");
    faIcon.add("fa-icon-volume-up");
    faIcon.add("fa-icon-qrcode");
    faIcon.add("fa-icon-barcode");
    faIcon.add("fa-icon-tag");
    faIcon.add("fa-icon-tags");
    faIcon.add("fa-icon-book");
    faIcon.add("fa-icon-bookmark");
    faIcon.add("fa-icon-print");
    faIcon.add("fa-icon-camera");
    faIcon.add("fa-icon-font");
    faIcon.add("fa-icon-bold");
    faIcon.add("fa-icon-italic");
    faIcon.add("fa-icon-text-height");
    faIcon.add("fa-icon-text-width");
    faIcon.add("fa-icon-align-left");
    faIcon.add("fa-icon-align-center");
    faIcon.add("fa-icon-align-right");
    faIcon.add("fa-icon-align-justify");
    faIcon.add("fa-icon-list");
    faIcon.add("fa-icon-indent-left");
    faIcon.add("fa-icon-indent-right");
    faIcon.add("fa-icon-facetime-video");
    faIcon.add("fa-icon-picture");
    faIcon.add("fa-icon-pencil");
    faIcon.add("fa-icon-map-marker");
    faIcon.add("fa-icon-adjust");
    faIcon.add("fa-icon-tint");
    faIcon.add("fa-icon-edit");
    faIcon.add("fa-icon-share");
    faIcon.add("fa-icon-check");
    faIcon.add("fa-icon-move");
    faIcon.add("fa-icon-step-backward");
    faIcon.add("fa-icon-fast-backward");
    faIcon.add("fa-icon-backward");
    faIcon.add("fa-icon-play");
    faIcon.add("fa-icon-pause");
    faIcon.add("fa-icon-stop");
    faIcon.add("fa-icon-forward");
    faIcon.add("fa-icon-fast-forward");
    faIcon.add("fa-icon-step-forward");
    faIcon.add("fa-icon-eject");
    faIcon.add("fa-icon-chevron-left");
    faIcon.add("fa-icon-chevron-right");
    faIcon.add("fa-icon-plus-sign");
    faIcon.add("fa-icon-minus-sign");
    faIcon.add("fa-icon-remove-sign");
    faIcon.add("fa-icon-ok-sign");
    faIcon.add("fa-icon-question-sign");
    faIcon.add("fa-icon-info-sign");
    faIcon.add("fa-icon-screenshot");
    faIcon.add("fa-icon-remove-circle");
    faIcon.add("fa-icon-ok-circle");
    faIcon.add("fa-icon-ban-circle");
    faIcon.add("fa-icon-arrow-left");
    faIcon.add("fa-icon-arrow-right");
    faIcon.add("fa-icon-arrow-up");
    faIcon.add("fa-icon-arrow-down");
    faIcon.add("fa-icon-mail-forward");
    faIcon.add("fa-icon-share-alt");
    faIcon.add("fa-icon-resize-full");
    faIcon.add("fa-icon-resize-small");
    faIcon.add("fa-icon-plus");
    faIcon.add("fa-icon-minus");
    faIcon.add("fa-icon-asterisk");
    faIcon.add("fa-icon-exclamation-sign");
    faIcon.add("fa-icon-gift");
    faIcon.add("fa-icon-leaf");
    faIcon.add("fa-icon-fire");
    faIcon.add("fa-icon-eye-open");
    faIcon.add("fa-icon-eye-close");
    faIcon.add("fa-icon-warning-sign");
    faIcon.add("fa-icon-plane");
    faIcon.add("fa-icon-calendar");
    faIcon.add("fa-icon-random");
    faIcon.add("fa-icon-comment");
    faIcon.add("fa-icon-magnet");
    faIcon.add("fa-icon-chevron-up");
    faIcon.add("fa-icon-chevron-down");
    faIcon.add("fa-icon-retweet");
    faIcon.add("fa-icon-shopping-cart");
    faIcon.add("fa-icon-folder-close");
    faIcon.add("fa-icon-folder-open");
    faIcon.add("fa-icon-resize-vertical");
    faIcon.add("fa-icon-resize-horizontal");
    faIcon.add("fa-icon-bar-chart");
    faIcon.add("fa-icon-twitter-sign");
    faIcon.add("fa-icon-facebook-sign");
    faIcon.add("fa-icon-camera-retro");
    faIcon.add("fa-icon-key");
    faIcon.add("fa-icon-gears");
    faIcon.add("fa-icon-cogs");
    faIcon.add("fa-icon-comments");
    faIcon.add("fa-icon-thumbs-up-alt");
    faIcon.add("fa-icon-thumbs-down-alt");
    faIcon.add("fa-icon-star-half");
    faIcon.add("fa-icon-heart-empty");
    faIcon.add("fa-icon-signout");
    faIcon.add("fa-icon-linkedin-sign");
    faIcon.add("fa-icon-pushpin");
    faIcon.add("fa-icon-external-link");
    faIcon.add("fa-icon-signin");
    faIcon.add("fa-icon-trophy");
    faIcon.add("fa-icon-github-sign");
    faIcon.add("fa-icon-upload-alt");
    faIcon.add("fa-icon-lemon");
    faIcon.add("fa-icon-phone");
    faIcon.add("fa-icon-unchecked");
    faIcon.add("fa-icon-check-empty");
    faIcon.add("fa-icon-bookmark-empty");
    faIcon.add("fa-icon-phone-sign");
    faIcon.add("fa-icon-twitter");
    faIcon.add("fa-icon-facebook");
    faIcon.add("fa-icon-github");
    faIcon.add("fa-icon-unlock");
    faIcon.add("fa-icon-credit-card");
    faIcon.add("fa-icon-rss");
    faIcon.add("fa-icon-hdd");
    faIcon.add("fa-icon-bullhorn");
    faIcon.add("fa-icon-bell");
    faIcon.add("fa-icon-certificate");
    faIcon.add("fa-icon-hand-right");
    faIcon.add("fa-icon-hand-left");
    faIcon.add("fa-icon-hand-up");
    faIcon.add("fa-icon-hand-down");
    faIcon.add("fa-icon-circle-arrow-left");
    faIcon.add("fa-icon-circle-arrow-right");
    faIcon.add("fa-icon-circle-arrow-up");
    faIcon.add("fa-icon-circle-arrow-down");
    faIcon.add("fa-icon-globe");
    faIcon.add("fa-icon-wrench");
    faIcon.add("fa-icon-tasks");
    faIcon.add("fa-icon-filter");
    faIcon.add("fa-icon-briefcase");
    faIcon.add("fa-icon-fullscreen");
    faIcon.add("fa-icon-group");
    faIcon.add("fa-icon-link");
    faIcon.add("fa-icon-cloud");
    faIcon.add("fa-icon-beaker");
    faIcon.add("fa-icon-cut");
    faIcon.add("fa-icon-copy");
    faIcon.add("fa-icon-paperclip");
    faIcon.add("fa-icon-paper-clip");
    faIcon.add("fa-icon-save");
    faIcon.add("fa-icon-sign-blank");
    faIcon.add("fa-icon-reorder");
    faIcon.add("fa-icon-list-ul");
    faIcon.add("fa-icon-list-ol");
    faIcon.add("fa-icon-strikethrough");
    faIcon.add("fa-icon-underline");
    faIcon.add("fa-icon-table");
    faIcon.add("fa-icon-magic");
    faIcon.add("fa-icon-truck");
    faIcon.add("fa-icon-pinterest");
    faIcon.add("fa-icon-pinterest-sign");
    faIcon.add("fa-icon-google-plus-sign");
    faIcon.add("fa-icon-google-plus");
    faIcon.add("fa-icon-money");
    faIcon.add("fa-icon-caret-down");
    faIcon.add("fa-icon-caret-up");
    faIcon.add("fa-icon-caret-left");
    faIcon.add("fa-icon-caret-right");
    faIcon.add("fa-icon-columns");
    faIcon.add("fa-icon-sort");
    faIcon.add("fa-icon-sort-down");
    faIcon.add("fa-icon-sort-up");
    faIcon.add("fa-icon-envelope");
    faIcon.add("fa-icon-linkedin");
    faIcon.add("fa-icon-rotate-left");
    faIcon.add("fa-icon-undo");
    faIcon.add("fa-icon-legal");
    faIcon.add("fa-icon-dashboard");
    faIcon.add("fa-icon-comment-alt");
    faIcon.add("fa-icon-comments-alt");
    faIcon.add("fa-icon-bolt");
    faIcon.add("fa-icon-sitemap");
    faIcon.add("fa-icon-umbrella");
    faIcon.add("fa-icon-paste");
    faIcon.add("fa-icon-lightbulb");
    faIcon.add("fa-icon-exchange");
    faIcon.add("fa-icon-cloud-download");
    faIcon.add("fa-icon-cloud-upload");
    faIcon.add("fa-icon-user-md");
    faIcon.add("fa-icon-stethoscope");
    faIcon.add("fa-icon-suitcase");
    faIcon.add("fa-icon-bell-alt");
    faIcon.add("fa-icon-coffee");
    faIcon.add("fa-icon-food");
    faIcon.add("fa-icon-file-text-alt");
    faIcon.add("fa-icon-building");
    faIcon.add("fa-icon-hospital");
    faIcon.add("fa-icon-ambulance");
    faIcon.add("fa-icon-medkit");
    faIcon.add("fa-icon-fighter-jet");
    faIcon.add("fa-icon-beer");
    faIcon.add("fa-icon-h-sign");
    faIcon.add("fa-icon-plus-sign-alt");
    faIcon.add("fa-icon-double-angle-left");
    faIcon.add("fa-icon-double-angle-right");
    faIcon.add("fa-icon-double-angle-up");
    faIcon.add("fa-icon-double-angle-down");
    faIcon.add("fa-icon-angle-left");
    faIcon.add("fa-icon-angle-right");
    faIcon.add("fa-icon-angle-up");
    faIcon.add("fa-icon-angle-down");
    faIcon.add("fa-icon-desktop");
    faIcon.add("fa-icon-laptop");
    faIcon.add("fa-icon-tablet");
    faIcon.add("fa-icon-mobile-phone");
    faIcon.add("fa-icon-circle-blank");
    faIcon.add("fa-icon-quote-left");
    faIcon.add("fa-icon-quote-right");
    faIcon.add("fa-icon-spinner");
    faIcon.add("fa-icon-circle");
    faIcon.add("fa-icon-mail-reply");
    faIcon.add("fa-icon-reply");
    faIcon.add("fa-icon-github-alt");
    faIcon.add("fa-icon-folder-close-alt");
    faIcon.add("fa-icon-folder-open-alt");
    faIcon.add("fa-icon-expand-alt");
    faIcon.add("fa-icon-collapse-alt");
    faIcon.add("fa-icon-smile");
    faIcon.add("fa-icon-frown");
    faIcon.add("fa-icon-meh");
    faIcon.add("fa-icon-gamepad");
    faIcon.add("fa-icon-keyboard");
    faIcon.add("fa-icon-flag-alt");
    faIcon.add("fa-icon-flag-checkered");
    faIcon.add("fa-icon-terminal");
    faIcon.add("fa-icon-code");
    faIcon.add("fa-icon-reply-all");
    faIcon.add("fa-icon-mail-reply-all");
    faIcon.add("fa-icon-star-half-full");
    faIcon.add("fa-icon-star-half-empty");
    faIcon.add("fa-icon-location-arrow");
    faIcon.add("fa-icon-crop");
    faIcon.add("fa-icon-code-fork");
    faIcon.add("fa-icon-unlink");
    faIcon.add("fa-icon-question");
    faIcon.add("fa-icon-info");
    faIcon.add("fa-icon-exclamation");
    faIcon.add("fa-icon-superscript");
    faIcon.add("fa-icon-subscript");
    faIcon.add("fa-icon-eraser");
    faIcon.add("fa-icon-puzzle-piece");
    faIcon.add("fa-icon-microphone");
    faIcon.add("fa-icon-microphone-off");
    faIcon.add("fa-icon-shield");
    faIcon.add("fa-icon-calendar-empty");
    faIcon.add("fa-icon-fire-extinguisher");
    faIcon.add("fa-icon-rocket");
    faIcon.add("fa-icon-maxcdn");
    faIcon.add("fa-icon-chevron-sign-left");
    faIcon.add("fa-icon-chevron-sign-right");
    faIcon.add("fa-icon-chevron-sign-up");
    faIcon.add("fa-icon-chevron-sign-down");
    faIcon.add("fa-icon-html5");
    faIcon.add("fa-icon-css3");
    faIcon.add("fa-icon-anchor");
    faIcon.add("fa-icon-unlock-alt");
    faIcon.add("fa-icon-bullseye");
    faIcon.add("fa-icon-ellipsis-horizontal");
    faIcon.add("fa-icon-ellipsis-vertical");
    faIcon.add("fa-icon-rss-sign");
    faIcon.add("fa-icon-play-sign");
    faIcon.add("fa-icon-ticket");
    faIcon.add("fa-icon-minus-sign-alt");
    faIcon.add("fa-icon-check-minus");
    faIcon.add("fa-icon-level-up");
    faIcon.add("fa-icon-level-down");
    faIcon.add("fa-icon-check-sign");
    faIcon.add("fa-icon-edit-sign");
    faIcon.add("fa-icon-external-link-sign");
    faIcon.add("fa-icon-share-sign");
    faIcon.add("fa-icon-compass");
    faIcon.add("fa-icon-collapse");
    faIcon.add("fa-icon-collapse-top");
    faIcon.add("fa-icon-expand");
    faIcon.add("fa-icon-euro");
    faIcon.add("fa-icon-eur");
    faIcon.add("fa-icon-gbp");
    faIcon.add("fa-icon-dollar");
    faIcon.add("fa-icon-usd");
    faIcon.add("fa-icon-rupee");
    faIcon.add("fa-icon-inr");
    faIcon.add("fa-icon-yen");
    faIcon.add("fa-icon-jpy");
    faIcon.add("fa-icon-renminbi");
    faIcon.add("fa-icon-cny");
    faIcon.add("fa-icon-won");
    faIcon.add("fa-icon-krw");
    faIcon.add("fa-icon-bitcoin");
    faIcon.add("fa-icon-btc");
    faIcon.add("fa-icon-file");
    faIcon.add("fa-icon-file-text");
    faIcon.add("fa-icon-sort-by-alphabet");
    faIcon.add("fa-icon-sort-by-alphabet-alt");
    faIcon.add("fa-icon-sort-by-attributes");
    faIcon.add("fa-icon-sort-by-attributes-alt");
    faIcon.add("fa-icon-sort-by-order");
    faIcon.add("fa-icon-sort-by-order-alt");
    faIcon.add("fa-icon-thumbs-up");
    faIcon.add("fa-icon-thumbs-down");
    faIcon.add("fa-icon-youtube-sign");
    faIcon.add("fa-icon-youtube");
    faIcon.add("fa-icon-xing");
    faIcon.add("fa-icon-xing-sign");
    faIcon.add("fa-icon-youtube-play");
    faIcon.add("fa-icon-dropbox");
    faIcon.add("fa-icon-stackexchange");
    faIcon.add("fa-icon-instagram");
    faIcon.add("fa-icon-flickr");
    faIcon.add("fa-icon-adn");
    faIcon.add("fa-icon-bitbucket");
    faIcon.add("fa-icon-bitbucket-sign");
    faIcon.add("fa-icon-tumblr");
    faIcon.add("fa-icon-tumblr-sign");
    faIcon.add("fa-icon-long-arrow-down");
    faIcon.add("fa-icon-long-arrow-up");
    faIcon.add("fa-icon-long-arrow-left");
    faIcon.add("fa-icon-long-arrow-right");
    faIcon.add("fa-icon-apple");
    faIcon.add("fa-icon-windows");
    faIcon.add("fa-icon-android");
    faIcon.add("fa-icon-linux");
    faIcon.add("fa-icon-dribbble");
    faIcon.add("fa-icon-skype");
    faIcon.add("fa-icon-foursquare");
    faIcon.add("fa-icon-trello");
    faIcon.add("fa-icon-female");
    faIcon.add("fa-icon-male");
    faIcon.add("fa-icon-gittip");
    faIcon.add("fa-icon-sun");
    faIcon.add("fa-icon-moon");
    faIcon.add("fa-icon-archive");
    faIcon.add("fa-icon-bug");
    faIcon.add("fa-icon-vk");
    faIcon.add("fa-icon-weibo");
    faIcon.add("fa-icon-renren");

  }

  @RequestMapping(method = RequestMethod.GET)
  //@RequestMapping(value = {"/"}, method = RequestMethod.GET)
  public String index(){
    return "/admin/index";
  }

  @RequestMapping(value = "/aboutme/newblockbiography", method = RequestMethod.GET)
  public String newBiographyBlock(Model model){
    model.addAttribute("biography", new AboutMe());
    model.addAttribute("faIcons", faIcon);

    return "/admin/aboutme/newblockbiography";
  }

  @RequestMapping(value = "/aboutme/addblockbiography", method = RequestMethod.POST)
  public String addBiographyBlock(@Valid AboutMe aboutMe, BindingResult bindingResult, Model model){
    System.out.println("/aboutme/addblockbiography -> " + aboutMe);
    if (!bindingResult.hasErrors()){
      adminService.addAboutMeBlock(aboutMe);
    }else {
      List<ObjectError> errors = bindingResult.getAllErrors();
    }

    model.addAttribute("biography", new AboutMe());

    return "redirect:/admin/aboutme/biography";
  }

  @RequestMapping(value = "/aboutme/biography", method = RequestMethod.GET)
  public String getBiography(Model model) {

  /*  CommonEntity biograpfy = adminService.getBiography();
    model.addAttribute("biograpfy", biograpfy);*/


    List<AboutMe> allBlocksAboutMe = adminService.getAllBlocksAboutMe();

    model.addAttribute("biografyBlocks", allBlocksAboutMe);
    model.addAttribute("faIcons", faIcon);

    return "admin/aboutme/biography";
  }

  @RequestMapping(value = {"/aboutme/biography/{id:[0-9]+}/edit"}, method = RequestMethod.POST)
  @ResponseBody
  public String updateBiographyBlock(@ModelAttribute @Valid AboutMe aboutMe,
                                     @PathVariable Integer id,
                                     BindingResult bindingResult,
                                     Model model){

    AboutMe am = adminService.getAboutMeBlockByID(id);
    System.out.println("updateBiographyBlock.am -> " + am);
    if (!bindingResult.hasErrors()){
      System.out.println("updateBiographyBlock.aboutMe -> " + aboutMe);
      am.setText(aboutMe.getText());
      am.setTitle(aboutMe.getTitle());
      am.setImageName(aboutMe.getImageName());
      am.setActive(aboutMe.isActive());

      adminService.updateAboutMeBlock(am);
      return "OK";
    }

    return "ERROR";
  }

  @RequestMapping(value = {"/aboutme/biography/edit"}, method = RequestMethod.POST)
  public String updateBiography(@ModelAttribute @Valid CommonEntity biography, BindingResult bindingResult, Model model) {

    CommonEntity b = adminService.getBiography();

    if (!bindingResult.hasErrors()) {
      b.setText(biography.getText());

      adminService.updateBiography(b);
    }

    return "redirect:/admin/biography";
  }

  @RequestMapping(value = "contacts/all", method = RequestMethod.GET)
  public String getContacts(Model model) {
  /*  CommonEntity contacts = adminService.getContacts();
    model.addAttribute("contacts", contacts);*/

    List<Contact> allBlocksContact = adminService.getAllBlocksContact();
    model.addAttribute("contactBlocks", allBlocksContact);
    model.addAttribute("faIcons", faIcon);

    return "/admin/contacts/contacts";
  }

  @RequestMapping(value = {"/contact/{id:[0-9]+}/edit"}, method = RequestMethod.POST)
  @ResponseBody
  public String updateContactBlock(@ModelAttribute @Valid Contact contact,
                                   @PathVariable Integer id,
                                   BindingResult bindingResult,
                                   Model model){

    System.out.println("contact -> " + contact);
    Contact contactByID = adminService.getContactByID(id);
    if (!bindingResult.hasErrors()) {
      contactByID.setTitle(contact.getTitle());
      contactByID.setText(contact.getText());
      contactByID.setIcon(contact.getIcon());
      contactByID.setActive(contact.isActive());

      adminService.updateContactBlock(contactByID);

      return "OK";
    }
    return "ERROR";
  }

  @RequestMapping(value = "/contact/newblockcontact", method = RequestMethod.GET)
  public String newContactBlock(Model model){
    model.addAttribute("contact", new Contact());
    model.addAttribute("faIcons", faIcon);

    return "/admin/contacts/newblockcontact";
  }

  @RequestMapping(value = "/contact/addblockcontact", method = RequestMethod.POST)
  public String addContactBlock(@Valid Contact contact, BindingResult bindingResult, Model model){
    if (!bindingResult.hasErrors()){
      adminService.addContactBlock(contact);
    }else {
      List<ObjectError> errors = bindingResult.getAllErrors();
    }

    model.addAttribute("contact", new Contact());

    return "redirect:/admin/contacts/all";
  }

  @RequestMapping(value = {"contacts/edit"}, method = RequestMethod.POST)
  public String updateContacts(@ModelAttribute @Valid CommonEntity contact, BindingResult bindingResult, Model model) {
    CommonEntity c = adminService.getContacts();

    if (!bindingResult.hasErrors()) {
      c.setText(contact.getText());
      adminService.updateContacts(c);
    }

      return "redirect:/admin/contacts";
  }

  @RequestMapping(value= {"/blog/newarticle"}, method = RequestMethod.GET)
  public String showArticle(Model model){

    model.addAttribute("article", new Post());

    List<CategoryPost> categories = adminService.getAllCategoriesPost();

   model.addAttribute("postcategories", categories);
    return "admin/blog/newarticle";
  }

  @RequestMapping(value = "/blog/allarticles", method = RequestMethod.GET)
  public String allArticles(Model model, @RequestParam(defaultValue = "1") int page){
  //  model.addAttribute("articles", adminService.getAllArticles());
    PageRequest pageNum = new PageRequest(page-1, 10, Sort.Direction.DESC, "createdAt");
    Page<Post> result = adminService.getAllPosts(pageNum);

    List<Post> posts = result.getContent();
    for (Post p: posts)
      System.out.println("posts-> " + p);

    model.addAttribute("post_count", result.getTotalElements());

    return renderLists(result, model);
  }

  @RequestMapping(value = "/blog/allarticles1", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<Post> allArticleTest(){
    PageRequest pageNum = new PageRequest(1-1, 10, Sort.Direction.DESC, "createdAt");
    Page<Post> result = adminService.getAllPosts(pageNum);

    List<Post> posts = result.getContent();

    List<String> res = new ArrayList<String>();

    for (Post p: posts){
      res.add(p.getTitle());
    }

    return posts;

  }

  @RequestMapping(value = "/lineOnLink/search", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<SearchResult> remoteFindArchiveAjax(@RequestParam String q){

    System.out.println("remoteFindArchiveAjax -> " + q);


    List<SearchResult> result = archiveService.getArchiveBySearch(q, 1);

    for (SearchResult archive : result) {
      System.out.println("remoteFindArticleAjax -> " + archive);
    }
    return result;
  }

  @RequestMapping(value = "/postsLink/search", method = RequestMethod.POST)
//  @RequestMapping(value = "/blog/search", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<Post> remoteFindArticleAjax(@RequestParam String q){
//  public List<Post> remoteFindArticleAjax(@RequestBody String q){

    System.out.println("remoteFindArticleAjax -> " + q);


    List<Post> result = postService.getPostBySearch(q, 1);

    for (Post post : result) {
      System.out.println("remoteFindArticleAjax -> " + post);
    }
    return result;
  }

  @RequestMapping(value="/blog/addarticle", method = RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.OK)
  public String addArticle(@Valid Post post, BindingResult bindingResult,
                           MultipartHttpServletRequest request){
    //model.addAttribute("categories", categoryService.getAllArticles());
    System.out.println("AdminController.addArticle");
      System.out.println(post);

    String dir = request.getSession().getServletContext().getRealPath("/");

    dir += "\\resources\\images\\blog\\";

    if (!bindingResult.hasErrors()) {
      Post p = null;
      try {
        p = adminService.addArticle(post);
      }catch(Exception e){
        System.out.println("ERROR!!!! adminService.addArticle");
        e.printStackTrace();
      }


      Map<String, MultipartFile> fileMap = request.getFileMap();

      System.out.println("fileMap.size ->" + fileMap.size());

      List<Image> uploadImages = new ArrayList<Image>();

      String rootPath = System.getProperty("catalina.home");
      File imagesDir = new File(rootPath + File.separator + "images" + File.separator + "blog" + File.separator);


      for (MultipartFile multipartFile : fileMap.values()){
        Image imageInfo = getUploadImageInfo(multipartFile, p);

        if (!imagesDir.exists()){
          imagesDir.mkdir();
        }

        System.out.println("imagesDir = " + imagesDir);

      //  String location = saveFileToLocalDisk(multipartFile, imagesDir.getAbsolutePath());

      //  imageInfo.setLocation(location);

        saveFileInfoToDataBase(imageInfo);

    //    saveFileToLocalDisk(multipartFile, imagesDir.getAbsolutePath(), imageInfo);
        System.out.println(imageInfo);

      }

    } else {
      List<ObjectError> errors = bindingResult.getAllErrors();
      for(ObjectError error : errors)
        System.out.println("error ->" + error);
    }

    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", "/admin/blog/allarticles");

    return "redirect:/admin/blog/allarticles";
  //  return new ResponseEntity<byte[]>(null, headers, HttpStatus.OK);
  }

  private Image getUploadImageInfo(MultipartFile multipartFile, Post p) {
    String rootPath = System.getProperty("catalina.home");
    File imagesDir = new File(rootPath + File.separator + "images" + File.separator + "blog");

    Image imageInfo =  new Image();
    imageInfo.setName(multipartFile.getOriginalFilename());
    imageInfo.setSize(multipartFile.getSize());
    imageInfo.setType(multipartFile.getContentType());
    imageInfo.setCategory(1);
    imageInfo.setIdEntity(p.getId());
    //imageInfo.setLocation(imagesDir + File.separator + multipartFile.getOriginalFilename());
    imageInfo.setLocation("/images/blog/" + multipartFile.getOriginalFilename());


//    imageInfo.setLocation(".." + File.separator + ".." + File.separator + "images" + File.separator + "blog" + File.separator + multipartFile.getOriginalFilename());
//    imageInfo.setLocation("/home/bochdm/images/blog/"  + multipartFile.getOriginalFilename());

    return imageInfo;
  }

  private void saveFileInfoToDataBase(Image uploadImage){
    imageService.uploadFileInfo(uploadImage);

  }

  private String saveFileToLocalDisk(MultipartFile multipartFile, String outputFile, Image imageInfo) {

    try {
      String rootPath = System.getProperty("catalina.home");
      File imagesDir = new File(rootPath + File.separator + "domain" + File.separator + "tkhostov.com" + File.separator + "images" + File.separator + "blog");

      FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(imagesDir + File.separator + imageInfo.getName()));
    } catch (IOException e) {
      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
    }

    return outputFile + File.separator + multipartFile.getOriginalFilename();
  }

  private String getOutputFileName(MultipartFile multipartFile) {
    return "/var/images/blog/" + multipartFile.getOriginalFilename();
  }

  @RequestMapping(value = "/video/allarchivevideo", method = RequestMethod.GET)
  public String allArchive(Model model, @RequestParam(defaultValue = "1") int page){
    PageRequest pageNum = new PageRequest(page - 1, 10, Sort.Direction.DESC, "createdAt");
    Page<Archive> result = adminService.getAllArchive(pageNum);

    model.addAttribute("archivevideo", result);
    model.addAttribute("paginationInfo", new PaginationInfo(result));

    return "admin/video/allarchivevideo";
  }

  @RequestMapping(value= {"/video/addarchivevideo"}, method = RequestMethod.GET)
  public String newArhiveVideo(Model model){

    model.addAttribute("video", new Archive());

    return "admin/video/addarchivevideo";
  }

  @RequestMapping(value = "/video/addarchivevideo", method = RequestMethod.POST)
  public String addArchiveVideo(@Valid Archive archive, BindingResult bindingResult, Model model){
    if (!bindingResult.hasErrors()){
      adminService.addArchiveVideo(archive);
    } else {
      List<ObjectError> errors = bindingResult.getAllErrors();
    }

    return "redirect:/admin/video/addarchivevideo";
  }

  @RequestMapping(value = "/allanswers", method = RequestMethod.GET)
  public String getAllAnswer(Model model, @RequestParam(defaultValue = "1") int page){
    PageRequest pageNum = new PageRequest(page-1, 10, Sort.Direction.DESC, "date");
    Page<Answer> result = adminService.getAllAnswers(pageNum);

    List<Answer> ans = result.getContent();
   /* for (Answer a : ans)
      System.out.println(a);*/

    model.addAttribute("answer_count", result.getTotalElements());

    model.addAttribute("answers", result);
    model.addAttribute("paginationInfo", new PaginationInfo(result));
    return "admin/allquestions";
  }

  @RequestMapping(value = "/question/{id:[0-9]+}/newreply", method = RequestMethod.GET)
  public String reply(Model model, @PathVariable Long id){
    Question q = adminService.getQuestion(id);
    Answer a = new Answer();
    a.setQuestion(q);
    model.addAttribute("answer", a);
    return "admin/reply";
  }

  @RequestMapping(value = "/question/{id:[0-9]+}/delete", method = {RequestMethod.GET})
  public String deleteQuestion(@PathVariable Long id, Model model){

    Question q = adminService.getQuestion(id);
    adminService.deleteQuestion(q);
    return "redirect:/admin/unanswered";
  }

  @RequestMapping(value = "/newreply/{id:[0-9]+}", method = RequestMethod.POST)
  public String newReply(@ModelAttribute Answer answer, @PathVariable Long id){
    Question q = adminService.getQuestion(id);
    answer.setQuestion(q);
    adminService.addReply(answer);
    return "redirect:/admin/unanswered";
  }


  @RequestMapping(value = "/unanswered", method = RequestMethod.GET)
  public String getAllUnansweredQuestions(Model model, @RequestParam(defaultValue = "1") int page){
    PageRequest pageNum = new PageRequest(page-1, 10, Sort.Direction.DESC, "date");
    Page<Question> result = adminService.getAllUnansweredQuestions(pageNum);

    System.out.println("admin:unanswered");
    List<Question> qq = result.getContent();
   /* for (Question q : qq)
      System.out.println("q -> " + q);
*/
    model.addAttribute("unanswered_count", result.getTotalElements());

    model.addAttribute("questions", result);
    model.addAttribute("paginationInfo", new PaginationInfo(result));

    return "admin/unanswered";
  }

  @RequestMapping(value = "/answer/{id:[0-9]+}/edit", method = {RequestMethod.GET})
  public String showAnswer(@PathVariable Long id, Model model){

    Answer answer = adminService.getAnswer(id);
    model.addAttribute("answer", answer);
    return "redirect:/admin/allanswers";
  }

  @RequestMapping(value = "/answer/{id:[0-9]+}/edit", method = {RequestMethod.POST})
  public String editAnswer(@PathVariable Long id, @ModelAttribute @Valid Answer answer, BindingResult bindingResult, Model model){

    Answer ans = adminService.getAnswer(id);
    System.out.println("editAnswer -> " + ans);
    if (!bindingResult.hasErrors()){
      ans.setAnswerText(answer.getAnswerText());
      adminService.updateAnswer(ans);
    }

    return "redirect:/admin/allanswers";
  }

  @RequestMapping(value = "/answer/{id:[0-9]+}/delete", method = {RequestMethod.GET})
  public String deleteAnswer(@PathVariable Long id, Model model){

    Answer answer = adminService.getAnswer(id);
    adminService.deleteAnswer(answer);

    return "redirect:/admin/allanswers";
  }

  @RequestMapping(value = "/answer/{id:[0-9]+}/publish", method = {RequestMethod.GET})
  public String publishAnswer(@PathVariable Long id, @ModelAttribute @Valid Answer answer, BindingResult bindingResult, Model model){
    Answer ans = adminService.getAnswer(id);
    System.out.println( "publishAnswer ->" + ans);

    if (!bindingResult.hasErrors()) {
      ans.setIsPublic(true);
      adminService.updateAnswer(ans);
    }
    return "redirect:/admin/allanswers";
  }

  @RequestMapping(value = "/answer/{id:[0-9]+}/unpublish", method = {RequestMethod.GET})
  public String unPublishAnswer(@PathVariable Long id, @ModelAttribute @Valid Answer answer, BindingResult bindingResult, Model model){
    Answer ans = adminService.getAnswer(id);
    System.out.println( "unPublishAnswer ->" + ans);
    if (!bindingResult.hasErrors()) {
      ans.setIsPublic(false);
      adminService.updateAnswer(ans);
    }
    return "redirect:/admin/allanswers";
  }

  @RequestMapping(value = "/blog/post/{id:[0-9]+}/edit", method = {RequestMethod.GET})
  public String findPost(@PathVariable Long id, Model model){
    Post post = adminService.getPost(id);
    model.addAttribute("post", post);
    return "admin/blog/editpost";
  }

  @RequestMapping(value = "/blog/post/{id:[0-9]+}/edit", method = {RequestMethod.POST})
  public String editPost(@PathVariable Long id, @ModelAttribute @Valid Post post, BindingResult bindingResult, Model model){
    Post p = adminService.getPost(id);
    if (!bindingResult.hasErrors()){
      p.setTitle(post.getTitle());
      p.setAuthor(post.getAuthor());
      p.setSummary(post.getSummary());
      p.setContent(post.getContent());
      adminService.updatePost(p);
    }

    //allArticles(model, 1);
    return "redirect:/admin/blog/allarticles";
  }

  @RequestMapping(value = "/blog/post/{id:[0-9]+}/delete", method = {RequestMethod.GET})
  public String deletePost(@PathVariable Long id, Model model){

    Post post = adminService.getPost(id);
    adminService.deletePost(post);
    return "redirect:/admin/blog/allarticles";
  }
  @RequestMapping(value = "/video/archivevideo/{id:[0-9]+}/edit", method = {RequestMethod.GET})
  public String findArchiveVideo(@PathVariable Long id, Model model) {
    Archive archive = adminService.getArchiveVideo(id);
    model.addAttribute("arhivevideo", archive);
    return "admin/video/editarchivevideo";
  }

  @RequestMapping(value = "/video/archivevideo/{id:[0-9]+}/edit", method = {RequestMethod.POST})
  public String editArchiveVideo(@PathVariable Long id,@ModelAttribute @Valid Archive archive, BindingResult bindingResult, Model model) {
    Archive a = adminService.getArchiveVideo(id);
    if (!bindingResult.hasErrors()){
      a.setContent(archive.getContent());
      a.setSummary(archive.getSummary());
      a.setTitle(archive.getTitle());
      a.setUrl(archive.getUrl());
      adminService.updateArchiveVideo(a);
    }

    return "redirect:/admin/video/allarchivevideo";
  }

  @RequestMapping(value = "/video/archivevideo/{id:[0-9]+}/delete", method = {RequestMethod.GET})
  public String deleteArchiveVideo(@PathVariable Long id, Model model){
    Archive archive = adminService.getArchiveVideo(id);
    adminService.deleteArchiveVideo(archive);
    return "redirect:/admin/video/allarchivevideo";
  }

  private String renderLists(Page<Post> page, Model model){

    System.out.println("admin: renderLists");

    model.addAttribute("articles", page);
    model.addAttribute("paginationInfo", new PaginationInfo(page));

    return "admin/blog/allarticles";
  }

  @RequestMapping(value = "/startpage/allcarousel", method = RequestMethod.GET)
  public String allCarousel(Model model){
    List<Carousel> result = adminService.getAllCarousel();

    model.addAttribute("carousel", result);

    return "admin/startpage/allcarousel";
  }

  private Map<String, String> getAnimatedClasses(){
    Map<String, String> animateClasses = new HashMap<String, String>();

    animateClasses.put("fadeInLeftBig", "Анимация влево");
    animateClasses.put("fadeInRightBig", "Анимация вправо");
    animateClasses.put("fadeInUpBig", "Анимация вверх");
    animateClasses.put("fadeInDownBig", "Анимация вниз");

    return animateClasses;
  }

  @RequestMapping(value= "/startpage/newcarousel", method = RequestMethod.GET)
  public String newCarousel(Model model){


    model.addAttribute("animateClasses", getAnimatedClasses());

    Map<String, String> linkTypes = new HashMap<>();
    linkTypes.put("extLink", "Внешняя ссылка");
    linkTypes.put("postsLink", "Статья");
    linkTypes.put("lineOnLink", "Прямая линия");

    model.addAttribute("linkTypes", linkTypes);

    model.addAttribute("carousel", new Carousel());

    return "admin/startpage/newcarousel";
  }

  @RequestMapping(value = "/startpage/addcarousel", method = RequestMethod.POST)
  public String addCarousel(@Valid Carousel carousel,
                            BindingResult bindingResult,
                            Model model,
//                            @RequestParam String testCarousel,
                            HttpServletRequest request){

//    System.out.println("testCarousel -> " + testCarousel);
    Map paramMap = request.getParameterMap();
    Enumeration en = request.getParameterNames();

    String linkType = request.getParameter("linkType");
    System.out.println("linkType -> " + linkType);

/*    while(en.hasMoreElements()){
      String p = (String) en.nextElement();
      System.out.println("param -> " + p);
      System.out.println("value -> " + request.getParameter(p));
    }*/

    String resultLink = "";
    switch (linkType){
      case "extLink":
        resultLink = request.getParameter("extLink");
        break;
      case "postsLink":
        long linkToPost = Long.parseLong(request.getParameter("postsLink"));
        resultLink = "/blog/" + linkToPost;
        break;
      case "lineOnLink":
        long lineOnLink = Long.parseLong(request.getParameter("lineOnLink"));
        resultLink = "/video/archive/" + lineOnLink;
        break;
    }

    if (!bindingResult.hasErrors()){
      carousel.setLink(resultLink);
      adminService.addCarousel(carousel);
    } else {
      List<ObjectError> errors = bindingResult.getAllErrors();
    }

    return "redirect:/admin/startpage/allcarousel";
  }

  @RequestMapping(value = "/startpage/carousel/{id:[0-9]+}/edit", method = {RequestMethod.GET})
  public String findCarousel(@PathVariable Long id, Model model){

    model.addAttribute("animateClasses", getAnimatedClasses());

    Map<String, String> linkTypes = new HashMap<>();
    linkTypes.put("extLink", "Внешняя ссылка");
    linkTypes.put("postsLink", "Статья");
    linkTypes.put("lineOnLink", "Прямая линия");

    model.addAttribute("linkTypes", linkTypes);

    Carousel carousel = adminService.getCarousel(id);
    model.addAttribute("carousel", carousel);

    String carouselLink = carousel.getLink();
    if (carouselLink != null) {
      if (carouselLink.contains("blog")) {
        model.addAttribute("carouselLinkType", "postsLink");
      }
      if (carouselLink.contains("video")) {
        model.addAttribute("carouselLinkType", "lineOnLink");
      }
      if (carouselLink.contains("http")) {
        model.addAttribute("carouselLinkType", "extLink");
      }
    }


    return "admin/startpage/editcarousel";
  }

  @RequestMapping(value = "/startpage/carousel/{id:[0-9]+}/edit", method = {RequestMethod.POST})
  public String editCarousel(@PathVariable Long id, @ModelAttribute @Valid Carousel archive, BindingResult bindingResult, Model model, HttpServletRequest request) {
    Carousel carousel = adminService.getCarousel(id);
    System.out.println("edit carousel post ->" + carousel);
    if (!bindingResult.hasErrors()){
      carousel.setContent(archive.getContent());
      carousel.setTitle(archive.getTitle());
      carousel.setContentClass(archive.getContentClass());
      carousel.setTitleClass(archive.getTitleClass());
      carousel.setLinkClass(archive.getLinkClass());

      String linkType = request.getParameter("linkType");

      String resultLink = "";
      switch (linkType){
        case "extLink":
          resultLink = request.getParameter("link");
          break;
        case "postsLink":
          long linkToPost = Long.parseLong(request.getParameter("postsLink"));
          System.out.println("linkToPost->" + linkToPost);
          break;
        case "lineOnLink":
          long lineOnLink = Long.parseLong(request.getParameter("lineOnLink"));
          resultLink = "/video/archive/" + lineOnLink;
          break;
      }

      carousel.setLink(resultLink);
      adminService.updateCarousel(carousel);
    }

    return "redirect:/admin/startpage/allcarousel";
  }

  @RequestMapping(value = "/startpage/carousel/{id:[0-9]+}/delete", method = {RequestMethod.GET})
  public String deleteCarousel(@PathVariable Long id, Model model){
    Carousel carousel = adminService.getCarousel(id);
    adminService.deleteCarousel(carousel);
    return "redirect:/admin/startpage/allcarousel";
  }

  @RequestMapping(value = "/startpage/carousel/{id:[0-9]+}/changeshow", method = {RequestMethod.POST})
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public String changeShowCarousel(@PathVariable Long id, @ModelAttribute @Valid Carousel carousel, HttpSession session){
    System.out.println("changeshow/id = " + carousel.toString());
    Carousel c = adminService.getCarousel(id);
    if (carousel != null) {
      c.setActive(carousel.getActive() ? false : true);
      adminService.updateCarousel(c);
      return "ok";
    }

    return "error";
 //   return new ResponseEntity<Object>(carousel, HttpStatus.OK);

  }
}