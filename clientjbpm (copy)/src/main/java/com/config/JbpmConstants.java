package com.config;



import java.util.Map;
/**
 * Created by simon on 14/08/19.
 */
public enum JbpmConstants {

    CONTAINER_ID("wissen-container"),

    KIESERVER("KieServer"),
    // Id container
    // process Id
    PROCESS_ID("ProyectoInversion.ProyectoDeInversion_ProcesoPrincipal");


    private final String taskName;


    JbpmConstants(String taskName) {
        this.taskName = taskName;
    }

    public String getValue() {
        return taskName;
    }


    public static JbpmConstants get(String taskName) {
        for (JbpmConstants cons : JbpmConstants.values()) {
            if (cons.getValue().equalsIgnoreCase(taskName)) {
                return cons;
            }
        }
        throw new IllegalStateException();
    }
}
