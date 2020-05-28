package pl.bergholc.bazak.jira.utility;

public class FormUtility {
    public static boolean isEmptyForm(String str){
        return str.startsWith(" ")||str.isEmpty();
    }
}
