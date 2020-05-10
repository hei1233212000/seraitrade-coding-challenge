import React, { useContext } from 'react'
import { PhoneValidationContext } from '../context/PhoneValidationContext'
import { Dropdown } from 'primereact/dropdown'
import { Button } from 'primereact/button';
import { ValidationResult } from '../models/ValidationResult'
import { usePhoneNumber } from '../hook/usePhoneNumberHook'

const PhoneValidationRevalidateForm = (): JSX.Element => {
    const phoneValidationState = useContext(PhoneValidationContext)
    const uniqueValidationResults = extractUniqueValidationResults(phoneValidationState.validationResults)

    const usePhoneNumberResult = usePhoneNumber()
    const onPhoneNumberChange = (e: { originalEvent: Event, value: any, target: { name: string, id: string, value: any } }) => {
        usePhoneNumberResult.setPhoneNumber(e.value)
    }

    return <div>
        <small>Select a validated number to validate it again:</small>
        <div className="p-inputgroup">
            <Dropdown
                options={uniqueValidationResults}
                optionLabel="phoneNumber" optionValue="phoneNumber"
                style={{minWidth: 200}}
                value={usePhoneNumberResult.phoneNumber} onChange={onPhoneNumberChange}
            />
            <Button icon="pi pi-search" onClick={usePhoneNumberResult.validateAndUpdatePhoneNumber}/>
        </div>
    </div>
}

const extractUniqueValidationResults = (validationResults: ValidationResult[]): ValidationResult[] => {
    return validationResults.filter((value: ValidationResult, index: number, self: ValidationResult[]) => {
        const indexOfExistingValue = self.findIndex((v: ValidationResult) => v.phoneNumber === value.phoneNumber)
        return indexOfExistingValue === index
    })
}

export default PhoneValidationRevalidateForm
