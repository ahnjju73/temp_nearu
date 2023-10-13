package com.nearu.nearu.object.request;


import com.nearu.nearu.entity.Application;
import com.nearu.nearu.entity.UserInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class ApplicationReadResponse {
    private String name;
    private Application app;
    private ArrayList<UserInfo>  applicants;
}
