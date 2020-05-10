import React, { useRef, useState } from 'react'

import 'primereact/resources/themes/nova-light/theme.css'
import 'primereact/resources/primereact.min.css'
import 'primeicons/primeicons.css'
import 'primeflex/primeflex.css'
import PhoneValidationInputForm from './components/PhoneValidationInputForm'
import PhoneValidationResultTable from './components/PhoneValidationResultTable'
import './App.css'

import {
    PhoneValidationContext,
    PhoneValidationState,
    ValidatePhoneNumberFunction
} from './context/PhoneValidationContext'
import { ValidationResult } from './models/ValidationResult'
import PhoneValidationRevalidateForm from './components/PhoneValidationRevalidateForm'
import PhoneValidationApi from './api/PhoneValidationApi'
import { Growl, GrowlMessage } from 'primereact/growl'

const App = (): JSX.Element => {
    const {growl, successCallback, errorCallback} = useGrowl()
    const phoneValidationState = usePhoneValidationState(successCallback, errorCallback)
    return (
        <PhoneValidationContext.Provider value={phoneValidationState}>
            <div className="p-grid">
                <div className="p-col-12"><h2>Phone number validation</h2></div>
                <div className="p-lg-2 p-lg-3">
                    <div data-testid="validate-phone-number-form">
                        <PhoneValidationInputForm/>
                    </div>
                    <br/>
                    <div data-testid="revalidate-phone-number-form">
                        <PhoneValidationRevalidateForm/>
                    </div>
                </div>
                <div className="p-lg-9 p-md-8" data-testid="validation-result-table">
                    <PhoneValidationResultTable/>
                </div>
            </div>
            <Growl ref={growl}/>
        </PhoneValidationContext.Provider>
    )
}

const usePhoneValidationState = (successCallback: (phoneNumber: string) => void,
                                 errorCallback: (phoneNumber: string) => void) => {
    const emptyValidationResults: ValidationResult[] = []
    const [validationResults, setValidationResults] = useState(emptyValidationResults)
    const validatePhoneNumber: ValidatePhoneNumberFunction = async (phoneNumber: string) => {
        return PhoneValidationApi.validate(phoneNumber)
            .then((validationResult: any) => {
                if (validationResult.error) {
                    errorCallback(phoneNumber)
                    return false
                } else {
                    successCallback(phoneNumber)
                    // latest validation result should be on top
                    validationResults.unshift(validationResult)
                    setValidationResults(Object.assign([], validationResults))
                    return true
                }
            })
    }

    return new PhoneValidationState(validationResults, validatePhoneNumber)
}

const useGrowl = () => {
    const growl = useRef({} as Growl)
    const successCallback = (phoneNumber: string) => {
        const successMessage = buildSuccessMessage(phoneNumber)
        growl.current.show(successMessage);
    }
    const errorCallback = (phoneNumber: string) => {
        const errorMessage = buildErrorMessage(phoneNumber)
        growl.current.show(errorMessage);
    }
    return {growl, successCallback, errorCallback}
}

const buildErrorMessage = (phoneNumber: string): GrowlMessage => {
    return {
        severity: 'error',
        summary: 'API Error',
        detail: `Fail to validate "${phoneNumber}" by API`,
    }
}

const buildSuccessMessage = (phoneNumber: string): GrowlMessage => {
    return {
        severity: 'success',
        summary: 'Validation is done',
        detail: `"${phoneNumber}" is validated`,
    }
}

export default App
