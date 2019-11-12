package org.jaws.core.exception;

public abstract class JawsException extends RuntimeException {

  public JawsException() {
    super();
  }

  public JawsException(String message) {
    super(message);
  }
}
