package com.nearu.nearu.object.request;

import com.nearu.nearu.entity.Application;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ApplicationReadAllResponse {
    private String name;
    private Application app;
    private Integer numberApplicants;
}
