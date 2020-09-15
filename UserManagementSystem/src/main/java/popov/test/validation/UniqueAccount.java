package popov.test.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueAccountValidator.class) // which class correspond for validation rules
@Retention(RetentionPolicy.RUNTIME) // indicates how long annotations are to be retained (available at compilation & for JVM)
@Target(ElementType.TYPE) // annotation for classes
public @interface UniqueAccount {

    // default displayed message
    String message() default "An account with this name already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
