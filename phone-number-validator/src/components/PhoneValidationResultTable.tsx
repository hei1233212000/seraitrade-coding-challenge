import React, { useContext } from 'react'
import { DataTable } from 'primereact/datatable'
import { Column } from 'primereact/column'
import { ValidationResult } from '../models/ValidationResult'
import { PhoneValidationContext } from '../context/PhoneValidationContext'

const PhoneValidationResultTable = () => {
    const phoneValidationState = useContext(PhoneValidationContext)

    return <div>
        <DataTable value={phoneValidationState.validationResults} emptyMessage="No record"
                   rowHover resizableColumns responsive>
            <Column field="phoneNumber" header="Number" style={{width: '20%'}}/>
            <Column header="Valid?" body={isValidTemplate} style={{width: '15%'}}/>
            <Column header="API response" body={apiResponseTemplate}/>
        </DataTable>
    </div>
}

const isValidTemplate = (validationResult: ValidationResult): JSX.Element => {
    const iconClassname = validationResult.validationApiResponse.valid ? 'check' : 'times'
    const classname = `pi pi-${iconClassname}`
    return <i className={classname}/>
}

const apiResponseTemplate = (validationResult: ValidationResult): JSX.Element => {
    return <div>
        <pre>
          <code>
              {JSON.stringify(validationResult.validationApiResponse, null, '\t')}
          </code>
        </pre>
    </div>
}

export default PhoneValidationResultTable