/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.feedback.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 *
 * @author zubbo
 */
public class CreateFeedbackRequest {

    @NotBlank(message = "Имя обязательно")
    @Size(max = 150, message = "Имя не должно быть длиннее 150 символов")
    private String contactName;

    @NotBlank(message = "Телефон обязателен")
    @Size(max = 30, message = "Телефон не должен быть длиннее 30 символов")
    private String contactPhone;

    @NotBlank(message = "Email обязателен")
    @Email(message = "Введите корректный email")
    @Size(max = 255, message = "Email не должен быть длиннее 255 символов")
    private String contactEmail;

    @NotBlank(message = "Сообщение обязательно")
    @Size(min = 10, max = 3000, message = "Сообщение должно быть от 10 до 3000 символов")
    private String messageText;

    private Long productId;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail == null ? null : contactEmail.trim().toLowerCase();
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
