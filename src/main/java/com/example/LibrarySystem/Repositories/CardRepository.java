package com.example.LibrarySystem.Repositories;

import com.example.LibrarySystem.Models.LibraryCard;
import org.h2.command.query.Select;
import org.hibernate.query.sqm.sql.FromClauseIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CardRepository extends JpaRepository<LibraryCard,Integer> {


}
