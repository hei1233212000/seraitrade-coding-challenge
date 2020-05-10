import React from 'react'
import {
    cleanup,
    fireEvent,
    getAllByPlaceholderText,
    getAllByText,
    getByText,
    queryAllByRole,
    queryByDisplayValue,
    render,
    screen
} from '@testing-library/react'
import App from '../App'
import { enableFetchMocks } from 'jest-fetch-mock'
import { act } from 'react-dom/test-utils'
import { ValidationApiResponse } from '../models/ValidationApiResponse'

enableFetchMocks()

describe('App', () => {
    let validationForm: HTMLElement
    let revalidationForm: HTMLElement
    let validationResultTable: HTMLElement

    beforeEach(async () => {
        await act(async () => {
            render(<App/>)
        })

        validationForm = screen.getByTestId('validate-phone-number-form')
        revalidationForm = screen.getByTestId('revalidate-phone-number-form')
        validationResultTable = screen.getByTestId('validation-result-table')
    })

    afterEach(cleanup)

    it('should show a caption', () => {
        expect(screen.getByText('Phone number validation')).toBeInTheDocument()
    })

    it('should show a form for validating phone number', () => {
        expect(validationForm).toBeInTheDocument()
    })

    it('should show a form for re-validating the phone number', () => {
        expect(revalidationForm).toBeInTheDocument()
    })

    it('should show a validation result table', () => {
        expect(validationResultTable).toBeInTheDocument()
    })

    describe('when we try to validate a phone number', () => {
        const firstPhoneNumberInResponse = '1234567'
        const firstPhoneNumberInput = `+${firstPhoneNumberInResponse}`
        let validationFormInput: HTMLElement
        let validationApiResponse: ValidationApiResponse
        let resultRow: HTMLElement

        beforeEach(async () => {
            // prepare API response
            fetchMock.resetMocks()

            validationApiResponse = createValidationApiResponse(firstPhoneNumberInResponse)
            fetchMock.mockOnce(JSON.stringify(validationApiResponse))

            // input phone number
            validationFormInput = getAllByPlaceholderText(validationForm, 'Phone Number')[0]
            expect(validationFormInput).toBeInTheDocument()
            await act(async () => {
                fireEvent.change(validationFormInput, {
                    target: {value: firstPhoneNumberInput}
                })
            })

            expect((validationFormInput as HTMLInputElement).value).toEqual(firstPhoneNumberInput)

            // click validate button
            const validationFormButton = validationForm.querySelector('button')
            expect(validationFormButton).toBeInTheDocument()
            await act(async () => {
                fireEvent.click(validationFormButton as HTMLButtonElement)
            })

            // should show the validation result in table
            const phoneNumberColumns = getAllByText(validationResultTable, firstPhoneNumberInput)
            expect(phoneNumberColumns).toHaveLength(1)
            resultRow = phoneNumberColumns[0].parentElement!
        })

        it('should show a new row in table', () => {
            expect(resultRow).toBeInTheDocument()
        })

        it('should show the validated phone number as a result in table ', () => {
            expect(getByText(resultRow, firstPhoneNumberInput)).toBeInTheDocument()
        })

        it('should show an icon to indicated if the phone number is valid in table', () => {
            expect(resultRow.querySelector('.pi-check')).toBeInTheDocument()
        })

        it('should show the api response as code in table', () => {
            expect(resultRow.querySelector('pre')).toBeInTheDocument()
        })

        it('should show the empty input box now', () => {
            expect(getByText(validationFormInput, '')).toBeInTheDocument()
        })

        describe('when we try to re-validate the phone number', () => {
            beforeEach(async () => {
                // select the validated phone number
                expect(queryByDisplayValue(revalidationForm, firstPhoneNumberInput)).toBeNull()

                const validatePhoneNumbers = queryAllByRole(revalidationForm, 'option')
                expect(validatePhoneNumbers).toHaveLength(1)

                const validatedPhoneNumber = validatePhoneNumbers[0]
                await act(async () => {
                    fireEvent.click(validatedPhoneNumber)
                })
                expect(queryByDisplayValue(revalidationForm, firstPhoneNumberInput)).toBeInTheDocument()

                // click revalidate button
                fetchMock.mockOnce(JSON.stringify(validationApiResponse))
                const revalidationFormButton = revalidationForm.querySelector('button')
                expect(revalidationFormButton).toBeInTheDocument()
                await act(async () => {
                    fireEvent.click(revalidationFormButton as HTMLButtonElement)
                })
            })

            it('should show the other record in validation result table', () => {
                const phoneNumberColumns = getAllByText(validationResultTable, firstPhoneNumberInput)
                expect(phoneNumberColumns).toHaveLength(2)
            })

            it('should show the dropdown box does not selected anything', () => {
                expect(queryByDisplayValue(revalidationForm, firstPhoneNumberInput)).toBeNull()
            })

            it('should show the dropdown box has only one item because it only shows unique item', () => {
                const options = revalidationForm.querySelectorAll('ul')
                expect(options).toHaveLength(1)
            })
        })
    })
})

function createValidationApiResponse(phoneNumber: string, valid: boolean = true): ValidationApiResponse {
    return {
        valid: valid,
        number: phoneNumber,
        local_format: '',
        international_format: '',
        country_prefix: '',
        country_code: '',
        country_name: '',
        location: '',
        carrier: '',
        line_type: null
    }
}
