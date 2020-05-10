import { useContext, useState } from 'react'
import { PhoneValidationContext } from '../context/PhoneValidationContext'

export class UsePhoneNumberResult {
    readonly phoneNumber: string
    readonly setPhoneNumber: SetPhoneNumberFunction
    readonly validateAndUpdatePhoneNumber: ValidateAndUpdatePhoneNumberFunction

    constructor(phoneNumber: string,
                setPhoneNumber: SetPhoneNumberFunction,
                validateAndUpdatePhoneNumber: ValidateAndUpdatePhoneNumberFunction) {
        this.phoneNumber = phoneNumber
        this.setPhoneNumber = setPhoneNumber
        this.validateAndUpdatePhoneNumber = validateAndUpdatePhoneNumber
    }
}

interface SetPhoneNumberFunction {
    (phoneNumber: string): void
}

interface ValidateAndUpdatePhoneNumberFunction {
    (): void
}

export const usePhoneNumber = (): UsePhoneNumberResult => {
    const phoneValidationState = useContext(PhoneValidationContext)
    const [phoneNumber, setPhoneNumber] = useState('')
    const validateAndUpdatePhoneNumber = () => {
        if (phoneNumber) {
            return phoneValidationState.validatePhoneNumber(phoneNumber)
                .then((success: boolean) => {
                    if (success) {
                        setPhoneNumber('')
                    }
                })
        }
    }
    return new UsePhoneNumberResult(phoneNumber, setPhoneNumber, validateAndUpdatePhoneNumber)
}
