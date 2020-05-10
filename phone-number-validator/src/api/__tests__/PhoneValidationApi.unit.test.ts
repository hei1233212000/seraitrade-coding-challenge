import { enableFetchMocks } from 'jest-fetch-mock'
import PhoneValidationApi from '../PhoneValidationApi'
import { ValidationResult } from '../../models/ValidationResult'
import { ValidationApiResponse } from '../../models/ValidationApiResponse'

enableFetchMocks()

describe('PhoneValidationApi', () => {
    beforeEach(() => {
        fetchMock.resetMocks()
    })

    describe('#validate', () => {
        const anyPhoneNumber = '1234567'

        describe.each([
            [true],
            [false],
        ])('when the response of the NumVerify API is returning: "%s"', (expectedResult: boolean) => {
            let result: ValidationResult

            beforeEach(async () => {
                const validationApiResponse: ValidationApiResponse = {
                    valid: expectedResult,
                    number: anyPhoneNumber,
                    local_format: '',
                    international_format: '',
                    country_prefix: '',
                    country_code: '',
                    country_name: '',
                    location: '',
                    carrier: '',
                    line_type: null
                }
                fetchMock.mockOnce(JSON.stringify(validationApiResponse))

                result = await PhoneValidationApi.validate(anyPhoneNumber)
            })

            it('should contain the phone number', async () => {
                expect(result.phoneNumber).toEqual(anyPhoneNumber)
            })

            it('should contain number', async () => {
                expect(result.validationApiResponse.number).toEqual(anyPhoneNumber)
            })

            it(`should contain "valid" as ${expectedResult}`, async () => {
                expect(result.validationApiResponse.valid).toEqual(expectedResult)
            })
        })
    })
})
