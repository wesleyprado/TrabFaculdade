package com.wesleyprado.trabalhouna.services.validation;

import com.wesleyprado.trabalhouna.domain.Client;
import com.wesleyprado.trabalhouna.dto.NewProposalDTO;
import com.wesleyprado.trabalhouna.repositories.ClientRepository;
import com.wesleyprado.trabalhouna.resources.exception.FieldMessage;
import com.wesleyprado.trabalhouna.services.validation.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ProposalInsertValidator implements ConstraintValidator<ProposalInsert, NewProposalDTO> {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void initialize(ProposalInsert ann) {
    }

    @Override
    public boolean isValid(NewProposalDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        if(objDto.getBirthdate() != null && !DateUtils.ofLegalAge(objDto.getBirthdate())){
            list.add(new FieldMessage("dataNascimento", "Deve ser maior de idade (18 anos)."));
        }

        Client aux = clientRepository.findByEmail(objDto.getEmail());
        if( aux != null ){
            list.add(new FieldMessage("email", "E-mail já existente."));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }

        return list.isEmpty();
    }
}