package com.ecological;

/**
 * Created by simon on 08/10/19.
 */
public class Solution2DTO implements java.io.Serializable {

    static final long serialVersionUID = 1L;

      private java.lang.Long id;

    private java.lang.Boolean ok;

    public Solution2DTO() {
    }

    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    public java.lang.Boolean getOk() {
        return this.ok;
    }

    public void setOk(java.lang.Boolean ok) {
        this.ok = ok;
    }

    public Solution2DTO(java.lang.Long id, java.lang.Boolean ok) {
        this.id = id;
        this.ok = ok;
    }

}