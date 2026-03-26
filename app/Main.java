package app;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ExpenseSystem es = new ExpenseSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1.Add Expense\n2.View Expenses\n3.Category-wise Spending\n4.Monthly Summary\n5.Set Budget\n6.Check Budget\n7.Delete Expense\n8.Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1: es.addExpense(); break;
                case 2: es.viewExpenses(); break;
                case 3: es.categoryWise(); break;
                case 4: es.monthlySummary(); break;
                case 5: es.setBudget(); break;
                case 6: es.checkBudget(); break;
                case 7: es.deleteExpense(); break;
                case 8:
                    sc.close();
                    System.exit(0);
                default: System.out.println("Invalid choice");
            }
        }
    }
}