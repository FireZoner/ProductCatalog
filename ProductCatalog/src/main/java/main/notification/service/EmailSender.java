/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.notification.service;

/**
 *
 * @author zubbo
 */
public interface EmailSender {
    void send(String recipientEmail, String subject, String body);
}
