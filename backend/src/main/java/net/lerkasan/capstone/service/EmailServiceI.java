package net.lerkasan.capstone.service;

import net.lerkasan.capstone.utils.EmailDetails;

public interface EmailServiceI {

    String sendSimpleMail(EmailDetails details);

}