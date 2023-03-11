package utils;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final String REPO_REMOVE_ENTITY ="The removal could not be initiated!\n";
    public static final String REPO_NO_ELEMENT_FOUND="The entity does not exist!\n";
    public static final String REPO_ALREADY_EXISTS="The entity already exists!\n";
    public static final String REPO_DATABASE_ERROR="We encountered a database error!\n";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public static DateTimeFormatter FORMATTER_MESSAGE =DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
}