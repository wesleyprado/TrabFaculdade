package com.wesleyprado.trabalhouna.services.validation;

import com.wesleyprado.trabalhouna.domain.Client;
import com.wesleyprado.trabalhouna.dto.ProposalDTO;
import com.wesleyprado.trabalhouna.repositories.ClientRepository;
import com.wesleyprado.trabalhouna.resources.exception.FieldMessage;
import com.wesleyprado.trabalhouna.services.validation.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProposalUpdateValidator implements ConstraintValidator<ProposalUpdate, ProposalDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void initialize(ProposalUpdate ann) {
    }

    @Override
    public boolean isValid(ProposalDTO objDto, ConstraintValidatorContext context) {
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriId = Integer.parseInt(map.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        if(!DateUtils.ofLegalAge(objDto.getBirthdate())){
            list.add(new FieldMessage("birthDate", "Must be of legal age (18 years)."));
        }

        Client aux = clientRepository.findByEmail(objDto.getEmail());
        if( aux != null && !aux.getId().equals(uriId) ){
            list.add(new FieldMessage("email", "Existing email."));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }

        return list.isEmpty();
    }
}