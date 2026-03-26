package app;

import java.sql.*;
import java.util.Scanner;

public class ExpenseSystem {

    Scanner sc = new Scanner(System.in);
    Connection con = DBConnection.getConnection();

    // 1. ADD EXPENSE
    public void addExpense() {
        try {
            System.out.print("Enter amount: ");
            double amt = sc.nextDouble();

            System.out.print("Enter category: ");
            String cat = sc.next();

            String q = "INSERT INTO expenses(amount,category,date) VALUES(?,?,CURDATE())";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setDouble(1, amt);
            ps.setString(2, cat);
            ps.executeUpdate();

            System.out.println("Expense added!");
        } catch (Exception e) {
            System.out.println("Error adding expense");
        }
    }

    // 2. VIEW EXPENSES
    public void viewExpenses() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM expenses");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("expense_id") + " | " +
                    rs.getDouble("amount") + " | " +
                    rs.getString("category") + " | " +
                    rs.getDate("date")
                );
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    // 3. CATEGORY-WISE SPENDING
    public void categoryWise() {
        try {
            String q = "SELECT category, SUM(amount) FROM expenses GROUP BY category";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);

            while (rs.next()) {
                System.out.println(
                    rs.getString(1) + " → " + rs.getDouble(2)
                );
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    // 4. MONTHLY SUMMARY
    public void monthlySummary() {
        try {
            String q = "SELECT SUM(amount) FROM expenses WHERE MONTH(date)=MONTH(CURDATE())";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(q);

            if (rs.next()) {
                System.out.println("Total this month: " + rs.getDouble(1));
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    // 5. SET BUDGET
    public void setBudget() {
        try {
            System.out.print("Enter monthly budget: ");
            double b = sc.nextDouble();

            String q = "UPDATE budget SET monthly_limit=? WHERE id=1";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setDouble(1, b);
            ps.executeUpdate();

            System.out.println("Budget set!");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    // 6. CHECK BUDGET
    public void checkBudget() {
        try {
            // get total
            String q1 = "SELECT SUM(amount) FROM expenses WHERE MONTH(date)=MONTH(CURDATE())";
            Statement st = con.createStatement();
            ResultSet rs1 = st.executeQuery(q1);

            double total = 0;
            if (rs1.next()) total = rs1.getDouble(1);

            // get budget
            String q2 = "SELECT monthly_limit FROM budget WHERE id=1";
            ResultSet rs2 = st.executeQuery(q2);

            double budget = 0;
            if (rs2.next()) budget = rs2.getDouble(1);

            System.out.println("Spent: " + total);
            System.out.println("Budget: " + budget);

            if (total > budget) {
                System.out.println("Status: Exceeded ⚠️");
            } else {
                System.out.println("Status: Within limit ✅");
            }

        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    // 7. DELETE EXPENSE
    public void deleteExpense() {
        try {
            System.out.print("Enter expense ID: ");
            int id = sc.nextInt();

            String q = "DELETE FROM expenses WHERE expense_id=?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Deleted!");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
}