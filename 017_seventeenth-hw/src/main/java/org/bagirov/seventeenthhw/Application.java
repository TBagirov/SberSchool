package org.bagirov.seventeenthhw;

import org.bagirov.seventeenthhw.model.Ingredient;
import org.bagirov.seventeenthhw.model.Recipe;
import org.bagirov.seventeenthhw.service.RecipeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final RecipeService recipeService;

    public Application(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Добавить рецепт");
            System.out.println("2. Найти рецепт");
            System.out.println("3. Удалить рецепт");
            System.out.println("4. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // поглощаем перевод строки

            switch (choice) {
                case 1:
                    System.out.print("\nВведите название рецепта: ");
                    String name = scanner.nextLine();
                    List<Ingredient> ingredients = new ArrayList<>();

                    while (true) {
                        System.out.print("\nВведите ингредиент (или пустую строку для завершения): ");
                        String ingName = scanner.nextLine();
                        if (ingName.isEmpty()) break;
                        System.out.print("Введите количество: ");
                        String amount = scanner.nextLine();
                        ingredients.add(new Ingredient(ingName, amount));
                    }

                    Recipe savedRecipe = recipeService.addRecipe(name, ingredients);
                    System.out.println("\nРецепт добавлен с id: " + savedRecipe.getId());
                    break;

                case 2:
                    System.out.print("\nВведите название или часть названия: ");
                    String query = scanner.nextLine();
                    List<Recipe> recipes = recipeService.searchRecipes(query);
                    if (recipes.isEmpty()) {
                        System.out.println("\nРецептов не найдено.");
                    } else {
                        recipes.forEach(r -> {
                            System.out.println("\nID: " + r.getId() + ", Название: " + r.getName());
                            r.getIngredients().forEach(i ->
                                    System.out.println(" - " + i.getName() + ": " + i.getAmount())
                            );
                        });
                    }
                    break;

                case 3:
                    System.out.print("\nВведите название рецепта для удаления: ");
                    String nameRec = scanner.next();
                    scanner.nextLine();
                    boolean flag = recipeService.deleteRecipe(nameRec);
                    System.out.println(flag ? "\nРецепт удалён." : "\nРецепта с таким названием нет");
                    break;

                case 4:
                    System.out.println("\nВыход...");
                    return;

                default:
                    System.out.println("\nНеверный ввод, попробуйте снова.");
            }
        }
    }
}
