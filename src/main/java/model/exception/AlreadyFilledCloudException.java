package model.exception;

import model.*;

public class AlreadyFilledCloudException extends Exception {
    public AlreadyFilledCloudException(String message) {
        super(message);
    }
}
