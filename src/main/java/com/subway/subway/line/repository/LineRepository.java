package com.subway.subway.line.repository;

import com.subway.subway.line.domian.Line;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, Long> {
}
