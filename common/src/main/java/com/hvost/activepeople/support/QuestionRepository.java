package com.hvost.activepeople.support;

import com.hvost.activepeople.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 * @author kseniaselezneva
 */

public interface QuestionRepository extends JpaRepository<Question, Long> {

 // Page<Questions> findByAnswerId(Answer isPublic, Pageable pageable);

  //@Query("SELECT q FROM Questions q INNER JOIN Answer a INNER JOIN CategoryQuestion cq WHERE q  and a.isPublic = 1")
  //@Query("SELECT q FROM Questions q WHERE q.answerId.isPublic = 1")
 // Page<Questions> findByAnswerId(Pageable pageable);

//  @Query(value = "SELECT q FROM Question q INNER JOIN Answer a WHERE q.id = a.qst_sqt_id", nativeQuery = true)

  /**
   * Список всех неотвеченных вопросов
   * @param pageable страница
   * @return Список всех вопросов, на которые еще не получен ответ
   */
  @Query(value = "SELECT q FROM Question q WHERE q NOT IN (SELECT a.question FROM Answer a)")
  Page<Question> findAllUnswered(Pageable pageable);

  /**
   * Поиск всех "видимых" неотвеченных вопросов, т.е. модерированные вопросы, которые можно показывать пользователям
   * Флаг видимости может поставить только администратор портала.
   *
   * @param pageable Страница
   * @param type
   * @return Список видимых вопросов, на которые еще не получен ответ
   */
  @Query(value = "SELECT q FROM Question q WHERE q.visible = true And q.type = :type AND q NOT IN (SELECT a.question FROM Answer a)")
  Page<Question> findVisibleUnswered(Pageable pageable,@Param("type") int type);
}
