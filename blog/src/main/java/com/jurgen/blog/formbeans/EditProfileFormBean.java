package com.jurgen.blog.formbeans;

import javax.validation.constraints.Size;

public class EditProfileFormBean {
    
    @Size(min = 0, max = 500, message = "-length should be no more than 500")
    private String about;

    public EditProfileFormBean() {
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

}
