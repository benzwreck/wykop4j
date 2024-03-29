package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when:
 * <ul>
 *     <li>body contains only pm</li>
 *     <li>you are going to change an entry or comment which is not yours</li>
 * </ul>
 */
public class BodyContainsOnlyPmException extends WykopException {
    public BodyContainsOnlyPmException() {
        super(602, "Body contains only pm. Probably you are going to change an entry or comment which is not yours.", "Treść zawiera tylko login. Możliwe, że próbujesz zmienić swój wpis.");
    }
}
