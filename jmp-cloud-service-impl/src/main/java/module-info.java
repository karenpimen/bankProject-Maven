module jmp.cloud.service.impl {
    //requires transitive module with Service interface
    requires transitive jmp.service.api;
    //requires jmp/dto
    requires jmp.dto;
    //exports implementation of Service interface
    exports jmp.cloud.service.impl;

    requires java.sql;
}