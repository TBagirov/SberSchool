package org.bagirov.seventeenthhw.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.bagirov.seventeenthhw.model.Recipe;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class RecipeRepositoryCustomImpl implements RecipeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Recipe> searchByName(String namePart) {
        String jpql = "SELECT DISTINCT r FROM Recipe r LEFT JOIN FETCH r.ingredients " +
                "WHERE LOWER(r.name) LIKE LOWER(:namePart)";
        return entityManager.createQuery(jpql, Recipe.class)
                .setParameter("namePart", "%" + namePart + "%")
                .getResultList();
    }


}

