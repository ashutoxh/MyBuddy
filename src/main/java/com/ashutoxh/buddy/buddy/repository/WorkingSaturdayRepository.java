package com.ashutoxh.buddy.buddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ashutoxh.buddy.buddy.entity.WorkingSaturday;

@Repository
public interface WorkingSaturdayRepository extends JpaRepository<WorkingSaturday, Integer> {
}
