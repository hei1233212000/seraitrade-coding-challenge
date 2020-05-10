import React from 'react'
import { InputText } from 'primereact/inputtext'
import { Button } from 'primereact/button'
import { usePhoneNumber } from '../hook/usePhoneNumberHook'

const PhoneValidationInputForm = () => {
    const usePhoneNumberResult = usePhoneNumber()
    const onPhoneNumberChange = (event: React.FormEvent<HTMLInputElement>) => {
        const newPhoneNumber = (event.target as HTMLInputElement).value
        usePhoneNumberResult.setPhoneNumber(newPhoneNumber)
    }
    const onKeyPressed = (event: React.KeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter') {
            usePhoneNumberResult.validateAndUpdatePhoneNumber()
        }
    }

    return <div>
        <div className="p-inputgroup">
            <span className="p-inputgroup-addon"><i className="pi pi-mobile"/></span>
            <InputText placeholder="Phone Number" value={usePhoneNumberResult.phoneNumber}
                       onChange={onPhoneNumberChange} onKeyPress={onKeyPressed}/>
            <Button icon="pi pi-search" onClick={usePhoneNumberResult.validateAndUpdatePhoneNumber}/>
        </div>
    </div>
}

export default PhoneValidationInputForm
