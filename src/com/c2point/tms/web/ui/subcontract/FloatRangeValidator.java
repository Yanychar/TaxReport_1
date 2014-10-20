package com.c2point.tms.web.ui.subcontract;

import com.vaadin.data.validator.RangeValidator;

@SuppressWarnings("serial")
public class FloatRangeValidator extends RangeValidator<Float> {

    public FloatRangeValidator( String errorMessage, Float minValue, Float maxValue) {
    	
        super( errorMessage, Float.class, minValue, maxValue );
        
    }

	
}
