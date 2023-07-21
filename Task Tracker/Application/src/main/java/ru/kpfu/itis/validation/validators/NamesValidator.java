package ru.kpfu.itis.validation.validators;

import org.springframework.beans.BeanWrapperImpl;
import ru.kpfu.itis.validation.annotations.NotSameNames;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class NamesValidator implements ConstraintValidator<NotSameNames, Object> {

    private String[] fields;
    private String message;

    @Override
    public void initialize(NotSameNames constraintAnnotation) {
        this.fields = constraintAnnotation.names();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        final BeanWrapperImpl bean = new BeanWrapperImpl(object);
        List<String> fieldValues = new ArrayList<>();

        for (String fieldName : fields) {
            fieldValues.add((String) bean.getPropertyValue(fieldName));
        }

        boolean isValid = true;

        if (fieldValues.size() != fieldValues.stream().distinct().count()){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("lastName")
                    .addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}