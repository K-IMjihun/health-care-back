package com.example.health_care_back.application.common.exception;

import com.example.health_care_back.application.common.response.ResponseCode;
import lombok.AllArgsConstructor;

public class ModifyPermissionException extends CommonException  {

    @AllArgsConstructor
    public enum ModifyPermissionExceptionCode implements ResponseCode {
        UNAUTHORIZED_ACCESS_EXCEPTION("MPE-001", "unauthorized access"),
        CANNOT_MODIFY_SAME_AUTHORITY_EXCEPTION("MPE-002", "cannot modify with the same authority"),
        ;


        private final String code;
        private final String message;

        @Override
        public String getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }
    }

    public ModifyPermissionException(ModifyPermissionExceptionCode code) {
        super(code);
    }

    public ModifyPermissionException(ModifyPermissionExceptionCode code, String message) {
        super(code, message);
    }
}