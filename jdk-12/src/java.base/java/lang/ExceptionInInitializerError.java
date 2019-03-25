/*
 * Copyright (c) 1996, 2000, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.lang;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;

/**
 * Signals that an unexpected exception has occurred in a static initializer.
 * An <code>ExceptionInInitializerError</code> is thrown to indicate that an
 * exception occurred during evaluation of a static initializer or the
 * initializer for a static variable.
 *
 * <p>As of release 1.4, this exception has been retrofitted to conform to
 * the general purpose exception-chaining mechanism.  The "saved throwable
 * object" that may be provided at construction time and accessed via
 * the {@link #getException()} method is now known as the <i>cause</i>,
 * and may be accessed via the {@link Throwable#getCause()} method, as well
 * as the aforementioned "legacy method."
 *
 * @author  Frank Yellin
 * @since   1.1
 */
public class ExceptionInInitializerError extends LinkageError {
    /**
     * Use serialVersionUID from JDK 1.1.X for interoperability
     */
    private static final long serialVersionUID = 1521711792217232256L;

    /**
     * Constructs an <code>ExceptionInInitializerError</code> with
     * <code>null</code> as its detail message string and with no saved
     * throwable object.
     * A detail message is a String that describes this particular exception.
     */
    public ExceptionInInitializerError() {
        initCause(null); // Disallow subsequent initCause
    }

    /**
     * Constructs a new <code>ExceptionInInitializerError</code> class by
     * saving a reference to the <code>Throwable</code> object thrown for
     * later retrieval by the {@link #getException()} method. The detail
     * message string is set to <code>null</code>.
     *
     * @param thrown The exception thrown
     */
    public ExceptionInInitializerError(Throwable thrown) {
        super(null, thrown); // Disallow subsequent initCause
    }

    /**
     * Constructs an {@code ExceptionInInitializerError} with the specified detail
     * message string.  A detail message is a String that describes this
     * particular exception. The detail message string is saved for later
     * retrieval by the {@link Throwable#getMessage()} method. There is no
     * saved throwable object.
     *
     * @param s the detail message
     */
    public ExceptionInInitializerError(String s) {
        super(s, null);  // Disallow subsequent initCause
    }

    /**
     * Returns the exception that occurred during a static initialization that
     * caused this error to be created.
     *
     * <p>This method predates the general-purpose exception chaining facility.
     * The {@link Throwable#getCause()} method is now the preferred means of
     * obtaining this information.
     *
     * @return the saved throwable object of this
     *         <code>ExceptionInInitializerError</code>, or <code>null</code>
     *         if this <code>ExceptionInInitializerError</code> has no saved
     *         throwable object.
     */
    public Throwable getException() {
        return super.getCause();
    }

    /**
     * Serializable fields for ExceptionInInitializerError.
     *
     * @serialField exception Throwable
     */
    private static final ObjectStreamField[] serialPersistentFields = {
        new ObjectStreamField("exception", Throwable.class)
    };

    /*
     * Reconstitutes the ExceptionInInitializerError instance from a stream
     * and initialize the cause properly when deserializing from an older
     * version.
     *
     * The getException and getCause method returns the private "exception"
     * field in the older implementation and ExceptionInInitializerError::cause
     * was set to null.
     */
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        ObjectInputStream.GetField fields = s.readFields();
        Throwable exception = (Throwable) fields.get("exception", null);
        if (exception != null) {
            setCause(exception);
        }
    }

    /*
     * To maintain compatibility with older implementation, write a serial
     * "exception" field with the cause as the value.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        ObjectOutputStream.PutField fields = out.putFields();
        fields.put("exception", super.getCause());
        out.writeFields();
    }

}