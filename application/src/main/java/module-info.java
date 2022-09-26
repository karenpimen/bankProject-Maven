module application {
    //use interfaces
    requires jmp.bank.api;
    requires jmp.service.api;
    //requires module with bank implementation
    requires jmp.cloud.bank.impl;
    //requires module with service implementation
    requires jmp.cloud.service.impl;
    //requires jmp.dto
    requires jmp.dto;
    requires java.sql;
}