package org.jaws.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDTO {
  private Long userId;
  @NotEmpty
  private String firstName;
  @NotEmpty
  private String lastName;
  @NotEmpty
  private String email;
  @NotEmpty
  private String userName;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
}
