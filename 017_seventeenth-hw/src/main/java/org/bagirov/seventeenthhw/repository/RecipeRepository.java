package org.bagirov.seventeenthhw.repository;

import org.bagirov.seventeenthhw.model.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long>, RecipeRepositoryCustom {

    // поиск по названию, нужен для корректного удаления по названию
    Optional<Recipe> findByName(String name);
}
