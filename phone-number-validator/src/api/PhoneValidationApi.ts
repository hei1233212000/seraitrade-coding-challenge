import { ValidationResult } from '../models/ValidationResult'
import { ValidationApiResponse } from '../models/ValidationApiResponse';

export default class PhoneValidationApi {
    static validate = async (phoneNumber: string): Promise<ValidationResult> => {
        const accessKey = process.env.REACT_APP_NUMVERIFY_ACCESS_KEY
        const response =
            await fetch(`http://apilayer.net/api/validate?access_key=${accessKey}&number=${phoneNumber}`)
        const validationApiResponse = await response.json() as ValidationApiResponse
        return new ValidationResult(phoneNumber, validationApiResponse)
    }
}
