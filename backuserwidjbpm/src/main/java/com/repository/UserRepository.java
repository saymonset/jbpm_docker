package com.repository;
import com.dao.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by simon on 21/10/19.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {}