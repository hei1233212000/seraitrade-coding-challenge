import { ValidationApiResponse } from './ValidationApiResponse'

export class ValidationResult {
    readonly phoneNumber: string
    readonly validationApiResponse: ValidationApiResponse

    constructor(phoneNumber: string, validationApiResponse: ValidationApiResponse) {
        this.phoneNumber = phoneNumber
        this.validationApiResponse = validationApiResponse
    }
}
