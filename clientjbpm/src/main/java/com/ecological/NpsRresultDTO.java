package com.ecological;

import org.kie.server.api.model.instance.ProcessInstance;

/**
 * Created by simon on 08/10/19.
 */
public class NpsRresultDTO implements java.io.Serializable {
    static final long serialVersionUID = 1L;

     private java.lang.Long id;

    public NpsRresultDTO() {
    }



    @org.kie.api.definition.type.Label("Process Instance ID")
    private ProcessInstance processInstance;

    @org.kie.api.definition.type.Label("Point")
    private java.lang.Integer point;

    @org.kie.api.definition.type.Label("Promotor")
    private java.lang.Boolean promotor;

    @org.kie.api.definition.type.Label("Detractor")
    private java.lang.Boolean detractor;

    @org.kie.api.definition.type.Label("Neutro")
    private java.lang.Boolean neutro;

    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    public java.lang.Integer getPoint() {
        return this.point;
    }

    public void setPoint(java.lang.Integer point) {
        this.point = point;
    }

    public java.lang.Boolean getPromotor() {
        return this.promotor;
    }

    public void setPromotor(java.lang.Boolean promotor) {
        this.promotor = promotor;
    }

    public java.lang.Boolean getDetractor() {
        return this.detractor;
    }

    public void setDetractor(java.lang.Boolean detractor) {
        this.detractor = detractor;
    }

    public java.lang.Boolean getNeutro() {
        return this.neutro;
    }

    public void setNeutro(java.lang.Boolean neutro) {
        this.neutro = neutro;
    }

    public NpsRresultDTO(java.lang.Long id, java.lang.Integer point,
                         java.lang.Boolean promotor, java.lang.Boolean detractor,
                         java.lang.Boolean neutro) {
        this.id = id;
        this.point = point;
        this.promotor = promotor;
        this.detractor = detractor;
        this.neutro = neutro;
    }


    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }
}