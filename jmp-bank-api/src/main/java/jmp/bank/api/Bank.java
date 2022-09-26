package jmp.bank.api;
import com.jmp.dto.*;

public interface Bank {
    BankCard createBankCard(User user,BankCardType cardType);
}
