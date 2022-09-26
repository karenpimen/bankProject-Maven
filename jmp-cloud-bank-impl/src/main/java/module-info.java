module jmp.cloud.bank.impl {
    //requires transitive module with Bank interface:Transitive means that any module that reads this module implicitly reads the transitive module
    requires transitive jmp.bank.api;
    //requires jmp-dto
    requires jmp.dto;
    //Exports implementation of Bank interface
    exports jmp.cloud.bank.impl;
    requires java.sql;

}