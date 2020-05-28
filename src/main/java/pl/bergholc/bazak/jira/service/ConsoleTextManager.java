package pl.bergholc.bazak.jira.service;

import org.apache.log4j.Logger;

import java.util.Scanner;

public class ConsoleTextManager {
    private static Logger logger = LoggerManager.getApplicationLogger();

    public String loadFromConsole(String description) {
        logger.trace("loadFromConsole()");
        System.out.println(description);
        Scanner sc = new Scanner(System.in);
        String result = sc.nextLine();
        logger.debug(result);

        return result;
    }

    public void sendMessage(String message){
        System.out.println(message);
    }

    public int getInt(String message){
        String result;
        do{
            result = loadFromConsole(message);
        }while (!isNumber(result));
        return Integer.valueOf(result);
    }

    private boolean isNumber(String message){
        try{
            Integer.valueOf(message);
        }catch (Exception e){
            logger.error(e.getMessage());
            return false;
        }
        return  true;
    }

}
