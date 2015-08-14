package com.hvost.admin;

import com.hvost.activepeople.Answer;
import com.hvost.activepeople.Question;
import com.hvost.archive.Archive;
import com.hvost.blog.CategoryPost;
import com.hvost.blog.Post;
import com.hvost.images.Image;
import com.hvost.startpage.Carousel;
import com.hvost.support.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

  @RequestMapping(method = RequestMethod.GET)
  //@RequestMapping(value = {"/"}, method = RequestMethod.GET)
  public String index(){

    return "/admin/index";
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

  @RequestMapping(value="/blog/addarticle", method = RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.OK)
  public String addArticle(@Valid Post post, BindingResult bindingResult,
                           MultipartHttpServletRequest request,
                           HttpServletResponse response, Model model){
    //model.addAttribute("categories", categoryService.getAllArticles());
    System.out.println("AdminController.addArticle");
      System.out.println(post);

    String dir = request.getSession().getServletContext().getRealPath("/");

    dir += "\\resources\\images\\blog\\";

    if (!bindingResult.hasErrors()) {
      Post p = adminService.addArticle(post);

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

        saveFileToLocalDisk(multipartFile, imagesDir.getAbsolutePath(), imageInfo);
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
    imageInfo.setLocation(imagesDir + File.separator + multipartFile.getOriginalFilename());
//    imageInfo.setLocation(".." + File.separator + ".." + File.separator + "images" + File.separator + "blog" + File.separator + multipartFile.getOriginalFilename());
//    imageInfo.setLocation("/home/bochdm/images/blog/"  + multipartFile.getOriginalFilename());

    return imageInfo;
  }

  private void saveFileInfoToDataBase(Image uploadImage){
    adminService.uploadFile(uploadImage);

  }

  private String saveFileToLocalDisk(MultipartFile multipartFile, String outputFile, Image imageInfo) {

    try {
      FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(imageInfo.getLocation()));
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
    System.out.println("AdminController:getAllAnswer");
    for (Answer a : ans)
      System.out.println(a);

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
    return "redirect:/admin/blog/allarticles";
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
    for (Question q : qq)
      System.out.println("q -> " + q);

    model.addAttribute("unanswered_count", result.getTotalElements());
    System.out.println("unanswered_count ->" + result.getTotalElements());

    model.addAttribute("questions", result);
    model.addAttribute("paginationInfo", new PaginationInfo(result));

    return "admin/unanswered";
  }

  @RequestMapping(value = "/answer/{id:[0-9]+}/edit", method = {RequestMethod.GET})
  public String showAnswer(@PathVariable Long id, Model model){

    Answer answer = adminService.getAnswer(id);
    System.out.println("AdminController:showAnswer = " + answer);
    model.addAttribute("answer", answer);
    return "redirect:/admin/allquestions";
  }

  @RequestMapping(value = "/answer/{id:[0-9]+}/edit", method = {RequestMethod.POST})
  public String editAnswer(@PathVariable Long id, @ModelAttribute @Valid Answer answer, BindingResult bindingResult, Model model){

    Answer ans = adminService.getAnswer(id);
    System.out.println("editAnswer -> " + ans);
    if (!bindingResult.hasErrors()){
      ans.setAnswerText(answer.getAnswerText());
      adminService.updateAnswer(ans);
    }

    return "redirect:/admin/allquestions";
  }

  @RequestMapping(value = "/answer/{id:[0-9]+}/publish", method = {RequestMethod.GET})
  public String publishAnswer(@PathVariable Long id, @ModelAttribute @Valid Answer answer, BindingResult bindingResult, Model model){
    Answer ans = adminService.getAnswer(id);
    if (!bindingResult.hasErrors()) {
      ans.setIsPublic(true);
      adminService.updateAnswer(ans);
    }
    return "redirect:/admin/allquestions";
  }

  @RequestMapping(value = "/answer/{id:[0-9]+}/unpublish", method = {RequestMethod.GET})
  public String unPublishAnswer(@PathVariable Long id, @ModelAttribute @Valid Answer answer, BindingResult bindingResult, Model model){
    Answer ans = adminService.getAnswer(id);
    if (!bindingResult.hasErrors()) {
      ans.setIsPublic(false);
      adminService.updateAnswer(ans);
    }
    return "redirect:/admin/allquestions";
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
    System.out.println("admin/editarchivevideo");
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

  @RequestMapping(value= "/startpage/newcarousel", method = RequestMethod.GET)
  public String newCarousel(Model model){

    model.addAttribute("carousel", new Carousel());

    return "admin/startpage/newcarousel";
  }

  @RequestMapping(value = "/startpage/addcarousel", method = RequestMethod.POST)
  public String addCarousel(@Valid Carousel carousel, BindingResult bindingResult, Model model){
    if (!bindingResult.hasErrors()){
      adminService.addCarousel(carousel);
    } else {
      List<ObjectError> errors = bindingResult.getAllErrors();
    }

    return "redirect:/admin/startpage/allcarousel";
  }

  @RequestMapping(value = "/startpage/carousel/{id:[0-9]+}/edit", method = {RequestMethod.GET})
  public String findCarousel(@PathVariable Long id, Model model){
    Carousel carousel = adminService.getCarousel(id);
    model.addAttribute("carousel", carousel);
    return "admin/startpage/editcarousel";
  }

  @RequestMapping(value = "/startpage/carousel/{id:[0-9]+}/edit", method = {RequestMethod.POST})
  public String editCarousel(@PathVariable Long id,@ModelAttribute @Valid Carousel archive, BindingResult bindingResult, Model model) {
    Carousel carousel = adminService.getCarousel(id);
    System.out.println("edit carousel post ->" + carousel);
    if (!bindingResult.hasErrors()){
      carousel.setContent(archive.getContent());
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
}
