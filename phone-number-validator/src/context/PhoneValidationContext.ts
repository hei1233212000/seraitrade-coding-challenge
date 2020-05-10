import { createContext } from 'react'
import { ValidationResult } from '../models/ValidationResult'

export const PhoneValidationContext = createContext({} as PhoneValidationState)

export class PhoneValidationState {
    readonly validationResults: ValidationResult[]
    readonly validatePhoneNumber: ValidatePhoneNumberFunction

    constructor(
        validationResults: ValidationResult[],
        validatePhoneNumberFunction: ValidatePhoneNumberFunction
    ) {
        this.validationResults = validationResults
        this.validatePhoneNumber = validatePhoneNumberFunction
    }
}

export interface ValidatePhoneNumberFunction {
    (phoneNumber: string): Promise<boolean>
}
