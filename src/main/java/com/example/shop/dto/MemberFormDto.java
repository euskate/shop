package com.example.shop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class MemberFormDto {

    @NotBlank(message = "필수입력 값입니다.")
    private String name;
    @NotEmpty(message = "필수입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;
    @NotEmpty(message = "필수입력 값입니다.")
    @Length(min=8, max=16, message = "8~16자 이내로 입력해주세요.")
    private String password;
    @NotEmpty(message = "필수입력 값입니다.")
    private String address;
}
