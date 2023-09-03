package by.BackendTestTaskClevertec;

import by.BackendTestTaskClevertec.controller.BankSystemController;
import by.BackendTestTaskClevertec.controller.BankSystemControllerImpl;
import by.BackendTestTaskClevertec.exception.ExceptionHandler;
import by.BackendTestTaskClevertec.exception.ExceptionHandlerImpl;
import by.BackendTestTaskClevertec.facade.FacadeService;
import by.BackendTestTaskClevertec.facade.FacadeServiceImpl;
import by.BackendTestTaskClevertec.model.User;
import by.BackendTestTaskClevertec.repository.BankAccountRepository;
import by.BackendTestTaskClevertec.repository.BankRepository;
import by.BackendTestTaskClevertec.repository.TransactionRepository;
import by.BackendTestTaskClevertec.repository.UserRepository;
import by.BackendTestTaskClevertec.repository.impl.BankAccountRepositoryImpl;
import by.BackendTestTaskClevertec.repository.impl.BankRepositoryImpl;
import by.BackendTestTaskClevertec.repository.impl.TransactionRepositoryImpl;
import by.BackendTestTaskClevertec.repository.impl.UserRepositoryImpl;
import by.BackendTestTaskClevertec.service.BankAccountService;
import by.BackendTestTaskClevertec.service.BankService;
import by.BackendTestTaskClevertec.service.TransactionService;
import by.BackendTestTaskClevertec.service.UserService;
import by.BackendTestTaskClevertec.service.impl.BankAccountServiceImpl;
import by.BackendTestTaskClevertec.service.impl.BankServiceImpl;
import by.BackendTestTaskClevertec.service.impl.TransactionServiceImpl;
import by.BackendTestTaskClevertec.service.impl.UserServiceImpl;
import by.BackendTestTaskClevertec.writer.AccountStatementWriter;
import by.BackendTestTaskClevertec.writer.CheckWriter;
import by.BackendTestTaskClevertec.writer.ConsoleWriter;
import by.BackendTestTaskClevertec.writer.impl.AccountStatementWriterImpl;
import by.BackendTestTaskClevertec.writer.impl.CheckWriterImpl;
import by.BackendTestTaskClevertec.writer.impl.ConsoleWriterImpl;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {

    public static void main(String[] args) {

        TransactionRepository transactionRepository = new TransactionRepositoryImpl();
        BankRepository bankRepository = new BankRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();
        BankAccountRepository bankAccountRepository = new BankAccountRepositoryImpl();
        TransactionService transactionService = new TransactionServiceImpl(transactionRepository);
        UserService userService = new UserServiceImpl(userRepository);
        BankService bankService = new BankServiceImpl(bankRepository);
        BankAccountService bankAccountService = new BankAccountServiceImpl(bankAccountRepository);
        CheckWriter checkWriter = new CheckWriterImpl();
        ConsoleWriter consoleWriter = new ConsoleWriterImpl();
        AccountStatementWriter statementWriter = new AccountStatementWriterImpl();
        FacadeService facadeService = new FacadeServiceImpl(bankAccountService, bankService, userService, transactionService, checkWriter, statementWriter);
        ExceptionHandler exceptionHandler = new ExceptionHandlerImpl(facadeService);
        BankSystemController controller = new BankSystemControllerImpl(exceptionHandler, consoleWriter);


        User currentUser = userService.getById(5);


        controller.start(currentUser);
    }
}