package com.example.host.databaseproject.OurClasses;

public abstract class Check {

    public static boolean checkEmail(String email){
        if((email.contains("@") && email.contains(".")) && !email.isEmpty()){
            return true;
        }

        else {
            return false;
        }
    }

    public static boolean comparePassword(String password1, String password2){
        if (password1.equals(password2) && !password1.isEmpty() && !password2.isEmpty()){
            return true;
        }

        else {
            return false;
        }

    }

    public static boolean checkPassword(String password){
       //String pattern = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])((?=\\S+$)(?=.{6,}))";
       String pattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";

       if(password.matches(pattern) || !password.isEmpty()){
           return true;
       }

       else {
           return false;
       }
    }

    public static boolean checkName(String name){
        //String pattern = "(?=.*[0-9]+$)(?=.*[а-я])(?=.*[А-Я])((?=\\S+$).{1,}";
        String pattern = "(?=.*[а-я])(?=.*[А-Я]).{2,100}";


        if(name.matches(pattern) && !name.isEmpty()){
            return true;
        }

        else {
            return false;
        }
    }



}
