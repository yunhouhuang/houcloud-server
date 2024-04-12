/*
 * Copyright (C) 2022 Yunhou·Huang  yunhouhuang@gmail.com.
 * All rights reserved.
 * Official Web Site: http://houcloud.com.
 */

package com.houcloud.example.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.houcloud.example.model.entity.Admin;
import com.houcloud.example.model.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * mark
 * </p>
 *
 * @author <a href="mailto:yunhouhuang@gmail.com">yunhouhuang@gmail.com</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AdminResponse extends Admin {

    @JsonIgnore
    private String password;

    private List<Role> roles;
}
