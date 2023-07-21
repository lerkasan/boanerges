package net.lerkasan.capstone.constraint;

public interface UniqueValidatable {

    boolean isAvailable(String fieldName, String fieldValue);
}