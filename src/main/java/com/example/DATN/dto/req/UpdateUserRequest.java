//package com.example.DATN.dto.req;
//
//import com.example.DATN.entities.UserType;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import jakarta.persistence.Column;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//
//import java.time.LocalDateTime;
//
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public class UpdateUserRequest {
//
//    private String email;
//
//    private String fullName;
//
//    private String phone;
//
//    private String avartar;
//
//    private String gender;
//
//    private String birthday;
//
//    private String job;
//
//    public UpdateUserRequest() {
//    }
//
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//
//
//    public String getFullName() {
//        return fullName;
//    }
//
//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getAvartar() {
//        return avartar;
//    }
//
//    public void setAvartar(String avartar) {
//        this.avartar = avartar;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public String getBirthday() {
//        return birthday;
//    }
//
//    public void setBirthday(String birthday) {
//        this.birthday = birthday;
//    }
//
//    public String getJob() {
//        return job;
//    }
//
//    public void setJob(String job) {
//        this.job = job;
//    }
//}

package com.example.DATN.dto.req;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserRequest {
    private String field;  // Tên trường cần cập nhật (vd: "birthday")
    private String value;  // Giá trị mới của trường đó

    // Getters và Setters
    public String getField() { return field; }
    public void setField(String field) { this.field = field; }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}

