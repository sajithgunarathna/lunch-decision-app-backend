package com.gov.lunchsession.repository;

import com.gov.lunchsession.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
