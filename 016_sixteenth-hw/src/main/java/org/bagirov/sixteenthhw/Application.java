package org.bagirov.sixteenthhw;

import org.bagirov.sixteenthhw.model.Ingredient;
import org.bagirov.sixteenthhw.service.RecipeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
            System.out.println("1. Добавить рецепт");
            System.out.println("2. Найти рецепт");
            System.out.println("3. Удалить рецепт");
            System.out.println("4. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Введите название рецепта: ");
                    String name = scanner.nextLine();
                    List<Ingredient> ingredients = new ArrayList<>();

                    while (true) {
                        System.out.print("Введите ингредиент (или пустую строку для завершения): ");
                        String ingName = scanner.nextLine();
                        if (ingName.isEmpty()) break;
                        System.out.print("Введите количество: ");
                        String amount = scanner.nextLine();
                        ingredients.add(new Ingredient(ingName, amount));
                    }

                    recipeService.addRecipe(name, ingredients);
                    break;

                case 2:
                    System.out.print("Введите название или часть названия: ");
                    String query = scanner.nextLine();
                    recipeService.searchRecipes(query);
                    break;

                case 3:
                    System.out.print("Введите название рецепта для удаления: ");
                    String delName = scanner.nextLine();
                    recipeService.deleteRecipe(delName);
                    break;

                case 4:
                    System.out.println("Выход...");
                    return;

                default:
                    System.out.println("Неверный ввод, попробуйте снова.");
            }
        }
    }
}
