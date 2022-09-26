package jmp.cloud.bank.impl;

import com.jmp.dto.*;
import jmp.bank.api.*;

import java.sql.*;

public class BankImpl implements Bank {
    static final String Url = "jdbc:mysql://localhost:3306/bankdata?useTimezone=true&serverTimezone=UTC";

    PreparedStatement ps;
    ResultSet rs;
    BankCard card;

    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {
        String cardNumber = Double.toString(Math.random());
        cardNumber = cardNumber.substring(2,10);

        if (cardType == BankCardType.CREDIT)
            card = new CreditBankCard(cardNumber, user);
        else
            card = new DebitBankCard(cardNumber, user);

        try (Connection con = BankImpl.getConnection();) {
            ps = con.prepareStatement("insert into bank_card (card_number, user_id) values(?,?)");
            ps.setString(1, card.getNumber());
            ps.setInt(2, card.getUser().getId());
            int result = ps.executeUpdate();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return card;
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(Url, "root", "root");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return connection;
    }
}
