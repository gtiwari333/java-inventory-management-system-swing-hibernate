package com.gt.uilib.inputverifier;

public interface Verifier {
    void validateFailed(); // Called when a component has failed validation.

    void validatePassed(); // Called when a component has passed validation.
}
