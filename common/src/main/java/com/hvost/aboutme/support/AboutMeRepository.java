package com.hvost.aboutme.support;

import com.hvost.aboutme.AboutMe;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by kseniaselezneva on 14/11/15.
 */
public interface AboutMeRepository extends JpaRepository<AboutMe, Integer> {
}
