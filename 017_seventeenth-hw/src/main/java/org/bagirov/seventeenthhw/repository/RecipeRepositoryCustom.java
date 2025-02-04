package org.bagirov.seventeenthhw.repository;


import org.bagirov.seventeenthhw.model.Recipe;
import java.util.List;

public interface RecipeRepositoryCustom {
    List<Recipe> searchByName(String namePart);
}
