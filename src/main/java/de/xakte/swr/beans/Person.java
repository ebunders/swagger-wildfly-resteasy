package de.xakte.swr.beans;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by Ernst Bunders on 29-6-15.
 */
@ApiModel(value = "some person")
public class Person {

    @NotNull
    @ApiModelProperty(required = true, notes = "Some bloke or lasses name")
    private String name;
    @NotNull
    @ApiModelProperty(required = true, notes = "Male, or female, in caps, I'm afraid", allowableValues = "MALE, FEMALE")
    private Gender gender;

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
